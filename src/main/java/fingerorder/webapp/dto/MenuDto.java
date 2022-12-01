package fingerorder.webapp.dto;

import fingerorder.webapp.entity.Category;
import lombok.Data;

@Data
public class MenuDto {

    private String name;
    private String description;
    private int price;
    private String imageUrl;
    private Category category;


}
