package fingerorder.webapp.domain.order.dto;

import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.order.status.OrderStatus;
import fingerorder.webapp.domain.order.status.ReviewStatus;
import fingerorder.webapp.domain.store.dto.OrderMenuInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListResponse {

    private Long orderId;
    private OrderStatus orderStatus;
    private ReviewStatus reviewStatus;
    private LocalDateTime orderDate;
    private int totalPrice;
    private List<OrderMenuInfo> menuList;

    public OrderListResponse(Long orderId, OrderStatus orderStatus, ReviewStatus reviewStatus,
        LocalDateTime orderDate, int totalPrice) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.reviewStatus = reviewStatus;
}


    public void insertMenu(List<OrderMenu> orderMenuList) {
        this.menuList = new ArrayList<>();
        for (OrderMenu orderMenu : orderMenuList) {
            menuList.add(new OrderMenuInfo(orderMenu.getMenu().getName()
            ,orderMenu.getTotalPrice(),orderMenu.getCount()));
        }
    }
}
