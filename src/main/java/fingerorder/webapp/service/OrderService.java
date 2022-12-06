package fingerorder.webapp.service;

import fingerorder.webapp.dto.OrderRequestDto;
import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Store;
import fingerorder.webapp.repository.MemberRepository;
import fingerorder.webapp.repository.OrderRepository;
import fingerorder.webapp.repository.StoreRepository;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final EntityManager em;

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Long save(final OrderRequestDto params) {

        Optional<Member> optionalMember = memberRepository.findById(params.getMemberId());
        Member member = optionalMember.orElseThrow(() -> new NullPointerException());

        Optional<Store> optionalStore = storeRepository.findById(params.getStoreId());
        Store store = optionalStore.orElseThrow(() -> new NullPointerException());

        return orderRepository.save(params.toEntity(member, store)).getId();
    }

}
