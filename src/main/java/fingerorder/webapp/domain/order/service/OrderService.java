package fingerorder.webapp.domain.order.service;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.menu.entity.Menu;
import fingerorder.webapp.domain.menu.repository.MenuRepository;
import fingerorder.webapp.domain.order.dto.GetIncompOrdersResponse;
import fingerorder.webapp.domain.order.dto.OrderMenuDto;
import fingerorder.webapp.domain.order.dto.SaveOrderRequest;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import fingerorder.webapp.domain.order.status.OrderStatus;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public ResponseEntity<?> save(final SaveOrderRequest saveOrderRequest) {

        checkEmptyOrderMenus(saveOrderRequest.getOrderMenus());

        Member member = findMemberById(saveOrderRequest.getMemberId());
        Store store = findStoreById(saveOrderRequest.getStoreId());
        List<OrderMenu> orderMenus = createOrderMenus(
            saveOrderRequest.getOrderMenus());

        Order order = Order.createOrder(member, store, OrderStatus.INCOMP, orderMenus);

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
        List<Order> getOrders = orderRepository.findAllByStoreIdAndOrderStatus(storeId, OrderStatus.INCOMP)
            .orElseThrow(() -> new RuntimeException());

        List<GetIncompOrdersResponse> orders = new ArrayList<>();
        for (Order getOrder : getOrders) {
            orders.add(new GetIncompOrdersResponse(getOrder));
        }

        return orders;
    }

    private Menu findMenuById(Long id) {
        return menuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    private Store findStoreById(Long id) {
        return storeRepository.findById(id)
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

    //손님의 주문 내역 조회
    public void searchOrderMenuList(Long memberId) {






    }

}
