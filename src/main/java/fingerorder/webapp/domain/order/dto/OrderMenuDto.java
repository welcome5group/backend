package fingerorder.webapp.domain.order.dto;

import fingerorder.webapp.domain.menu.dto.MenuResponse;
import fingerorder.webapp.domain.menu.entity.Menu;
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

    private MenuResponse menu;

    private OrderMenuDto(OrderMenu orderMenu, Menu menu) {
        this.id = orderMenu.getId();
        this.count = orderMenu.getCount();
        this.totalPrice = orderMenu.getTotalPrice();
        this.menu = menu.toMenuResponse(menu);
    }

    public static OrderMenuDto createOrderMenu(OrderMenu orderMenu, Menu menu) {
        return new OrderMenuDto(orderMenu, menu);
    }

}
