package fingerorder.webapp.domain.order.dto;

import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.store.dto.OrderMenuInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetInCompOrdersResponse {

    private Long id;
    private Integer tableNum;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private List<OrderMenuInfo> orderMenus;


    public GetInCompOrdersResponse(Order order) {
        this.id = order.getId();
        this.tableNum = order.getTableNum();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = order.getCreatedAt();
        this.orderMenus = createOrderMenus(order.getOrderMenus());
    }

    private List<OrderMenuInfo> createOrderMenus(List<OrderMenu> orderMenuList) {
        List<OrderMenuInfo> orderMenuInfos = new ArrayList<>();

        for (OrderMenu orderMenu : orderMenuList) {
            orderMenuInfos.add(
                new OrderMenuInfo(orderMenu.getMenu().getName(), orderMenu.getTotalPrice(),
                    orderMenu.getCount()));
        }

        return orderMenuInfos;
    }

}
