package fingerorder.webapp.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponseDto {

	private Long storeId;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;


}
