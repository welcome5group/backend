package fingerorder.webapp.repository;

import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.member = :member")
    List<Order> findAllByMember(@Param("member") Member member);
}
