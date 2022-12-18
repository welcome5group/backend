package fingerorder.webapp.domain.category.service;

import fingerorder.webapp.domain.category.exception.CategoryNotFoundException;
import fingerorder.webapp.domain.category.exception.NoMatchingCategoryException;
import fingerorder.webapp.domain.category.exception.NoProperCategoryException;
import fingerorder.webapp.domain.category.exception.NoUniqueCategoryException;
import fingerorder.webapp.domain.category.exception.StoreNotFoundException;
import fingerorder.webapp.domain.category.repository.CategoryQueryRepository;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import fingerorder.webapp.domain.category.vo.CategoriesVo;
import fingerorder.webapp.domain.category.vo.CategoryVo;
import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.store.entity.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final EntityManager em;
	private final CategoryRepository categoryRepository;
	private final StoreRepository storeRepository;
	private final CategoryQueryRepository categoryQueryRepository;

	public CategoryVo createCategory(Long storeId, String categoryName) {
//		validateName(categoryName);
//		isUnique(categoryName);

		try{
			Category category = new Category(categoryName);
			Store store = findStore(storeId);
			category.setCategoryAndStore(store);
			categoryRepository.save(category);	// 추상화된 Repository -> 스프링 예외
		} catch (DataIntegrityViolationException e) {
			throw new NoUniqueCategoryException();
		}

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

//	@Transactional(rollbackFor = Exception.class)
	@Transactional
	public CategoryVo updateCategory(Long storeId, String categoryName, String updateName) {
//		validateName(updateName);
//		isUnique(updateName);

//		try{
			Category category = findCategory(storeId, categoryName);
			category.editName(updateName);
//			em.flush();
//			System.out.println();
//		}
//		catch (PersistenceException e) {	// 더티체킹 -> JPA용 예외
////		catch (DataIntegrityViolationException e) {
//			System.out.println("===============일번===============");
//			throw new NoUniqueCategoryException();
//		} catch (Exception e) {
//			System.out.println("===============이번===============");
//			System.err.println(e);
//		}

		return new CategoryVo(updateName);
	}

	@Transactional
	public CategoryVo deleteCategory(Long storeId, String categoryName) {
//		validateName(categoryName);

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

	public Category findCategory(Long storeId, String categoryName) {
		return categoryQueryRepository.findCategory(storeId, categoryName)
			.orElseThrow(NoMatchingCategoryException::new);
	}

//	private void validateName(String categoryName) {
//		if (categoryName == null || categoryName.equals("")) {
//			throw new NoProperCategoryException();
//		} else if (!Pattern.matches("^[a-zA-Z가-힣0-9 ()]*$", categoryName)) {
//			throw new NoProperCategoryException();
//		}
//	}

//	private void isUnique(String categoryName) {
//		if (categoryRepository.findByName(categoryName).isPresent()) {
//			throw new NoUniqueCategoryException();
//		}
//	}

}
