package fingerorder.webapp.dto;

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

    private UserDto userDto;

    private StoreDto storeDto;

    private List<OrderMenuDto> orderMenuDtoList = new ArrayList<>();

}
