package fingerorder.webapp.service;

import fingerorder.webapp.dto.OrderMenuRequestDto;
import fingerorder.webapp.dto.OrderRequestDto;
import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Menu;
import fingerorder.webapp.entity.Order;
import fingerorder.webapp.entity.OrderMenu;
import fingerorder.webapp.entity.Store;
import fingerorder.webapp.repository.MemberRepository;
import fingerorder.webapp.repository.MenuRepository;
import fingerorder.webapp.repository.OrderMenuRepository;
import fingerorder.webapp.repository.OrderRepository;
import fingerorder.webapp.repository.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Transactional
    public Long save(final OrderRequestDto orderRequestDto) {
        Member member = findMemberById(orderRequestDto.getMemberId());
        Store store = findStoreById(orderRequestDto.getStoreId());
        List<OrderMenu> orderMenus = createOrderMenus(orderRequestDto.getOrderMenus());

        Order order = Order.createOrder(member, store, orderMenus);

        return orderRepository.save(order).getId();
    }

    private List<OrderMenu> createOrderMenus(List<OrderMenuRequestDto> orderMenusDto) {
        List<OrderMenu> orderMenus = new ArrayList<>();

        for (OrderMenuRequestDto orderMenu : orderMenusDto) {
            Menu menu = findMenuById(orderMenu.getMenuId());
            orderMenus.add(OrderMenu.createOrderMenu(menu, orderMenu.getCount()));
        }

        return orderMenus;
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

}
