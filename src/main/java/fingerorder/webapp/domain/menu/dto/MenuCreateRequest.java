package fingerorder.webapp.domain.menu.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuCreateRequest {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣0-9 ()].+$")
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Integer price;
    @NotBlank
    private String imageUrl;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣0-9 ()].+$")
    private String categoryName;

    @NotBlank
    private String menuStatus;

    @Builder
    public MenuCreateRequest(String name, String description, int price,
        String imageUrl,
        String categoryName, String menuStatus) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
        this.menuStatus = menuStatus;
    }
}
