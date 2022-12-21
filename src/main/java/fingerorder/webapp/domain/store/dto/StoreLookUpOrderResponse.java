package fingerorder.webapp.domain.store.dto;

import fingerorder.webapp.domain.order.entity.Order;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreLookUpOrderResponse {
	private String storeName;
	private String location;
	private List<OrderResponse> orders;

	public StoreLookUpOrderResponse(String name, String storeLocation) {
		this.storeName = name;
		this.location = storeLocation;
		this.orders = new ArrayList<>();
	}

	public void insertOrder(Order order,HashMap<String,Integer> menuList) {
		OrderResponse orderResponse
			= OrderResponse.builder()
			.totalPrice(order.getTotalPrice())
			.orderDate(order.getCreatedAt())
			.tableNum(order.getTableNum())
			.menus(menuList)
			.build();

		this.orders.add(orderResponse);
	}
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class OrderResponse{
		private int totalPrice;
		private LocalDateTime orderDate;
		private int tableNum;
		private HashMap<String, Integer> menus;
	}
}
