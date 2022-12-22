package fingerorder.webapp.service;

import static fingerorder.webapp.domain.member.status.UserType.MERCHANT;
import static fingerorder.webapp.domain.menu.status.MenuStatus.ABLE;
import static fingerorder.webapp.domain.member.status.UserStatus.ACTIVATE;
import static org.assertj.core.api.Assertions.assertThat;

import fingerorder.webapp.domain.store.service.StoreService;
import fingerorder.webapp.dto.request.create.StoreCreateRequest;
import fingerorder.webapp.dto.request.update.StoreUpdateRequest;
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
import java.util.List;
import java.util.Optional;
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

        StoreCreateRequest storeCreateRequest = new StoreCreateRequest(savedMember.getId(), "중국집",3,"서울시");
        //when
        storeService.registerStore(storeCreateRequest);
        Store store = storeRepository.findByName(storeCreateRequest.getName());
        //then
        assertThat(store.getName()).isEqualTo(storeCreateRequest.getName());
        assertThat(store.getStoreLocation()).isEqualTo(storeCreateRequest.getStoreLocation());
//        assertThat(store.getCreatedAt()).isEqualTo(store.showCreateAt());
        assertThat(savedMember.getId()).isEqualTo(storeCreateRequest.getMemberId());
    }

    private static Member createMember() { //빌더 패턴은 new arrayList 초기화 안해줌 -> 트러블 슈팅에 넣기
        return Member.builder()
            .email("wlscww@kakao.com")
            .nickName("suzhan")
            .password("1234")
            .userType(MERCHANT)
            .status(ACTIVATE)
            .stores(new ArrayList<>())
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

