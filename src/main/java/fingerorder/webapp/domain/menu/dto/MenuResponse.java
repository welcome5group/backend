package fingerorder.webapp.domain.menu.dto;

import fingerorder.webapp.domain.category.entity.Category;
import lombok.Data;

@Data
public class MenuResponse {

    private Long storeId;
    private Long menuId;
    private String name;
    private String description;
    private int price;
    private String imageUrl;
    private Category category;


}
