package fingerorder.webapp.domain.menu.dto.menuquerydto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuInCategory {

    private String menuName;
    private String description;
    private int price;
    private String imageUrl;

}
