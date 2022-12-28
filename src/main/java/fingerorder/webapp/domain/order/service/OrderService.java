package fingerorder.webapp.domain.order.service;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.menu.entity.Menu;
import fingerorder.webapp.domain.menu.repository.MenuRepository;
import fingerorder.webapp.domain.order.dto.GetIncompOrdersResponse;
import fingerorder.webapp.domain.order.dto.OrderListResponse;
import fingerorder.webapp.domain.order.dto.OrderMenuDto;
import fingerorder.webapp.domain.order.dto.SaveOrderRequest;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import fingerorder.webapp.domain.order.status.OrderStatus;
import fingerorder.webapp.domain.order.status.ReviewStatus;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public ResponseEntity<?> save(final SaveOrderRequest request) {

        checkEmptyOrderMenus(request.getOrderMenus());

        Member member = findMemberById(request.getMemberId());
        Store store = findStoreById(request.getStoreId());
        List<OrderMenu> orderMenus = createOrderMenus(
            request.getOrderMenus());

        Order order = Order.createOrder(member, store, OrderStatus.INCOMP, ReviewStatus.INCOMP,
            orderMenus);

        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<OrderMenu> createOrderMenus(List<OrderMenuDto> orderMenusDto) {
        List<OrderMenu> orderMenus = new ArrayList<>();

        for (OrderMenuDto orderMenuDto : orderMenusDto) {
            Menu menu = findMenuById(orderMenuDto.getId());
            orderMenus.add(OrderMenu.createOrderMenu(menu, orderMenuDto.getCount()));
        }

        return orderMenus;
    }

    public List<GetIncompOrdersResponse> getIncompOrders(Long storeId) {
        List<Order> getOrders = orderRepository.findAllByStoreIdAndOrderStatus(storeId,
                OrderStatus.INCOMP)
            .orElseThrow(() -> new RuntimeException());

        List<GetIncompOrdersResponse> orders = new ArrayList<>();
        for (Order getOrder : getOrders) {
            orders.add(new GetIncompOrdersResponse(getOrder));
        }

        return orders;
    }

    @Transactional
    public void editOrderStatus(Long orderId) {
        Order order = findOrderById(orderId);

        order.editOrderStatus(OrderStatus.COMP);
    }

    private Menu findMenuById(Long id) {
        return menuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    private Store findStoreById(Long id) {
        return storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    private void checkEmptyOrderMenus(List<OrderMenuDto> orderMenus) {
        if (orderMenus.isEmpty()) {
            throw new RuntimeException("주문한 메뉴가 없습니다.");
        }
    }

    public List<OrderListResponse> getOrderList(Long memberId) {
        LocalDateTime date = LocalDateTime.now().minusDays(3);
        Sort sort = sortByDate();
        List<Order> orderList = this.orderRepository.findByMemberIdAndDate(memberId,date,sort)
            .orElseThrow(() -> new RuntimeException());

        List<OrderListResponse> orderListResponses = new ArrayList<>();

        for (Order order : orderList) {
            OrderListResponse orderListItem = OrderListResponse.builder()
                .storeId(order.getStore().getId())
                .storeName(order.getStore().getName())
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .reviewStatus(order.getReviewStatus())
                .orderDate(order.getCreatedAt())
                .totalPrice(order.getTotalPrice())
                .build();
            orderListItem.insertMenu(order.getOrderMenus());
            orderListResponses.add(orderListItem);
        }
        return orderListResponses;
    }

    private Sort sortByDate() {
        return Sort.by(Sort.Direction.DESC,"createdAt");
    }
}
