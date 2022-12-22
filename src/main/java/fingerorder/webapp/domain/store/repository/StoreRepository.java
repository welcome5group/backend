package fingerorder.webapp.domain.store.repository;

import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.store.entity.Store;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    Store findByName(String name);

    List<Store> findAllByMemberId(Long id);
}

