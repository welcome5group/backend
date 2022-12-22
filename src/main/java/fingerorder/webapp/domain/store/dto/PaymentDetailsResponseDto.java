package fingerorder.webapp.domain.store.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PaymentDetailsResponseDto {
	private String yyyymmdd;
	private int salesSum;

	@QueryProjection
	public PaymentDetailsResponseDto(String yyyymmdd, int salesSum) {
		this.yyyymmdd = yyyymmdd;
		this.salesSum = salesSum;
	}
}
