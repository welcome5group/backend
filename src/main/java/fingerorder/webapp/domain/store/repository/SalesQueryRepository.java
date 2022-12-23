package fingerorder.webapp.domain.store.repository;

import static fingerorder.webapp.domain.order.entity.QOrder.order;
import static fingerorder.webapp.domain.store.entity.QStore.store;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.store.dto.PaymentDetailsResponseDto;
import fingerorder.webapp.domain.store.dto.QPaymentDetailsResponseDto;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SalesQueryRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	@Autowired
	public SalesQueryRepository(EntityManager em) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	public List<PaymentDetailsResponseDto> findOrders(Long storeId, int year, int month) {

		LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0, 0)
			.with(TemporalAdjusters.firstDayOfMonth());
		LocalDateTime endDate = LocalDateTime.of(year, month, 1, 23, 59, 59)
			.with(TemporalAdjusters.lastDayOfMonth());

		StringTemplate formattedDate = Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})"
			, order.createdAt
			, ConstantImpl.create("%Y-%m-%d"));

		return queryFactory
			.select(
				new QPaymentDetailsResponseDto(
					formattedDate.as("yyyymm")
					, order.totalPrice.sum()
				)
			)
			.from(order)
			.join(order.store, store).on(store.id.eq(storeId))
			.where(order.createdAt.goe(startDate)
				, order.createdAt.loe(endDate)
			)
			.groupBy(formattedDate)
			.orderBy(formattedDate.asc())
			.fetch();

	}

	public List<Order> findOrders(Long storeId, LocalDateTime startDate, LocalDateTime endDate) {

//		StringTemplate formattedDate = Expressions.stringTemplate(
//			"DATE_FORMAT({0}, {1})"
//			, order.createdAt
//			, ConstantImpl.create("%Y-%m-%d"));

		return queryFactory
//			.select(
//				new QOrderDetailsResponseDto(
//					order.id,
//					order.tableNum,
//					order.totalPrice,
//					order.createdAt
//				)
//			)
			.selectFrom(order)
			.join(order.store, store).on(store.id.eq(storeId))
			.where(order.createdAt.goe(startDate)
				, order.createdAt.loe(endDate)
			)
			.orderBy(order.createdAt.asc())
			.fetch();
	}

}
