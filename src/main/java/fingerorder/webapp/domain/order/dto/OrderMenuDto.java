package fingerorder.webapp.domain.order.dto;

import fingerorder.webapp.domain.order.entity.OrderMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenuDto {

    private Long id;

    private int count;

    private int totalPrice;

    private OrderMenuDto(OrderMenu orderMenu) {
        this.id = orderMenu.getId();
        this.count = orderMenu.getCount();
        this.totalPrice = orderMenu.getTotalPrice();
    }

    public static OrderMenuDto createOrderMenu(OrderMenu orderMenu) {
        return new OrderMenuDto(orderMenu);
    }

}
