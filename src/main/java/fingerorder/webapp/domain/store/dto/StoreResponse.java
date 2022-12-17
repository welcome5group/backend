package fingerorder.webapp.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreResponse {

    private Long storeId;
    private String name;
    private String Location;


}
