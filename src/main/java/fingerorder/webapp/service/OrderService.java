//package fingerorder.webapp.service;
//
//import fingerorder.webapp.dto.OrderRequestDto;
//import fingerorder.webapp.entity.Member;
//import fingerorder.webapp.entity.Order;
//import fingerorder.webapp.entity.OrderMenu;
//import fingerorder.webapp.entity.Store;
//import fingerorder.webapp.repository.MemberRepository;
//import fingerorder.webapp.repository.OrderMenuRepository;
//import fingerorder.webapp.repository.OrderRepository;
//import fingerorder.webapp.repository.StoreRepository;
//import java.util.Optional;
//import javax.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class OrderService {
//
//    private final EntityManager em;
//
//    private final OrderRepository orderRepository;
//    private final OrderMenuRepository orderMenuRepository;
//    private final MemberRepository memberRepository;
//    private final StoreRepository storeRepository;
//
//    @Transactional
//    public Long save(final OrderRequestDto params) {
//
//
//        Optional<Member> member = memberRepository.findById(params.getMemberId());
//        Member member1 = member.get();
//
//        Optional<Store> store = storeRepository.findById(params.getStoreId());
//        Store store1 = store.get();
//
//        System.out.println("member1.getId() = " + member1.getId());
//        System.out.println("store1 = " + store1.getId());
//
//        Order entity = orderRepository.save(params.toEntity(member1, store1));
//
//
//        return entity.getId();
//    }
//
//}