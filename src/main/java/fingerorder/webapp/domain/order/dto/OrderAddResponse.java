package fingerorder.webapp.domain.order.dto;

import fingerorder.webapp.domain.order.entity.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderAddResponse {

    private Long ordersId;


    private OrderAddResponse(Order order) {
        this.ordersId = order.getId();
    }

    private OrderAddResponse(Long id) {
        this.ordersId = id;
    }

    public static OrderAddResponse saveOrderResponse(Long id) {
        return new OrderAddResponse(id);
    }

}
