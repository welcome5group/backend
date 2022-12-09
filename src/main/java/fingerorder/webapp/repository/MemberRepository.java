package fingerorder.webapp.repository;

import fingerorder.webapp.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickName(String name);

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}


