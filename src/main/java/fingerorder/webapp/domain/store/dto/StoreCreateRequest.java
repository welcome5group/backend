package fingerorder.webapp.domain.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreCreateRequest {

    //    @NotNull
    private Long memberId; //사장 아이디
    //    @NotEmpty
    private String name; //가게 이름

    //    @NotEmpty
    private Integer tableCount;

    //    @NotEmpty
    private String storeLocation;


    public StoreCreateRequest(Long memberId, String name, Integer tableCount,
        String storeLocation) {
        this.memberId = memberId;
        this.name = name;
        this.tableCount = tableCount;
        this.storeLocation = storeLocation;
    }
    //    public StoreDto(String name, LocalDateTime createdAt, LocalDateTime updatedAt, int tableCount,
//        String storeLocation) { // 가게 수정
//        this.name = name;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.tableCount = tableCount;
//        this.storeLocation = storeLocation;
//    }
}
