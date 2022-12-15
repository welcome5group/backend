package fingerorder.webapp.entity;

import fingerorder.webapp.dto.MenuCreateRequest;
import fingerorder.webapp.dto.MenuUpdateRequest;
import fingerorder.webapp.status.MenuStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
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


    public Menu(MenuCreateRequest menuCreateRequest) {
        this.name = menuCreateRequest.getName();
        this.description = menuCreateRequest.getDescription();
        this.price = menuCreateRequest.getPrice();
        this.imageUrl = menuCreateRequest.getImageUrl();
        this.category = menuCreateRequest.getCategory();
    }


    protected Menu() {

    }

    @Builder
    public Menu(String name, String description, int price, String imageUrl, MenuStatus status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public Menu updateMenu(MenuUpdateRequest menuUpdateRequest) {
        this.name = menuUpdateRequest.getName();
        this.description = menuUpdateRequest.getDescription();
        this.price = menuUpdateRequest.getPrice();
        this.imageUrl = menuUpdateRequest.getImageUrl();
        this.category = menuUpdateRequest.getCategory();
        return this;

    }

    public void changeStatus() {
        this.status = MenuStatus.ENABLE;
    }

    public MenuResponse toMenuResponse(Menu menu) {

        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setStoreId(menu.getStore().getId());
        menuResponse.setMenuId(menu.getId());
        menuResponse.setName(menu.getName());
        menuResponse.setPrice(menu.getPrice());
        menuResponse.setDescription(menu.getDescription());
        menuResponse.setImageUrl(menu.getImageUrl());
        menuResponse.setCategory(menu.getCategory());
        return menuResponse;
    }

    public void changeStore(Store store) { //비즈니스 메서드
        this.store = store;
        store.getMenus().add(this);
    }

    public void changeCategory(Category category) {
        this.category = category;
        category.getMenus().add(this);

    }
}
