package fingerorder.webapp.domain.store.repository;

import fingerorder.webapp.domain.store.entity.Store;
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

//    @Query(value = "select c from Store s on s.id = :id join fetch s.categories c where c.name = :name", nativeQuery = true)
//    Optional<Category> findCategory(@Param("id") Long storeId, @Param("name") String categoryName);

    // storeId x번인 스토어 안에 {'메인메뉴' '사이드메뉴'} 중 '메인메뉴' 만
    // fetchone()


}
