package fingerorder.webapp.dto;

import fingerorder.webapp.entity.OrderMenu;
import fingerorder.webapp.status.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenuDto {

    private Long id;

    private OrderStatus orderStatus;

    private int count;

    private int totalPrice;


    private OrderMenuDto(OrderMenu orderMenu) {
        this.id = orderMenu.getId();
        this.orderStatus = orderMenu.getOrderStatus();
        this.count = orderMenu.getCount();
        this.totalPrice = orderMenu.getTotalPrice();
    }

    public static OrderMenuDto createOrderMenu(OrderMenu orderMenu) {
        return new OrderMenuDto(orderMenu);
    }

}
