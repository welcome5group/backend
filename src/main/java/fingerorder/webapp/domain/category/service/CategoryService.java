package fingerorder.webapp.domain.category.service;

import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.category.exception.CategoryNotFoundException;
import fingerorder.webapp.domain.category.exception.NoMatchingCategoryException;
import fingerorder.webapp.domain.category.exception.NoProperCategoryException;
import fingerorder.webapp.domain.category.exception.NoUniqueCategoryException;
import fingerorder.webapp.domain.category.exception.StoreNotFoundException;
import fingerorder.webapp.domain.category.repository.CategoryQueryRepository;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.category.vo.CategoriesVo;
import fingerorder.webapp.domain.category.vo.CategoryVo;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    public CategoryVo createCategory(Long storeId, String categoryName) {
        validateName(categoryName);
        isUnique(categoryName);

        Category category = new Category(categoryName);
        Store store = findStore(storeId);
        category.setCategoryAndStore(store);
        categoryRepository.save(category);

        return new CategoryVo(categoryName);
    }

    @Transactional(readOnly = true)
    public CategoriesVo getCategory(Long storeId) {
        List<Category> categories = findCategories(storeId);

        List<String> categoryNames = new ArrayList<>();

        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        return new CategoriesVo(categoryNames);
    }

    @Transactional
    public CategoryVo updateCategory(Long storeId, String categoryName, String updateName) {
        validateName(updateName);
        isUnique(updateName);

        Category category = findCategory(storeId, categoryName);
        category.editName(updateName);

        return new CategoryVo(updateName);
    }

    @Transactional
    public CategoryVo deleteCategory(Long storeId, String categoryName) {
        validateName(categoryName);

        Category category = findCategory(storeId, categoryName);

        categoryRepository.delete(category);

        return new CategoryVo(categoryName);
    }

    private List<Category> findCategories(Long storeId) {
        return storeRepository.findCategories(storeId)
            .orElseThrow(CategoryNotFoundException::new);
    }

    private Store findStore(Long storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(StoreNotFoundException::new);
    }

    private Category findCategory(Long storeId, String categoryName) {
        return categoryQueryRepository.findCategory(storeId, categoryName)
            .orElseThrow(NoMatchingCategoryException::new);
    }

    private void validateName(String categoryName) {
        if (categoryName == null || categoryName.equals("")) {
            throw new NoProperCategoryException();
        } else if (!Pattern.matches("^[a-zA-Z가-힣0-9 ()]*$", categoryName)) {
            throw new NoProperCategoryException();
        }
    }

    private void isUnique(String categoryName) {
        if (categoryRepository.findByName(categoryName).isPresent()) {
            throw new NoUniqueCategoryException();
        }
    }

}
