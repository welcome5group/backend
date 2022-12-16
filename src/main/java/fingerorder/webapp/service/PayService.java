package fingerorder.webapp.service;

import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Order;
import fingerorder.webapp.repository.MemberRepository;
import fingerorder.webapp.repository.OrderMenuRepository;
import fingerorder.webapp.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Transactional
    public void findPayList(Long memberId) {
        Member member = findMemberById(memberId);

        List<Order> orders = orderRepository.findAllByMember(member);
        // response에 넣기

    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException());
    }


}
