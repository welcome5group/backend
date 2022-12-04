package fingerorder.webapp.repository;

import fingerorder.webapp.entity.Merchant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Long> {
	Optional<Merchant> findByEmail(String email);

	boolean existsByEmail(String email);
}
