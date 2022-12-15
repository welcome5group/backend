package fingerorder.webapp.domain.category.dto;

import fingerorder.webapp.entity.Category;
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
