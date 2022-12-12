package fingerorder.webapp.repository;

import fingerorder.webapp.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
	Optional<Member> findByUuid(String uuid);
	boolean existsByEmail(String email);
}
