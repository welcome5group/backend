package fingerorder.webapp.domain.order.dto;

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
    private String storeName;
    private String orderStatus;
    private LocalDateTime orderDate;
    private Integer totalPrice;
    private List<MenuDto> menuList = new ArrayList<>();

    public OrderListResponse(String storeName, Enum orderStatus, LocalDateTime orderDate,
        Integer totalPrice) {
        this.storeName = storeName;
        this.orderStatus = String.valueOf(orderStatus);
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}