package fingerorder.webapp.domain.store.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreCreateRequest {

    @NotBlank
    private Long memberId; //사장 아이디
    @NotBlank
    private String name; //가게 이름

    @NotBlank
    private Integer tableCount;

    @NotBlank
    private String storeLocation;


    public StoreCreateRequest(Long memberId, String name, Integer tableCount,
        String storeLocation) {
        this.memberId = memberId;
        this.name = name;
        this.tableCount = tableCount;
        this.storeLocation = storeLocation;
    }

}
