package fingerorder.webapp.domain.order.dto;

import fingerorder.webapp.domain.order.entity.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveOrderResponse {

    private Long ordersId;


    private SaveOrderResponse(Order order) {
        this.ordersId = order.getId();
    }

    private SaveOrderResponse(Long id) {
        this.ordersId = id;
    }

    public static SaveOrderResponse saveOrderResponse(Long id) {
        return new SaveOrderResponse(id);
    }

}
