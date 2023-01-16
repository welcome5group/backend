package fingerorder.webapp.domain.menu.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EntityListeners(TrimEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
public class Menu {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "menu_id")
    private Long id;
    @Trim
    private String name;
    @Trim
    private String description;
    private int price;
    private String imageUrl;

    @Enumerated(STRING)
    private MenuStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = LAZY)
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

    public Menu updateMenu(MenuUpdateRequest menuUpdateRequest, Category category) {

        this.name = menuUpdateRequest.getName();
        this.description = menuUpdateRequest.getDescription();
        this.price = menuUpdateRequest.getPrice();
        this.imageUrl = menuUpdateRequest.getImageUrl();
        this.status = Enum.valueOf(MenuStatus.class, menuUpdateRequest.getMenuStatus());
        this.category = category;
        return this;
    }

    public void editStatus() {
        this.status = MenuStatus.SOLDOUT;
    }

    public MenuResponse toMenuResponse(Menu menu) { // response, request 로 모든 dto 변환

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

    public void addStore(Store store) { //비즈니스 메서드
        this.store = store;
        store.getMenus().add(this);
    }

    public void addCategory(Category category) {
        this.category = category;
        category.getMenus().add(this);
    }

    public void editProfileImg(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
