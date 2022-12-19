package fingerorder.webapp.service;

import fingerorder.webapp.dto.PayResponseDto;
import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Order;
import fingerorder.webapp.repository.MemberRepository;
import fingerorder.webapp.repository.OrderRepository;
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
    public List<PayResponseDto> findPayList(Long memberId) {
        Member member = findMemberById(memberId);

        List<Order> orders = orderRepository.findAllByMember(member);

        List<PayResponseDto> pays = new ArrayList<>();
        for (Order order : orders) {
            pays.add(PayResponseDto.createPayResponse(order));
        }

        return pays;
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException());
    }

}
