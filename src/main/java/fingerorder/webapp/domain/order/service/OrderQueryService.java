//package fingerorder.webapp.domain.order.service;
//
//import fingerorder.webapp.domain.order.dto.MenuDto;
//import javax.persistence.EntityManager;
//import lombok.Data;
//
//@Data
//public class OrderQueryService {
//
//    private final EntityManager em;
//
//    public void searchOrderMenu() {
//
//        em.createQuery("select new fingerorder.webapp.domain.order.dto.MenuDto(om.menu.name, om.count)"
//            + "from OrderMenu om", MenuDto.class);
//    }
//
//    public void searchOrderList() {
//
//        em.createQuery("select new fingerorder.webapp.domain.order.dto.OrderListResponse()")
//    }
//
//}
//
//    private String storeName;
//    private String orderStatus;
//    private String orderDate;
//    private int totalPrice;
