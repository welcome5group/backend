package fingerorder.webapp.domain.order.repository;

import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.store.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findAllByStore(Store store);

}
