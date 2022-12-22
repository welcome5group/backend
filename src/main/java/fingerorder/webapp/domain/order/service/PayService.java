package fingerorder.webapp.domain.order.service;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.order.dto.FindPaysResponse;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<FindPaysResponse> findPayList(Long memberId) {
        Member member = findMemberById(memberId);

        List<Order> orders = orderRepository.findAllByMember(member);

        List<FindPaysResponse> pays = new ArrayList<>();
        for (Order order : orders) {
            pays.add(FindPaysResponse.createPayResponse(order, order.getStore()));
        }

        return pays;
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException());
    }

}
