package fingerorder.webapp.repository;

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


}
