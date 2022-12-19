package fingerorder.webapp.domain.category.controller;

import fingerorder.webapp.domain.category.dto.DeleteCategoryDto;
import fingerorder.webapp.domain.category.dto.GetCategoryDto;
import fingerorder.webapp.domain.category.dto.UpdateCategoryDto;
import fingerorder.webapp.domain.category.exception.NoProperCategoryException;
import fingerorder.webapp.domain.category.vo.CategoriesVo;
import fingerorder.webapp.domain.category.dto.CreateCategoryDto;
import fingerorder.webapp.domain.category.service.CategoryService;
import fingerorder.webapp.domain.category.vo.CategoryVo;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/{storeId}/category")
	public ResponseEntity<GetCategoryDto> getCategory(
		@PathVariable Long storeId) {

		CategoriesVo categoriesVo = categoryService.getCategory(storeId);

		return new ResponseEntity<>(new GetCategoryDto(categoriesVo.getNames()), HttpStatus.OK);
	}

	@PostMapping("/{storeId}/category")
	public ResponseEntity<CreateCategoryDto> createCategory(
		@PathVariable Long storeId, @Valid @RequestBody CreateCategoryDto createCategoryDto,
		BindingResult bindingResult) {

		validateCategoryName(bindingResult);

		CategoryVo categoryVo = categoryService.createCategory(storeId, createCategoryDto.getName());

		return new ResponseEntity<>(new CreateCategoryDto(categoryVo.getName()), HttpStatus.OK);
	}

	@PutMapping("/{storeId}/category")
	public ResponseEntity<UpdateCategoryDto> updateCategory(
		@PathVariable Long storeId, @Valid @RequestBody UpdateCategoryDto updateCategoryDto,
		BindingResult bindingResult) {

		validateCategoryName(bindingResult);

		CategoryVo categoryVo = categoryService.updateCategory(storeId, updateCategoryDto.getCategoryName(), updateCategoryDto.getUpdateName());

		return new ResponseEntity<>(new UpdateCategoryDto(categoryVo.getName(), categoryVo.getName()), HttpStatus.OK);
	}

	@DeleteMapping("/{storeId}/category")
	public ResponseEntity<DeleteCategoryDto> deleteCategory(
		@PathVariable Long storeId, @Valid @RequestBody DeleteCategoryDto deleteCategoryDto,
		BindingResult bindingResult) {

		validateCategoryName(bindingResult);

		CategoryVo categoryVo = categoryService.deleteCategory(storeId, deleteCategoryDto.getName());

		return new ResponseEntity<>(new DeleteCategoryDto(categoryVo.getName()), HttpStatus.OK);
	}

	public void validateCategoryName(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new NoProperCategoryException();
		}
	}

}
