package fingerorder.webapp.repository;

import fingerorder.webapp.entity.Category;
import fingerorder.webapp.entity.Store;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	@Query(value = "select c from Category c join fetch c.store s where s.id = :id")
	Optional<List<Category>> findCategories(@Param("id") Long id);

}
