package fingerorder.webapp.domain.order.repository;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.status.OrderStatus;
import fingerorder.webapp.domain.store.entity.Store;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMember(Member member);

    List<Order> findAllByStore(Store store);

    Optional<List<Order>> findAllByStoreIdAndOrderStatus(Long storeId, OrderStatus orderStatus);

    @Query("select o from Order o where o.member.id=:memberId and o.createdAt >= :date")
    Optional<List<Order>> findByMemberIdAndDate(@Param("memberId") Long memberId,
        @Param("date") LocalDateTime date, Sort sort);
}
