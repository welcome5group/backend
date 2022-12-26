package fingerorder.webapp.domain.order.service;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.order.dto.MenuResponse;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMenuQueryService {
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final EntityManager em;

	public void getOrderList(Long memberId) {
		Member findMember = this.memberRepository.findById(memberId)
			.orElseThrow(()-> new RuntimeException());

		List<Order> orderList = orderRepository.findAllByMember(findMember);

		for (Order order : orderList) {
			order.getOrderStatus();
			order.getTotalPrice();
			order.getCreatedAt();
			order.getId();
			
			for (OrderMenu orderMenu : order.getOrderMenus()) {
				orderMenu.getMenu().getName();
				orderMenu.getCount();
			}
			em.createQuery(
				"select new fingerorder.webapp.domain.order.dto.MenuResponse(om.menu.name,om.count)"
					+ "from OrderMenu om" , MenuResponse.class)
				.getResultList();

		}
	}
}
