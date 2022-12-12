package fingerorder.webapp.repository;

import static fingerorder.webapp.entity.QCategory.category;
import static fingerorder.webapp.entity.QStore.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fingerorder.webapp.entity.Category;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryQueryRepository {
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	@Autowired
	public CategoryQueryRepository(EntityManager em) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	public Optional<Category> findCategory(Long storeId, String categoryName) {

		return Optional.ofNullable(queryFactory.select(category).from(category)
			.join(category.store, store).on(store.id.eq(storeId))
			.where(category.name.eq(categoryName))
			.fetchOne());
	}
}
