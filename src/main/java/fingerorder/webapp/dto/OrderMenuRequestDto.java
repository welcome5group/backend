package fingerorder.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderMenuRequestDto {

    private Long menuId;
    private int count;

}
