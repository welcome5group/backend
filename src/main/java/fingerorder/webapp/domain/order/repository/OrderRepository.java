package fingerorder.webapp.domain.order.repository;

import fingerorder.webapp.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
