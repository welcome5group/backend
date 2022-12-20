package fingerorder.webapp.domain.store.service;

import fingerorder.webapp.domain.category.exception.StoreNotFoundException;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import fingerorder.webapp.domain.store.dto.StoreLookUpOrderResponse;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import fingerorder.webapp.domain.store.dto.StoreCreateRequest;
import fingerorder.webapp.domain.store.dto.StoreResponse;
import fingerorder.webapp.domain.store.dto.StoreUpdateRequest;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public StoreResponse registerStore(StoreCreateRequest storeCreateRequest) { // 가게 생성

        Store store = new Store(storeCreateRequest);
        Store savedStore = storeRepository.save(store);
        Member member = memberRepository.findById(storeCreateRequest.getMemberId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        member.addStore(savedStore);
        return savedStore.toStoreRequestDto(savedStore);
    }

    @Transactional
    public StoreResponse updateStore(StoreUpdateRequest storeUpdateRequest) {

        Long storeId = storeUpdateRequest.getStoreId();
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));
        store.changeStore(storeUpdateRequest);
        return store.toStoreRequestDto(store);
    }

    @Transactional
    public void deleteStore(Long storeId) {

        storeRepository.deleteById(storeId);
    }

    public List<StoreResponse> findAllStores(Long memberId) { //memberId

        List<Store> stores = storeRepository.findAllByMemberId(memberId);
        return stores.stream()
            .map(s -> new StoreResponse(s.getId(), s.getName(), s.getStoreLocation())).collect(
                Collectors.toList());

    }

    public StoreLookUpOrderResponse getOrders(Long id) {
        Store findStore = storeRepository.findById(id)
            .orElseThrow(StoreNotFoundException::new);

        List<Order> orders = orderRepository.findAllByStore(findStore);
        StoreLookUpOrderResponse storeLookUpOrderResponse =
            new StoreLookUpOrderResponse(findStore.getName(), findStore.getStoreLocation());

        for (Order order: orders) {
            HashMap<String, Integer> menus = (HashMap<String, Integer>)
                order.getOrderMenus().stream().
                    collect(Collectors.toMap(OrderMenu::getName,OrderMenu::getCount));
            storeLookUpOrderResponse.insertOrder(order,menus);
        }

        return storeLookUpOrderResponse;
    }
}
