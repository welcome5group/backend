package fingerorder.webapp.domain.menu.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuCreateRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private Integer price;
    @NotEmpty
    private String imageUrl;
    @NotEmpty
    private String categoryName;

    @Builder
    public MenuCreateRequest(String name, String description, int price,
        String imageUrl,
        String categoryName) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }
}
