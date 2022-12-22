package fingerorder.webapp.domain.store.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreUpdateRequest {

    //    private Long memberId; 우선은 필요없으니 나중에 추가하든지 하자
    @NotEmpty
    private String name;
    @NotNull
    private Integer tableCount;
    @NotEmpty
    private String storeLocation;
}
