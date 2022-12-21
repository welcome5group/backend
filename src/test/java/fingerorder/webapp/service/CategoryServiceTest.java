package fingerorder.webapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fingerorder.webapp.domain.category.dto.CreateCategoryDto;
import fingerorder.webapp.domain.category.dto.DeleteCategoryDto;
import fingerorder.webapp.domain.category.dto.GetCategoryDto;
import fingerorder.webapp.domain.category.dto.UpdateCategoryDto;
import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.category.repository.CategoryQueryRepository;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.category.service.CategoryService;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
class CategoryServiceTest {

	@Autowired private CategoryRepository categoryRepository;
	@Autowired private CategoryQueryRepository categoryQueryRepository;
	@Autowired private StoreRepository storeRepository;
	@Autowired private CategoryService categoryService;

	@Autowired private EntityManager em;

	private final String categoryName = "메인 메뉴";
	private final String updateName = "서브 메뉴";

	Store createStore() {
		return storeRepository.save(new Store("가게", LocalDateTime.now(), LocalDateTime.now(),
			10, "강남"));
	}

	@Test
//	@Transactional(rollbackFor = Exception.class)
	@DisplayName("고유값 예외처리")
	void exception(){
		Store store = createStore();

		try{
			categoryService.createCategory(store.getId(), categoryName);
			categoryService.createCategory(store.getId(), "categoryName");
			categoryService.updateCategory(store.getId(), categoryName, "categoryName");
		} catch (Exception e) {
			System.err.println("===================" + e);
		}
//			CategoriesVo categoryVo = categoryService.getCategory(store.getId());


	}

	@Test
	@Transactional
	@DisplayName("카테고리 생성 성공")
	void createCategory_success() {
	    //given
		Store store = createStore();
		CreateCategoryDto category = categoryService.createCategory(store.getId(), categoryName);

		// when
		Category createdCategory = categoryQueryRepository.findCategory(store.getId(), categoryName).get();

	    // then
		assertThat(createdCategory.getName()).isEqualTo(categoryName);
		assertThat(category.getName()).isEqualTo(categoryName);
	}

	@Test
	@Transactional
	@DisplayName("카테고리 생성 실패 - Null String 입력")
	void createCategory_fail_null() {
		//given
		Store store = createStore();

		// when

		// then
		assertThrows(IllegalArgumentException.class, () -> {
			categoryService.createCategory(store.getId(), null);
		});
	}

	@Test
	@Transactional
	@DisplayName("카테고리 생성 실패 - Empty String 입력")
	void createCategory_fail_empty() {
		//given
		Store store = createStore();

		// when

		// then
		assertThrows(IllegalArgumentException.class, () -> {
			categoryService.createCategory(store.getId(), "");
		});
	}

	@Test
	@Transactional
	@DisplayName("카테고리 생성 실패 - 정규표현식 불일치")
	void createCategory_fail_RegEx() {
		//given
		Store store = createStore();

		// when

		// then
		assertThrows(IllegalArgumentException.class, () -> {
			categoryService.createCategory(store.getId(), "ㅁ");
		});
	}


	@Test
	@DisplayName("카테고리 읽기 성공")
	void getCategory_success() {
		//given
		Store store = createStore();
		categoryService.createCategory(store.getId(), categoryName);

		// when
		GetCategoryDto category = categoryService.getCategory(store.getId());

		// then
		assertThat(category.getNames().get(0)).isEqualTo(categoryName);
	}

	@Test
	@DisplayName("카테고리 읽기 성공 - 빈 카테고리")
	void getCategory_fail() {
		//given
		Store store = createStore();

		// when
		GetCategoryDto category = categoryService.getCategory(store.getId());

		// then
		assertThat(category.getNames()).isEmpty();
	}

	@Test
	@Transactional
	@DisplayName("카테고리 업데이트 성공")
	void updateCategory() {
		//given
		Store store = createStore();
		categoryService.createCategory(store.getId(), categoryName);

		// when
		UpdateCategoryDto updateCategoryDto = categoryService.updateCategory(store.getId(),
			categoryName, updateName);
		List<Category> categories = storeRepository.findCategories(store.getId()).get();

		// then
		assertThat(categories.get(0).getName()).isEqualTo(updateName);
		assertThat(updateCategoryDto.getUpdateName()).isEqualTo(updateName);
	}

	@Test
	@Transactional
	@DisplayName("카테고리 삭제 성공")
	void deleteCategory_success() {
		//given
		Store store = createStore();
		categoryService.createCategory(store.getId(), categoryName);

		// when
		DeleteCategoryDto deleteCategoryDto = categoryService.deleteCategory(store.getId(),
			categoryName);

		// then
		assertThat(categoryRepository.findAll().size()).isEqualTo(0);
		assertThat(deleteCategoryDto.getName()).isEqualTo(categoryName);
	}

	@Test
	@Transactional
	@DisplayName("카테고리 삭제 실패 - 일치하는 카테고리가 없는 경우")
	void deleteCategory_fail() {
		//given
		Store store = createStore();
		categoryService.createCategory(store.getId(), categoryName);

		// when

		// then
		assertThrows(IllegalArgumentException.class, () -> {
			categoryService.deleteCategory(store.getId(), "categoryName");
		});
	}

	@Test
	@Transactional
	@DisplayName("카테고리 삭제 실패 - Empty String 입력")
	void deleteCategory_fail_empty() {
		//given
		Store store = createStore();
		categoryService.createCategory(store.getId(), categoryName);

		// when

		// then
		assertThrows(IllegalArgumentException.class, () -> {
			categoryService.deleteCategory(store.getId(), "");
		});
	}

	@Test
	@Transactional
	@DisplayName("카테고리 삭제 실패 - Null String 입력")
	void deleteCategory_fail_null() {
		//given
		Store store = createStore();
		categoryService.createCategory(store.getId(), categoryName);

		// when

		// then
		assertThrows(IllegalArgumentException.class, () -> {
			categoryService.deleteCategory(store.getId(), null);
		});
	}

}