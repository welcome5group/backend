package fingerorder.webapp.domain.member.repository;

import fingerorder.webapp.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickName(String name);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByUuid(String uuid);

    boolean existsByEmail(String email);

    List<Member> findAll();

    boolean existsByNickName(String nickName);
}


