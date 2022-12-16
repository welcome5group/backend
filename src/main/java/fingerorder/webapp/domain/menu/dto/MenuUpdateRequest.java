package fingerorder.webapp.domain.menu.dto;

import fingerorder.webapp.domain.category.entity.Category;
import lombok.Builder;
import lombok.Data;

@Data
public class MenuUpdateRequest {

    private Long menuId;
    private String name;
    private String description;
    private int price;
    private String imageUrl;
    private Category category;

    @Builder
    public MenuUpdateRequest(Long menuId, String name, String description, int price,
        String imageUrl,
        Category category) {
        this.menuId = menuId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
