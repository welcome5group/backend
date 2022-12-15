package fingerorder.webapp.service;

import fingerorder.webapp.dto.menuquerydto.MenuAndCategory;
import fingerorder.webapp.dto.menuquerydto.MenuInCategory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuQueryService {

    private final EntityManager em;

    @Transactional
    public List<MenuAndCategory> findMenuAndCategory(Long storeId) { // 전체 메뉴 조회인데, 카테고리에 해당하는 전체메뉴 조회
        List<MenuAndCategory> categories = findCategories();
        categories.forEach(c -> {
            List<MenuInCategory> menus = findMenus(c.getCategoryName(), storeId);
            c.setMenus(menus);
        });
        return categories;
    }


    private List<MenuAndCategory> findCategories() {
        return em.createQuery("select new fingerorder.webapp.dto.menuquerydto"
            + ".MenuAndCategory(c.name) "
            + "from Category c join c.menus", MenuAndCategory.class).getResultList();
    }

    private List<MenuInCategory> findMenus(String categoryName, Long storeId) {
        return em.createQuery("select new fingerorder.webapp.dto.menuquerydto"
                + ".MenuInCategory(m.name,m.description,m.price,m.imageUrl) "
                + "from Menu m "
                + "join m.store s "
                + "where s.id =:storeId "
                + "and m.category.name =: categoryName", MenuInCategory.class)
            .setParameter("storeId",storeId)
            .setParameter("categoryName", categoryName)
            .getResultList();
    }
}