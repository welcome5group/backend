package fingerorder.webapp.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuDto {
    String name;
    Integer menuNum;
}