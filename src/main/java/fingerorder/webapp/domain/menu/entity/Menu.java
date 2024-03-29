package fingerorder.webapp.domain.menu.entity;

import static javax.persistence.EnumType.STRING;

import fingerorder.webapp.annotation.Trim;
import fingerorder.webapp.annotation.TrimEntityListener;
import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.menu.dto.MenuCreateRequest;
import fingerorder.webapp.domain.menu.dto.MenuResponse;
import fingerorder.webapp.domain.menu.dto.MenuUpdateRequest;
import fingerorder.webapp.domain.menu.status.MenuStatus;
import fingerorder.webapp.domain.store.entity.Store;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
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
@EntityListeners(TrimEntityListener.class)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;
    @Trim
    private String name;
    @Trim
    private String description;
    private Integer price;
    private String imageUrl;

    @Enumerated(STRING)
    private MenuStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Menu(MenuCreateRequest menuCreateRequest, Category category) {
        this.name = menuCreateRequest.getName();
        this.description = menuCreateRequest.getDescription();
        this.price = menuCreateRequest.getPrice();
        this.imageUrl = menuCreateRequest.getImageUrl();
        this.status = Enum.valueOf(MenuStatus.class, menuCreateRequest.getMenuStatus());
        this.category = category;
    }

    protected Menu() {

    }

    @Builder
    public Menu(String name, String description, Integer price, String imageUrl,
        MenuStatus status, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
        this.category = category;
    }


    public Menu updateMenu(MenuUpdateRequest menuUpdateRequest, Category category) {

        this.name = menuUpdateRequest.getName();
        this.description = menuUpdateRequest.getDescription();
        this.price = menuUpdateRequest.getPrice();
        this.imageUrl = menuUpdateRequest.getImageUrl();
        this.status = Enum.valueOf(MenuStatus.class, menuUpdateRequest.getMenuStatus());
        this.category = category;

        return this;
    }

    public void changeStatus() {
        this.status = MenuStatus.SOLDOUT;
    }

    public MenuResponse toMenuResponse(Menu menu) {

        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setStoreId(menu.getStore().getId());
        menuResponse.setMenuId(menu.getId());
        menuResponse.setName(menu.getName());
        menuResponse.setPrice(menu.getPrice());
        menuResponse.setDescription(menu.getDescription());
        menuResponse.setImageUrl(menu.getImageUrl());
        menuResponse.setCategoryName(menu.getCategory().getName());
        menuResponse.setMenuStatus(String.valueOf(menu.getStatus()));

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


    public void add(Category category) { // dto에서 가져온 name으로 카테고리 가져와서 주입
        this.category = category;
    }

    public void editProfileImg(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
