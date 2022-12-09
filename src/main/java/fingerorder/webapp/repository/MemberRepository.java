package fingerorder.webapp.repository;

import fingerorder.webapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickname(String name);


}
