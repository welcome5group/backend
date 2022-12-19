package fingerorder.webapp.domain.store.dto;

import lombok.Data;

@Data
public class StoreCreateRequest {

    private Long memberId; //사장 아이디
    private String name; //가게 이름
    private String storeLocation;

    public StoreCreateRequest(Long memberId, String name, String storeLocation) {
        this.memberId = memberId;
        this.name = name;
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
