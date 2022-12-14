package fingerorder.webapp.domain.store.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreCreateRequest {
    @NotNull
    private Long memberId; //사장 아이디
    @NotBlank
    private String name; //가게 이름
    @NotNull
    private Integer tableCount;
    @NotBlank
    private String storeLocation;

    private String orderNumber;

    private String tableNumber;

    public StoreCreateRequest(Long memberId, String name, Integer tableCount,
        String storeLocation) {

        this.memberId = memberId;
        this.name = name;
        this.tableCount = tableCount;
        this.storeLocation = storeLocation;
    }
}
