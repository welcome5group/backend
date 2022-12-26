package fingerorder.webapp.domain.order.dto;

import fingerorder.webapp.domain.member.dto.MemberDto;
import fingerorder.webapp.domain.store.dto.StoreUpdateResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private Long id;

    private int totalPrice;

    private LocalDateTime createdAt;

    private MemberDto userDto;

    private StoreUpdateResponse storeDto;

    private List<OrderMenuDto> orderMenuDtoList = new ArrayList<>();

}
