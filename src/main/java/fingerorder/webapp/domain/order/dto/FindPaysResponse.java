package fingerorder.webapp.domain.order.dto;

import fingerorder.webapp.domain.menu.entity.Menu;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.store.dto.StoreResponse;
import fingerorder.webapp.domain.store.entity.Store;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindPaysResponse {

    private Long id;

    private int totalPrice;

    private LocalDateTime createdAt;

    private StoreResponse store;

    private List<OrderMenuDto> orderMenus = new ArrayList<>();


    private FindPaysResponse(Order order, Store store) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = order.getCreatedAt();
        this.store = store.toStoreRequest(store);

        for (OrderMenu orderMenu : order.getOrderMenus()) {
            addOrderMenu(orderMenu, orderMenu.getMenu());
        }
    }

    public static FindPaysResponse createPayResponse(Order order, Store store) {
        return new FindPaysResponse(order, store);
    }

    private void addOrderMenu(OrderMenu orderMenu, Menu menu) {
        this.orderMenus.add(OrderMenuDto.createOrderMenu(orderMenu, menu));
    }

}
