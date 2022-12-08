package fingerorder.webapp.entity;

import fingerorder.webapp.dto.MenuDto;
import fingerorder.webapp.status.MenuStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;
    private String name;
    private String description;
    private int price;
    private String imageUrl;
    private int star;
    private MenuStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;



    public Menu(MenuDto menuDto) {
        this.name = menuDto.getName();
        this.description = menuDto.getDescription();
        this.price = menuDto.getPrice();
        this.imageUrl = menuDto.getImageUrl();
        this.category = menuDto.getCategory();
    }


    protected Menu() {

    }

    public Menu changeMenu(MenuDto menuDto) {
        this.name = menuDto.getName();
        this.description = menuDto.getDescription();
        this.price = menuDto.getPrice();
        this.imageUrl = menuDto.getImageUrl();
        this.category = menuDto.getCategory();
        return this;

    }
}
