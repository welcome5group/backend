package fingerorder.webapp.repository;

import fingerorder.webapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query("select c from Category c ")
//    List<Category> findCategories();

}
