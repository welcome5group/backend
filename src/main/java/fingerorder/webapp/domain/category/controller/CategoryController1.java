package fingerorder.webapp.domain.category.controller;

import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.category.exception.CategoryNotFoundException;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController1 {

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;


    @Transactional
    @GetMapping("/api/category")
    public ResponseEntity<?> addCategory(@RequestParam String categoryName,
        @RequestParam Long storeId) {

        Category category = new Category(categoryName);
        Store store = storeRepository.findById(storeId).get();

        categoryRepository.save(category);

        category.setCategoryAndStore(store);

        Category category1 = categoryRepository.findByName(categoryName).orElseThrow(
            CategoryNotFoundException::new);

        System.out.println("category1 = " + category1);

        return new ResponseEntity<>(category.getName(), HttpStatus.OK);
    }

}
