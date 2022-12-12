package fingerorder.webapp.service;

import static fingerorder.webapp.status.CategoryStatus.CREATE;
import static fingerorder.webapp.status.CategoryStatus.DELETE;
import static fingerorder.webapp.status.CategoryStatus.READ;
import static fingerorder.webapp.status.CategoryStatus.UPDATE;

import fingerorder.webapp.entity.Category;
import fingerorder.webapp.entity.Store;
import fingerorder.webapp.repository.CategoryQueryRepository;
import fingerorder.webapp.repository.CategoryRepository;
import fingerorder.webapp.repository.StoreRepository;
import fingerorder.webapp.vo.CategoriesVo;
import fingerorder.webapp.vo.CategoryVo;
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
		category.setCategoryAndStore(store, category);
		categoryRepository.save(category);

		return new CategoryVo(categoryName, CREATE);
	}

	@Transactional(readOnly = true)
	public CategoriesVo get(Long storeId) {
		List<Category> categories = getCategories(storeId);
		if(categories == null) {
			return new CategoriesVo(null, READ);
		}

		List<String> categoryNames = new ArrayList<>();

		for (Category category : categories) {
			categoryNames.add(category.getName());
		}

		return new CategoriesVo(categoryNames, READ);
	}

	@Transactional
	public CategoryVo update(Long storeId, String categoryName, String updateName) {
		validateName(updateName);
		isUnique(updateName);

		Category category = getCategory(storeId, categoryName);
		category.editName(updateName);

		return new CategoryVo(updateName, UPDATE);
	}

	@Transactional
	public CategoryVo delete(Long storeId, String categoryName) {
		validateName(categoryName);

		Category category = getCategory(storeId, categoryName);

		categoryRepository.delete(category);

		return new CategoryVo(categoryName, DELETE);
	}

	private List<Category> getCategories(Long storeId) {
		return storeRepository.findCategories(storeId).orElseThrow(() -> {
			throw new IllegalArgumentException("생성된 카테고리명이 없습니다.");
		});
	}

	private Store getStore(Long storeId) {
		return storeRepository.findById(storeId).orElseThrow(() -> {
			throw new IllegalArgumentException("일치하는 가게 정보가 없습니다.");
		});
	}

	private Category getCategory(Long storeId, String categoryName) {
		return categoryQueryRepository.findCategory(storeId, categoryName).orElseThrow(() -> {
			throw new IllegalArgumentException("일치하는 카테고리명이 없습니다.");
		});
	}

	private void validateName(String categoryName) {
		if (categoryName == null || categoryName.equals("")) {
			throw new IllegalArgumentException("카테고리명을 정확히 입력해주세요.");
		} else if (!Pattern.matches("^[a-zA-Z가-힣0-9 ()]*$", categoryName)) {
			throw new IllegalArgumentException("카테고리명을 정확히 입력해주세요.");
		}
	}

	private void isUnique(String categoryName) {
		if (categoryRepository.findByName(categoryName).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 카테고리명입니다.");
		}
	}

}
