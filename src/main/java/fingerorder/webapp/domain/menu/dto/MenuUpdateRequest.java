package fingerorder.webapp.domain.menu.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuUpdateRequest {

    @NotNull
    private Long menuId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private int price;
    @NotEmpty
    private String imageUrl;

    private String categoryName;


    @Builder
    public MenuUpdateRequest(Long menuId, String name, String description, int price,
        String imageUrl, String categoryName) {
        this.menuId = menuId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }
}
