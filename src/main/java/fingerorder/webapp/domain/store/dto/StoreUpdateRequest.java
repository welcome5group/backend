package fingerorder.webapp.domain.store.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreUpdateRequest {

    //    private Long memberId; 우선은 필요없으니 나중에 추가하든지 하자
    @NotBlank
    private String name;
    @NotBlank
    private Integer tableCount;
    @NotBlank
    private String storeLocation;
}
