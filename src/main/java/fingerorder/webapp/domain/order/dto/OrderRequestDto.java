package fingerorder.webapp.domain.order.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderRequestDto {

    private Long memberId;
    private Long storeId;

    private List<OrderMenuRequestDto> orderMenus;

}
