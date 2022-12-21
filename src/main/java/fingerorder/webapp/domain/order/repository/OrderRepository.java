package fingerorder.webapp.domain.order.repository;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.order.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMember(Member member);

}
