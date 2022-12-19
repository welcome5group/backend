package fingerorder.webapp.domain.order.repository;

import fingerorder.webapp.domain.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

}
