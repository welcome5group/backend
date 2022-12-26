package fingerorder.webapp.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuResponse {
	String name;
	Integer menuNum;
}