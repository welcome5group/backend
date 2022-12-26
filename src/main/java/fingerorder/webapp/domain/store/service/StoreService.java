package fingerorder.webapp.domain.store.service;

import fingerorder.webapp.domain.category.exception.StoreNotFoundException;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.exception.MemberFindException;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import fingerorder.webapp.domain.store.dto.OrderDetailsRequestDto;
import fingerorder.webapp.domain.store.dto.OrderDetailsResponseDto;
import fingerorder.webapp.domain.store.dto.OrderMenuInfo;
import fingerorder.webapp.domain.store.dto.PaymentDatailsRequestDto;
import fingerorder.webapp.domain.store.dto.PaymentDetailsResponseDto;
import fingerorder.webapp.domain.store.dto.StoreCreateRequest;
import fingerorder.webapp.domain.store.dto.StoreCreateResponse;
import fingerorder.webapp.domain.store.dto.StoreLookUpOrderResponse;
import fingerorder.webapp.domain.store.dto.StoreLookUpResponse;
import fingerorder.webapp.domain.store.dto.StoreUpdateRequest;
import fingerorder.webapp.domain.store.dto.StoreUpdateResponse;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.exception.StoreNotFindException;
import fingerorder.webapp.domain.store.repository.SalesQueryRepository;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final SalesQueryRepository salesQueryRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public StoreCreateResponse registerStore(StoreCreateRequest storeCreateRequest) { // 가게 생성

        Store store = new Store(storeCreateRequest);
        Store savedStore = storeRepository.save(store);
        Member member = memberRepository.findById(storeCreateRequest.getMemberId())
            .orElseThrow(MemberFindException::new);
        member.addStore(savedStore);

        String orderNumber = storeCreateRequest.getOrderNumber();
        String tableNumber = storeCreateRequest.getTableNumber();

        return savedStore.toStoreCreateResponse(savedStore, orderNumber, tableNumber);
    }

    @Transactional
    public StoreUpdateResponse updateStore(StoreUpdateRequest storeUpdateRequest, Long storeId) {

        Store store = storeRepository.findById(storeId)
            .orElseThrow(StoreNotFindException::new);
        store.changeStore(storeUpdateRequest);

        String orderNumber = storeUpdateRequest.getOrderNumber();
        String tableNumber = storeUpdateRequest.getTableNumber();
        return store.toStoreUpdateResponse(store, orderNumber, tableNumber);
    }

    @Transactional
    public void deleteStore(Long storeId) {
        storeRepository.deleteById(storeId);
    }

    public List<StoreLookUpResponse> findAllStores(Long memberId) { //memberId
        List<Store> stores = storeRepository.findAllByMemberId(memberId);
        return stores.stream()
            .map(s -> new StoreLookUpResponse(s.getId(), s.getName(), s.getStoreLocation(),
                s.getUpdatedAt(), s.getTableCount(), s.getOrderNumber(), s.getTableNumber()))
            .collect(Collectors.toList());
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

    public List<PaymentDetailsResponseDto> findPaymentsForMonth(
        PaymentDatailsRequestDto paymentDatailsRequestDto) {
        List<PaymentDetailsResponseDto> orders = salesQueryRepository.findOrders(
            paymentDatailsRequestDto.getStoreId(), paymentDatailsRequestDto.getYear(),
            paymentDatailsRequestDto.getMonth());

        return orders;
    }

    public List<OrderDetailsResponseDto> findOrderDetails(
        OrderDetailsRequestDto orderDetailsRequestDto) {
        List<Order> orders = salesQueryRepository.findOrders(
            orderDetailsRequestDto.getStoreId(), orderDetailsRequestDto.getStartDate(),
            orderDetailsRequestDto.getEndDate()
        );

        List<OrderDetailsResponseDto> orderDetailsResponseDtos = new ArrayList<>();

        for (Order order : orders) {
            List<OrderMenu> orderMenus = order.getOrderMenus();
            List<OrderMenuInfo> orderMenuInfos = new ArrayList<>();

            for (OrderMenu orderMenu : orderMenus) {
                OrderMenuInfo orderMenuInfo = new OrderMenuInfo(orderMenu.getMenu().getName(),
                    orderMenu.getTotalPrice(), orderMenu.getCount());
                orderMenuInfos.add(orderMenuInfo);
            }

            OrderDetailsResponseDto orderDetailsResponseDto = new OrderDetailsResponseDto(
                order.getId(), order.getTableNum(), order.getTotalPrice(), order.getCreatedAt(),
                orderMenuInfos
            );

            orderDetailsResponseDtos.add(orderDetailsResponseDto);
        }

        return orderDetailsResponseDtos;
    }
}
