package fingerorder.webapp.domain.category.controller;

import fingerorder.webapp.domain.category.dto.CreateCategoryDto;
import fingerorder.webapp.domain.category.dto.DeleteCategoryDto;
import fingerorder.webapp.domain.category.dto.GetCategoryDto;
import fingerorder.webapp.domain.category.dto.UpdateCategoryDto;
import fingerorder.webapp.domain.category.exception.NoProperCategoryException;
import fingerorder.webapp.domain.category.service.CategoryService;
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

		return new ResponseEntity<>(categoryService.getCategory(storeId), HttpStatus.OK);
	}

	@PostMapping("/{storeId}/category")
	public ResponseEntity<CreateCategoryDto> createCategory(
		@PathVariable Long storeId, @Valid @RequestBody CreateCategoryDto createCategoryDto,
		BindingResult bindingResult) {

		validateCategoryName(bindingResult);

		return new ResponseEntity<>(categoryService.createCategory(
			storeId, createCategoryDto.getName()), HttpStatus.OK);
	}

	@PutMapping("/{storeId}/category")
	public ResponseEntity<UpdateCategoryDto> updateCategory(
		@PathVariable Long storeId, @Valid @RequestBody UpdateCategoryDto updateCategoryDto,
		BindingResult bindingResult) {

		validateCategoryName(bindingResult);

		return new ResponseEntity<>(categoryService.updateCategory(
			storeId, updateCategoryDto.getCategoryName(), updateCategoryDto.getUpdateName()), HttpStatus.OK);
	}

	@DeleteMapping("/{storeId}/category")
	public ResponseEntity<DeleteCategoryDto> deleteCategory(
		@PathVariable Long storeId, @Valid @RequestBody DeleteCategoryDto deleteCategoryDto,
		BindingResult bindingResult) {

		validateCategoryName(bindingResult);

		return new ResponseEntity<>(categoryService.deleteCategory(storeId, deleteCategoryDto.getName()), HttpStatus.OK);
	}

	public void validateCategoryName(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new NoProperCategoryException();
		}
	}

}
