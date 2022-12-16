package fingerorder.webapp.domain.category.service;

import fingerorder.webapp.domain.category.exception.CategoryNotFoundException;
import fingerorder.webapp.domain.category.exception.NoMatchingCategoryException;
import fingerorder.webapp.domain.category.exception.NoProperCategoryException;
import fingerorder.webapp.domain.category.exception.NoUniqueCategoryException;
import fingerorder.webapp.domain.category.exception.StoreNotFoundException;
import fingerorder.webapp.domain.category.repository.CategoryQueryRepository;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import fingerorder.webapp.domain.category.status.CategoryStatus;
import fingerorder.webapp.domain.category.vo.CategoriesVo;
import fingerorder.webapp.domain.category.vo.CategoryVo;
import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.store.entity.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final StoreRepository storeRepository;
	private final CategoryQueryRepository categoryQueryRepository;

	public CategoryVo create(Long storeId, String categoryName) {
		validateName(categoryName);
		isUnique(categoryName);

		Category category = new Category(categoryName);
		Store store = getStore(storeId);
		category.setCategoryAndStore(store);
		categoryRepository.save(category);

		return new CategoryVo(categoryName, CategoryStatus.CREATE);
	}

	@Transactional(readOnly = true)
	public CategoriesVo get(Long storeId) {
		List<Category> categories = getCategories(storeId);
		if(categories == null) {
			return new CategoriesVo(null, CategoryStatus.READ);
		}

		List<String> categoryNames = new ArrayList<>();

		for (Category category : categories) {
			categoryNames.add(category.getName());
		}

		return new CategoriesVo(categoryNames, CategoryStatus.READ);
	}

	@Transactional
	public CategoryVo update(Long storeId, String categoryName, String updateName) {
		validateName(updateName);
		isUnique(updateName);

		Category category = getCategory(storeId, categoryName);
		category.editName(updateName);

		return new CategoryVo(updateName, CategoryStatus.UPDATE);
	}

	@Transactional
	public CategoryVo delete(Long storeId, String categoryName) {
		validateName(categoryName);

		Category category = getCategory(storeId, categoryName);

		categoryRepository.delete(category);

		return new CategoryVo(categoryName, CategoryStatus.DELETE);
	}

	private List<Category> getCategories(Long storeId) {
		return storeRepository.findCategories(storeId)
			.orElseThrow(() -> new CategoryNotFoundException());
	}

	private Store getStore(Long storeId) {
		return storeRepository.findById(storeId)
			.orElseThrow(() -> new StoreNotFoundException());
	}

	private Category getCategory(Long storeId, String categoryName) {
		return categoryQueryRepository.findCategory(storeId, categoryName)
			.orElseThrow(() -> new NoMatchingCategoryException());
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
