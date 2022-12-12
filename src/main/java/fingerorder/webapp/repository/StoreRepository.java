package fingerorder.webapp.repository;

<<<<<<< HEAD
import fingerorder.webapp.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {


    Store findByName(String name);

//    @Query("select c from Store s join fetch s.categories c where s.id =: id")
//    Optional<Category> findCategory(@Param("id") Long id);
//
//
//    @Query("select c from Store s join fetch s.categories c")
//    List<Category> findCategoriesV1(Long id);
//
//    @Query("select c from Store s join s.categories c")
//    List<Category> findCategoriesV2(Long id);

    List<Store> findAllByMemberId(Long id);

=======
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
>>>>>>> a420c83 (feat: 카테고리 CRUD 기능 구현 및 테스트)

}
