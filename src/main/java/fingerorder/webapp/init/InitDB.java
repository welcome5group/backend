//package fingerorder.webapp.init;
//
//import static fingerorder.webapp.domain.member.status.UserStatus.ACTIVATE;
//import static fingerorder.webapp.domain.member.status.UserType.MEMBER;
//import static fingerorder.webapp.domain.member.status.UserType.MERCHANT;
//
//import fingerorder.webapp.domain.member.entity.Member;
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//public class InitDB {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//
//        public void dbInit1() {
//            System.out.println("Init1" + this.getClass());
//            Member member1 = new Member("wlscww@kakao.com", "1216dltncks!A", "suzhn",
//                ACTIVATE, MEMBER);
//            em.persist(member1);
//
//            Member member2 = new Member("wlscww@naver.com", "1216dltncks!Aaa", "suzahn",
//                ACTIVATE, MERCHANT);
//            em.persist(member2);
//        }
//
//
//    }
//}
//
