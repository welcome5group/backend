//package fingerorder.webapp.service;
//
//import fingerorder.webapp.entity.Category;
//import fingerorder.webapp.repository.StoreRepository;
//import java.util.List;
//import javax.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class StoreQueryService {
//
//    private final StoreRepository storeRepository;
//    private final EntityManager em;
//
//    public void findCategory(Long storeId, String categoryName) {
//        List categories = findCategories(storeId);
//
//        em.createQuery("select c from Category c where c.name =: categoryName")
//            .setParameter("categoryName",categoryName)
//            .getSingleResult();
//    }
//
//    private List findCategories(Long storeId) {
//
//        return em.createQuery("select c from Store s join fetch s.categories c where s.id =: id")
//            .setParameter("id",storeId)
//            .getResultList();
//
//    }
//
//
//
//}
