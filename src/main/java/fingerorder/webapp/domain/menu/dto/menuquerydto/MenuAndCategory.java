package fingerorder.webapp.domain.menu.dto.menuquerydto;

import java.util.List;
import lombok.Data;

@Data
public class MenuAndCategory {

    private String categoryName;
    private List<MenuInCategory> menus;

    public MenuAndCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
