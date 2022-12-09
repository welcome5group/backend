//package fingerorder.webapp.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import fingerorder.webapp.dto.OrderRequestDto;
//import fingerorder.webapp.entity.Member;
//import fingerorder.webapp.entity.Store;
//import fingerorder.webapp.repository.MemberRepository;
//import fingerorder.webapp.repository.StoreRepository;
//import java.time.LocalDateTime;
//import javax.persistence.EntityManager;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class OrderServiceTest {
//
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private EntityManager em;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private StoreRepository storeRepository;
//
//    @Test
//    void test1() {
//        //given
//        Member member = new Member();
//        Store store = new Store("aa", LocalDateTime.now(),LocalDateTime.now(),1,"1");
//
//        em.persist(member);
//        em.persist(store);
//
//        OrderRequestDto orderRequestDto = new OrderRequestDto(member.getId(), store.getId(), 1);
//
//
//        //when
//        //then
//    }
//
//
//
//}