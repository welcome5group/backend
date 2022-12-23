package fingerorder.webapp.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuInfo {

	private String name;
	private int price;
	private int count;

}
