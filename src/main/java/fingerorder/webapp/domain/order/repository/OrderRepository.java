package fingerorder.webapp.domain.order.repository;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.order.entity.Order;
import java.util.List;
import fingerorder.webapp.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMember(Member member);
	List<Order> findAllByStore(Store store);

}
