package fingerorder.webapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fingerorder.webapp.domain.category.service.CategoryService;
import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.category.repository.CategoryQueryRepository;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import fingerorder.webapp.domain.category.vo.CategoriesVo;
import fingerorder.webapp.domain.category.vo.CategoryVo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
			CategoryVo categoryVo1 = categoryService.createCategory(store.getId(), categoryName);
			CategoryVo categoryVo2 = categoryService.createCategory(store.getId(), "categoryName");
			CategoryVo categoryVo3 = categoryService.updateCategory(store.getId(), categoryName, "categoryName");
		} catch (DataIntegrityViolationException e) {
			System.err.println(e);
		}
//			CategoriesVo categoryVo = categoryService.getCategory(store.getId());


	}

	@Test
	@Transactional
	@DisplayName("카테고리 생성 성공")
	void createCategory_success() {
	    //given
		Store store = createStore();
		CategoryVo categoryVo = categoryService.createCategory(store.getId(), categoryName);

		// when
		Category createdCategory = categoryQueryRepository.findCategory(store.getId(), categoryName).get();

	    // then
		assertThat(createdCategory.getName()).isEqualTo(categoryName);
		assertThat(categoryVo.getName()).isEqualTo(categoryName);
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
		CategoriesVo categoriesVo = categoryService.getCategory(store.getId());

		// then
		assertThat(categoriesVo.getNames().get(0)).isEqualTo(categoryName);
	}

	@Test
	@DisplayName("카테고리 읽기 성공 - 빈 카테고리")
	void getCategory_fail() {
		//given
		Store store = createStore();

		// when
		CategoriesVo categoriesVo = categoryService.getCategory(store.getId());

		// then
		assertThat(categoriesVo.getNames()).isEmpty();
	}

	@Test
	@Transactional
	@DisplayName("카테고리 업데이트 성공")
	void updateCategory() {
		//given
		Store store = createStore();
		categoryService.createCategory(store.getId(), categoryName);

		// when
		CategoryVo categoryVo = categoryService.updateCategory(store.getId(), categoryName, updateName);
		List<Category> categories = storeRepository.findCategories(store.getId()).get();

		// then
		assertThat(categories.get(0).getName()).isEqualTo(updateName);
		assertThat(categoryVo.getName()).isEqualTo(updateName);
	}

	@Test
	@Transactional
	@DisplayName("카테고리 삭제 성공")
	void deleteCategory_success() {
		//given
		Store store = createStore();
		categoryService.createCategory(store.getId(), categoryName);

		// when
		CategoryVo categoryVo = categoryService.deleteCategory(store.getId(), categoryName);

		// then
		assertThat(categoryRepository.findAll().size()).isEqualTo(0);
		assertThat(categoryVo.getName()).isEqualTo(categoryName);
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