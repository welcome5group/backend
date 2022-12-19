package fingerorder.webapp.repository;

import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMember(Member member);

}
