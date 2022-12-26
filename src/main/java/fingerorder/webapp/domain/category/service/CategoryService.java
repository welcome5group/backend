package fingerorder.webapp.domain.category.service;

import fingerorder.webapp.domain.category.dto.CreateCategoryDto;
import fingerorder.webapp.domain.category.dto.DeleteCategoryDto;
import fingerorder.webapp.domain.category.dto.GetCategoryDto;
import fingerorder.webapp.domain.category.dto.UpdateCategoryDto;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final EntityManager em;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    @Transactional(readOnly = true)
    public GetCategoryDto getCategory(Long storeId) {
        List<Category> categories = findCategories(storeId);

        List<String> categoryNames = new ArrayList<>();

        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        return new GetCategoryDto(categoryNames);
    }

    @Transactional
    public CreateCategoryDto createCategory(Long storeId, String categoryName) {
        try{
            Category category = new Category(categoryName);
            Store store = findStore(storeId);
            category.setCategoryAndStore(store);
            categoryRepository.save(category);	// 추상화된 Repository -> 스프링 예외
        } catch (DataIntegrityViolationException e) {
            throw new NoUniqueCategoryException();
        }

        return new CreateCategoryDto(categoryName);
    }


    public UpdateCategoryDto updateCategory(Long storeId, String categoryName, String updateName) {
        try {
            editName(storeId, categoryName, updateName);
        } catch (DataIntegrityViolationException e) {
            throw new NoUniqueCategoryException();
        }

        return new UpdateCategoryDto(categoryName, updateName);
    }

    @Transactional
    public DeleteCategoryDto deleteCategory(Long storeId, String categoryName) {
//		validateName(categoryName);

        Category category = findCategory(storeId, categoryName);

        categoryRepository.delete(category);

        return new DeleteCategoryDto(categoryName);
    }

    @Transactional
    public void editName(Long storeId, String categoryName, String updateName) {
        Category category = findCategory(storeId, categoryName);
        category.editName(updateName);
        categoryRepository.save(category);
    }

    private List<Category> findCategories(Long storeId) {
        return storeRepository.findCategories(storeId)
            .orElseThrow(CategoryNotFoundException::new);
    }

    private Store findStore(Long storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(StoreNotFoundException::new);
    }

    public Category findCategory(Long storeId, String categoryName) {
        return categoryQueryRepository.findCategory(storeId, categoryName)
            .orElseThrow(NoMatchingCategoryException::new);
    }

}
