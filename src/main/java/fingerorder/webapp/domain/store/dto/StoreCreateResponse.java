package fingerorder.webapp.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreCreateResponse {

    private Long storeId;
    private String name;
    private String location;
    private String orderNumber = "";
    private String tableNumber = "";


}
