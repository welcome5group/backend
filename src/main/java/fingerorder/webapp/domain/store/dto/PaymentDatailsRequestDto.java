package fingerorder.webapp.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDatailsRequestDto {

	private Long storeId;
	private int year;
	private int month;
}
