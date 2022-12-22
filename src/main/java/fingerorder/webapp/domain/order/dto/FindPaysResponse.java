package fingerorder.webapp.domain.order.dto;

import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.entity.OrderMenu;
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

    // MemberDto 추가 예정

    // StoreDto 추가 예정

    private List<OrderMenuDto> orderMenus = new ArrayList<>();


    private FindPaysResponse(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = order.getCreatedAt();

        for (OrderMenu orderMenu : order.getOrderMenus()) {
            addOrderMenu(orderMenu);
        }
    }

    public static FindPaysResponse createPayResponse(Order order) {
        return new FindPaysResponse(order);
    }

    private void addOrderMenu(OrderMenu orderMenu) {
        this.orderMenus.add(OrderMenuDto.createOrderMenu(orderMenu));
    }

}
