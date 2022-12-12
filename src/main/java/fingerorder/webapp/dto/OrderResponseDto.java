package fingerorder.webapp.dto;

import fingerorder.webapp.entity.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderResponseDto {

    private Long ordersId;


    private OrderResponseDto(Order order) {
        this.ordersId = order.getId();
    }

    private OrderResponseDto(Long id) {
        this.ordersId = id;
    }

    public static OrderResponseDto saveOrderResponse(Long id) {
        return new OrderResponseDto(id);
    }

}
