package fingerorder.webapp.service;

import static fingerorder.webapp.domain.member.status.UserType.MERCHANT;
import static fingerorder.webapp.domain.menu.status.MenuStatus.ABLE;
import static fingerorder.webapp.domain.menu.status.MenuStatus.ENABLE;
import static fingerorder.webapp.domain.member.status.UserStatus.ACTIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import fingerorder.webapp.domain.menu.service.MenuService;
import fingerorder.webapp.dto.request.create.MenuCreateRequest;
import fingerorder.webapp.dto.response.MenuResponse;
import fingerorder.webapp.dto.request.update.MenuUpdateRequest;
import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.menu.entity.Menu;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.menu.repository.MenuRepository;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MenuServiceTest {

    @Autowired
    MenuService menuService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void registerMenuTest() {
        //given

        Store store = createStore("서울시", 10, "차이나");
        Store savedStore = storeRepository.save(store);

        Category category = new Category("메인 메뉴");
        Category savedCategory = categoryRepository.save(category);

        MenuCreateRequest menuCreateRequest = MenuCreateRequest
            .builder()
            .description("탕수육 입니다.")
            .imageUrl("aaa")
            .name("탕수육")
            .price(10000)
            .build();

        //카테고리에 아직 메뉴 안넣었음 어떻게 넣어야 하지????? requestDTO에서는 제외했다

        //when
        MenuResponse menuResponse = menuService.registerMenu(menuCreateRequest, savedStore.getId());

        //then
        assertThat(menuResponse.getStoreId()).isEqualTo(savedStore.getId());
        assertThat(menuResponse.getMenuId()).isEqualTo(
            menuRepository.findByName(menuCreateRequest.getName()).getId());
        assertThat(menuResponse.getName()).isEqualTo("탕수육");
        assertThat(menuResponse.getDescription()).isEqualTo("탕수육 입니다.");
        assertThat(menuResponse.getPrice()).isEqualTo(10000);
        assertThat(menuResponse.getImageUrl()).isEqualTo("aaa");
        assertThat(menuResponse.getCategory()).isEqualTo(savedCategory);
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

    @Test
    void updateMenuTest() {
        //given
        Store store = createStore("서울시", 10, "차이나");
        Store savedStore = storeRepository.save(store);

        Menu menu = createMenu(5000, "짬뽕 입니다.", "aaa", "짬뽕");
        Menu savedMenu = menuRepository.save(menu);

        Category category = new Category("메인 메뉴");
        Category savedCategory = categoryRepository.save(category);

        store.addMenu(savedMenu);
        store.addCategory(savedCategory);

        MenuUpdateRequest menuUpdateRequest = MenuUpdateRequest
            .builder()
            .menuId(savedMenu.getId())
            .name("탕수육")
            .description("탕수육 입니다.")
            .price(10000)
            .imageUrl("aaa")
//            .category(savedCategory)
            .build();

        //when
        MenuResponse menuResponse = menuService.updateMenu(menuUpdateRequest);

        //then
        assertThat(menuResponse.getStoreId()).isEqualTo(menu.getStore().getId());
        assertThat(menuResponse.getMenuId()).isEqualTo(savedMenu.getId());
        assertThat(menuResponse.getName()).isEqualTo("탕수육");
        assertThat(menuResponse.getDescription()).isEqualTo("탕수육 입니다.");
        assertThat(menuResponse.getPrice()).isEqualTo(10000);
        assertThat(menuResponse.getImageUrl()).isEqualTo("aaa");
//        assertThat(menuResponse.getCategory()).isEqualTo(savedCategory);

    }

    @Test
    void deleteMenuTest() {
        //given
        Member member = createMember();
        Member savedMember = memberRepository.save(member);

        Store store = createStore("서울시",10,"차이나");
        Store savedStore = storeRepository.save(store);

        member.addStore(savedStore);
        Menu menu = createMenu(20000, "탕수육입니다.", "aaa", "탕수육");
        Menu savedMenu = menuRepository.save(menu);
        store.addMenu(savedMenu);

        //when
        menuService.deleteMenu(savedMenu.getId());
        Optional<Menu> findMenu = menuRepository.findById(savedMenu.getId());

        //then
        assertThat(findMenu).isEmpty();
    }
    private static Member createMember() {
        return Member.builder()
            .email("wlscww@kakao.com")
            .nickName("suzhan")
            .password("1234")
            .userType(MERCHANT)
            .status(ACTIVATE)
            .stores(new ArrayList<>())
            .build();
    }

    private static Menu createMenu(Integer price, String description, String imageUrl, String menuName) {
        return Menu.builder()
            .price(price)
            .description(description)
            .imageUrl(imageUrl)
            .name(menuName)
            .status(ABLE)
            .build();
    }

    @Test
    void menuDisableTest() {
        //given
        Menu menu = createMenu(10000, "탕수육입니다.", "aaa", "탕수육");
        Menu savedMenu = menuRepository.save(menu);

        //when
        menuService.menuDisable(savedMenu.getId());

        //then
        assertThat(savedMenu.getStatus()).isEqualTo(ENABLE);
    }


}