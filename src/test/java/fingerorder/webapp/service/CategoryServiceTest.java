package fingerorder.webapp.service;

import static fingerorder.webapp.domain.category.status.CategoryStatus.CREATE;
import static fingerorder.webapp.domain.category.status.CategoryStatus.DELETE;
import static fingerorder.webapp.domain.category.status.CategoryStatus.READ;
import static fingerorder.webapp.domain.category.status.CategoryStatus.UPDATE;

import fingerorder.webapp.domain.category.service.CategoryService;
import fingerorder.webapp.entity.Category;
import fingerorder.webapp.entity.Store;
import fingerorder.webapp.domain.category.repository.CategoryQueryRepository;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.category.repository.StoreRepository;
import fingerorder.webapp.domain.category.vo.CategoriesVo;
import fingerorder.webapp.domain.category.vo.CategoryVo;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
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

	private final String categoryName = "메인 메뉴";
	private final String updateName = "서브 메뉴";

	Store createStore() {
		return storeRepository.save(new Store("가게", LocalDateTime.now(), LocalDateTime.now(),
			10, "강남"));
	}

	@Test
	@Transactional
	@DisplayName("카테고리 생성 성공")
	void createCategory_success() {
	    //given
		Store store = createStore();
		CategoryVo categoryVo = categoryService.create(store.getId(), categoryName);

		// when
		Category createdCategory = categoryQueryRepository.findCategory(store.getId(), categoryName).get();

	    // then
		Assertions.assertThat(createdCategory.getName()).isEqualTo(categoryName);
		Assertions.assertThat(categoryVo.getName()).isEqualTo(categoryName);
		Assertions.assertThat(categoryVo.getResult()).isEqualTo(CREATE);
	}

	@Test
	@Transactional
	@DisplayName("카테고리 생성 실패 - Null String 입력")
	void createCategory_fail_null() {
		//given
		Store store = createStore();

		// when

		// then
		org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
			categoryService.create(store.getId(), null);
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
		org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
			categoryService.create(store.getId(), "");
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
		org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
			categoryService.create(store.getId(), "ㅁ");
		});
	}


	@Test
	@DisplayName("카테고리 읽기 성공")
	void getCategory_success() {
		//given
		Store store = createStore();
		categoryService.create(store.getId(), categoryName);

		// when
		CategoriesVo categoriesVo = categoryService.get(store.getId());
		categoriesVo.getNames().stream().forEach(System.out::println);

		// then
		Assertions.assertThat(categoriesVo.getNames().get(0)).isEqualTo(categoryName);
		Assertions.assertThat(categoriesVo.getResult()).isEqualTo(READ);
	}

	@Test
	@DisplayName("카테고리 읽기 성공 - 빈 카테고리")
	void getCategory_fail() {
		//given
		Store store = createStore();

		// when
		CategoriesVo categoriesVo = categoryService.get(store.getId());

		// then
		Assertions.assertThat(categoriesVo.getNames()).isEmpty();
		Assertions.assertThat(categoriesVo.getResult()).isEqualTo(READ);
	}

	@Test
	@Transactional
	@DisplayName("카테고리 업데이트 성공")
	void updateCategory() {
		//given
		Store store = createStore();
		categoryService.create(store.getId(), categoryName);

		// when
		CategoryVo categoryVo = categoryService.update(store.getId(), categoryName, updateName);
		List<Category> categories = storeRepository.findCategories(store.getId()).get();

		// then
		Assertions.assertThat(categories.get(0).getName()).isEqualTo(updateName);
		Assertions.assertThat(categoryVo.getName()).isEqualTo(updateName);
		Assertions.assertThat(categoryVo.getResult()).isEqualTo(UPDATE);
	}

	@Test
	@Transactional
	@DisplayName("카테고리 삭제 성공")
	void deleteCategory_success() {
		//given
		Store store = createStore();
		categoryService.create(store.getId(), categoryName);

		// when
		CategoryVo categoryVo = categoryService.delete(store.getId(), categoryName);

		// then
		Assertions.assertThat(categoryRepository.findAll().size()).isEqualTo(0);
		Assertions.assertThat(categoryVo.getName()).isEqualTo(categoryName);
		Assertions.assertThat(categoryVo.getResult()).isEqualTo(DELETE);
	}

	@Test
	@Transactional
	@DisplayName("카테고리 삭제 실패 - 일치하는 카테고리가 없는 경우")
	void deleteCategory_fail() {
		//given
		Store store = createStore();
		categoryService.create(store.getId(), categoryName);

		// when

		// then
		org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
			categoryService.delete(store.getId(), "categoryName");
		});
	}

	@Test
	@Transactional
	@DisplayName("카테고리 삭제 실패 - Empty String 입력")
	void deleteCategory_fail_empty() {
		//given
		Store store = createStore();
		categoryService.create(store.getId(), categoryName);

		// when

		// then
		org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
			categoryService.delete(store.getId(), "");
		});
	}

	@Test
	@Transactional
	@DisplayName("카테고리 삭제 실패 - Null String 입력")
	void deleteCategory_fail_null() {
		//given
		Store store = createStore();
		categoryService.create(store.getId(), categoryName);

		// when

		// then
		org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
			categoryService.delete(store.getId(), null);
		});
	}

}