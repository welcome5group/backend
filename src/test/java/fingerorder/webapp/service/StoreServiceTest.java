package fingerorder.webapp.service;

import static fingerorder.webapp.entity.UserType.MERCHANT;
import static fingerorder.webapp.status.MenuStatus.ABLE;
import static fingerorder.webapp.status.UserStatus.ACTIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import fingerorder.webapp.dto.StoreCreateRequest;
import fingerorder.webapp.dto.StoreUpdateRequest;
import fingerorder.webapp.entity.Category;
import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Menu;
import fingerorder.webapp.entity.Store;
import fingerorder.webapp.repository.CategoryRepository;
import fingerorder.webapp.repository.MemberRepository;
import fingerorder.webapp.repository.MenuRepository;
import fingerorder.webapp.repository.StoreRepository;
import fingerorder.webapp.status.MenuStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.xml.catalog.Catalog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StoreServiceTest {
    @Autowired
    StoreService storeService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MenuRepository menuRepository;

    @Test
    void registerStoreTest() {
        //given
        Member member = createMember();

        Member savedMember = memberRepository.save(member);

        StoreCreateRequest storeCreateRequest = new StoreCreateRequest(savedMember.getId(), "중국집","서울시");
        //when
        storeService.registerStore(storeCreateRequest);
        Store store = storeRepository.findByName(storeCreateRequest.getName());
        //then
        assertThat(store.getName()).isEqualTo(storeCreateRequest.getName());
        assertThat(store.getStoreLocation()).isEqualTo(storeCreateRequest.getStoreLocation());
//        assertThat(store.getCreatedAt()).isEqualTo(store.showCreateAt());
        assertThat(savedMember.getId()).isEqualTo(storeCreateRequest.getMemberId());
    }

    private static Member createMember() {
        return Member.builder()
            .email("wlscww@kakao.com")
            .nickname("suzhan")
            .password("1234")
            .userType(MERCHANT)
            .status(ACTIVATE)
            .build();
    }

    @Test
    void updateStoreTest() {
        //given
        Store store = new Store("일식집",LocalDateTime.now(),LocalDateTime.now(),4,"수원시");
        Store savedStore = storeRepository.save(store);
        Long storeId = savedStore.getId();
        StoreUpdateRequest storeUpdateRequest = new StoreUpdateRequest(storeId,"중국집",10,"서울시");
        //when
        storeService.updateStore(storeUpdateRequest);
        //then
        Store updatedStore = storeRepository.findById(storeUpdateRequest.getStoreId())
            .orElseThrow(() -> new RuntimeException("존재하는 가게가 없습니다."));
        assertThat(updatedStore.getName()).isEqualTo("중국집");
        assertThat(updatedStore.getStoreLocation()).isEqualTo("서울시");
        assertThat(updatedStore.getTableCount()).isEqualTo(10);
//        assertThat(updatedStore.getCreatedAt()).isEqualTo(updatedStore.showCreateAt());
        //updateAt 이랑 createdAt test 코드 짜기


    }

    @Test
    @Transactional
    void deleteTest() {
        //given
        Store store = createStore("서울시", 10, "차이나");

        storeRepository.save(store);

        Category category = new Category("메인 메뉴");
        categoryRepository.save(category);

        Menu menu = createMenu();

        menuRepository.save(menu);
        menu.changeStore(store);
        menu.changeCategory(category);
        //when
        Long storeId = store.getId();
        storeRepository.deleteById(storeId);
        //then
        Optional<Store> findStore = storeRepository.findById(storeId);
        assertThat(findStore).isEmpty();

    }

    private static Menu createMenu() {
        return Menu.builder()
            .price(10000)
            .description("탕수육입니다.")
            .imageUrl("aaa")
            .name("탕수육")
            .status(ABLE)
            .build();
    }

    private static Store createStore(String storeLocation, int tableCount, String name) {
        return Store.builder()
            .storeLocation(storeLocation)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .tableCount(tableCount)
            .name(name)
            .build();
    }


    @Test
    void findAllStoresTest() {
        //given
        Member member = createMember();

        Member savedMember = memberRepository.save(member);

        Store store1 = createStore("서울시", 10, "차이나");

        storeRepository.save(store1);

        Store store2 = createStore("수원시", 11, "홍콩 반점");

        storeRepository.save(store2);
        member.addStore(store1);
        member.addStore(store2);

        //when
        List<Store> stores = storeRepository.findAllByMemberId(savedMember.getId());
        //then
        assertThat(stores.size()).isEqualTo(2);
        assertThat(stores.get(0).getName()).isEqualTo("차이나");
        assertThat(stores.get(0).getStoreLocation()).isEqualTo("서울시");
        assertThat(stores.get(0).getTableCount()).isEqualTo(10);
        assertThat(stores.get(1).getName()).isEqualTo("홍콩 반점");
        assertThat(stores.get(1).getStoreLocation()).isEqualTo("수원시");
        assertThat(stores.get(1).getTableCount()).isEqualTo(11);

    }
}

