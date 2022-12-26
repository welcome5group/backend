package fingerorder.webapp.domain.store.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreLookUpResponse {

    private Long storeId;
    private String name;
    private String location;
    private LocalDateTime updatedAt;
    private Integer tableCount;
    private String orderNumber = "";
    private String tableNumber = "";

}
