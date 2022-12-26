package fingerorder.webapp.domain.order.dto;

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
    private String orderDate;
    private int totalPrice;
    private List<MenuDto> menuList = new ArrayList<>();

    public OrderListResponse(String storeName, String orderStatus, String orderDate,
        int totalPrice) {
        this.storeName = storeName;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public void insertMenu() {

    }
}
