package fingerorder.webapp.domain.menu.dto;

import lombok.Data;

@Data
public class MenuResponse {

    private Long storeId;
    private Long menuId;
    private String name;
    private String description;
    private int price;
    private String imageUrl;
    private String categoryName;
    private String menuStatus;

}
