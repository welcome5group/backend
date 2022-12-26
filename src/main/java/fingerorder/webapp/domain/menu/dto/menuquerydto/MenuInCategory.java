package fingerorder.webapp.domain.menu.dto.menuquerydto;

import lombok.Data;

@Data
public class MenuInCategory {


    private Long menuId;
    private String menuName;
    private String description;
    private int price;
    private String imageUrl;
    private String menuStatus;

    public MenuInCategory(Long menuId, String menuName, String description, int price,
        String imageUrl,
        Enum menuStatus) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.menuStatus = String.valueOf(menuStatus);
    }
}
