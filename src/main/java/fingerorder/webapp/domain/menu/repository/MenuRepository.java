package fingerorder.webapp.domain.menu.repository;

import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByName(String menuName);

    void deleteByName(String menuName);

    Page<Menu> findMenusByCategory(Pageable pageable, Category category);

    @Modifying
    void deleteById(Long menuId);

}
