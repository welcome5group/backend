package fingerorder.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreUpdateRequest {

    //    private Long memberId; 우선은 필요없으니 나중에 추가하든지 하자
    private Long storeId;
    private String name;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
    private int tableCount;
    private String storeLocation;
}
