package fingerorder.webapp.domain.order.service;

import fingerorder.webapp.domain.order.dto.MenuDto;
import javax.persistence.EntityManager;
import lombok.Data;

@Data
public class OrderQueryService {

    private final EntityManager em;

    public void searchOrderMenu() {

        em.createQuery("select new fingerorder.webapp.domain.order.dto.MenuDto(om.menu.name, om.count)"
            + "from OrderMenu om", MenuDto.class);
    }

    public void searchOrderList() {


//        em.createQuery("select new fingerorder.webapp.domain.order.dto"
//                + ".OrderListResponse(o.store.name, o.orderStatus, o.createdAt, o.totalPrice)"
//            + "from Order o")
//            .getSingleResult();
    }
}
