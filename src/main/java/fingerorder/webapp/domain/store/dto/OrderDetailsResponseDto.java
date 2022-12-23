package fingerorder.webapp.domain.store.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDetailsResponseDto {

	private Long orderNum;
	private Integer tableNum;
	private Integer totalPrice;
	private LocalDateTime orderTime;
	private List<OrderMenuInfo> orderMenus;

//	@QueryProjection
	public OrderDetailsResponseDto(Long orderNum, Integer tableNum, Integer totalPrice, LocalDateTime orderTime, List<OrderMenuInfo> orderMenus) {
		this.orderNum = orderNum;
		this.tableNum = tableNum;
		this.totalPrice = totalPrice;
		this.orderTime = orderTime;
		this.orderMenus = orderMenus;
	}

}
