package fingerorder.webapp.service;

import static fingerorder.webapp.domain.menu.status.MenuStatus.ABLE;
import static org.assertj.core.api.Assertions.assertThat;

import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.menu.dto.menuquerydto.MenuAndCategory;
import fingerorder.webapp.domain.menu.entity.Menu;
import fingerorder.webapp.domain.menu.repository.MenuRepository;
import fingerorder.webapp.domain.menu.service.MenuQueryService;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MenuQueryServiceTest {

    @Autowired
	MenuQueryService menuQueryService;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    StoreRepository storeRepository;

    @Test
    void findMenuAndCategoryTest() {
        //given
        Store store = createStore("서울시", 10, "차이나");
        storeRepository.save(store);

        Category categoryA = new Category("메인 메뉴");
        categoryRepository.save(categoryA);
        Category categoryB = new Category("사이드 메뉴");
        categoryRepository.save(categoryB);

        Menu menuA = createMenu(10000, "탕수육입니다.", "aaa", "탕수육");
        Menu menuB = createMenu(1000, "공기밥입니다.", "bbb", "공기밥");
        menuRepository.save(menuA);
        menuRepository.save(menuB);

        menuA.changeCategory(categoryA);
        menuB.changeCategory(categoryB);

        store.addMenu(menuA);
        store.addMenu(menuB);
        //when
        List<MenuAndCategory> menuAndCategory = menuQueryService.findMenuAndCategory(store.getId());
        //then
        assertThat(menuAndCategory.get(0).getCategoryName()).isEqualTo("메인 메뉴");
        assertThat(menuAndCategory.get(0).getMenus().get(0).getMenuName()).isEqualTo("탕수육");
        assertThat(menuAndCategory.get(0).getMenus().get(0).getDescription()).isEqualTo("탕수육입니다.");
        assertThat(menuAndCategory.get(0).getMenus().get(0).getPrice()).isEqualTo(10000);
        assertThat(menuAndCategory.get(0).getMenus().get(0).getImageUrl()).isEqualTo("aaa");
        assertThat(menuAndCategory.get(1).getCategoryName()).isEqualTo("사이드 메뉴");
        assertThat(menuAndCategory.get(1).getMenus().get(0).getMenuName()).isEqualTo("공기밥");
        assertThat(menuAndCategory.get(1).getMenus().get(0).getDescription()).isEqualTo("공기밥입니다.");
        assertThat(menuAndCategory.get(1).getMenus().get(0).getPrice()).isEqualTo(1000);
        assertThat(menuAndCategory.get(1).getMenus().get(0).getImageUrl()).isEqualTo("bbb");

    }


    private static Menu createMenu(Integer price, String description, String imageUrl,
        String menuName) {

        return Menu.builder()
            .price(price)
            .description(description)
            .imageUrl(imageUrl)
            .name(menuName)
            .status(ABLE)
            .build();
    }


    private static Store createStore(String storeLocation, Integer tableCount, String name) {

        return Store.builder()
            .storeLocation(storeLocation)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .tableCount(tableCount)
            .name(name)
            .build();
    }


}