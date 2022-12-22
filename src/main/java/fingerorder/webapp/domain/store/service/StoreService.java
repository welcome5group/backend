package fingerorder.webapp.domain.store.service;

import fingerorder.webapp.domain.store.dto.PaymentDatailsRequestDto;
import fingerorder.webapp.domain.store.dto.PaymentDetailsResponseDto;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import fingerorder.webapp.domain.category.exception.StoreNotFoundException;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.exception.MemberFindException;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import fingerorder.webapp.domain.store.dto.StoreCreateRequest;
import fingerorder.webapp.domain.store.dto.StoreLookUpOrderResponse;
import fingerorder.webapp.domain.store.dto.StoreResponse;
import fingerorder.webapp.domain.store.dto.StoreUpdateRequest;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.exception.StoreFindException;
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
//    private final SalesQueryRepository salesQueryRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public StoreResponse registerStore(StoreCreateRequest storeCreateRequest) { // 가게 생성

        Store store = new Store(storeCreateRequest);
        Store savedStore = storeRepository.save(store);
        Member member = memberRepository.findById(storeCreateRequest.getMemberId())
            .orElseThrow(MemberFindException::new);
        member.addStore(savedStore);
        return savedStore.toStoreRequest(savedStore);
    }

    @Transactional
    public StoreResponse updateStore(StoreUpdateRequest storeUpdateRequest, Long storeId) {

        Store store = storeRepository.findById(storeId)
            .orElseThrow(StoreFindException::new);
        store.changeStore(storeUpdateRequest);
        return store.toStoreRequest(store);
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

        for (Order order : orders) {
            List<OrderMenu> orderMenus = order.getOrderMenus();
        }

        return storeLookUpOrderResponse;
    }

//    public List<PaymentDetailsResponseDto> findSalesForMonth(
//        PaymentDatailsRequestDto paymentDatailsRequestDto) {
//        List<PaymentDetailsResponseDto> orders = salesQueryRepository.findOrders(
//            paymentDatailsRequestDto.getStoreId(), paymentDatailsRequestDto.getYear(), paymentDatailsRequestDto.getMonth());
//
//        if(orders.isEmpty()) {
//            throw new RuntimeException("해당 년월의 매출 내역이 존재하지 않습니다.");
//        }
//
//        return orders;
//    }
}
