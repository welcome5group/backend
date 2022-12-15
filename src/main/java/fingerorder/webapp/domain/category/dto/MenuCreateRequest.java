package fingerorder.webapp.dto;

import fingerorder.webapp.entity.Category;
import lombok.Builder;
import lombok.Data;

@Data
public class MenuCreateRequest {

    private Long storeId;
    private String name;
    private String description;
    private int price;
    private String imageUrl;
    private Category category;

    @Builder
    public MenuCreateRequest(Long storeId, String name, String description, int price,
        String imageUrl,
        Category category) {
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
