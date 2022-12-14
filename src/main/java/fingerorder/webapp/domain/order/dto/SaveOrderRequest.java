package fingerorder.webapp.domain.order.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveOrderRequest {

    private Long memberId;
    private Long storeId;

    private List<OrderMenuDto> orderMenus;

}
