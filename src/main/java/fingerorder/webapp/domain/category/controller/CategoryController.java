package fingerorder.webapp.domain.category.controller;

import fingerorder.webapp.domain.category.dto.DeleteCategoryDto;
import fingerorder.webapp.domain.category.dto.GetCategoryDto;
import fingerorder.webapp.domain.category.dto.UpdateCategoryDto;
import fingerorder.webapp.domain.category.vo.CategoriesVo;
import fingerorder.webapp.domain.category.dto.CreateCategoryDto;
import fingerorder.webapp.domain.category.service.CategoryService;
import fingerorder.webapp.domain.category.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
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
	public GetCategoryDto category(@PathVariable Long storeId) {

		CategoriesVo categoriesVo = categoryService.get(storeId);

		return new GetCategoryDto(categoriesVo.getNames(), categoriesVo.getResult());
	}

	@PostMapping("/{storeId}/category")
	public CreateCategoryDto createCategory(@PathVariable Long storeId, @RequestBody CreateCategoryDto createCategoryDto) {

		CategoryVo categoryVo = categoryService.create(storeId, createCategoryDto.getName());

		return new CreateCategoryDto(categoryVo.getName(), categoryVo.getResult());
	}

	@PutMapping("/{storeId}/category")
	public UpdateCategoryDto updateCategory(@PathVariable Long storeId, @RequestBody UpdateCategoryDto updateCategoryDto) {

		CategoryVo categoryVo = categoryService.update(storeId, updateCategoryDto.getCategoryName(), updateCategoryDto.getUpdateName());

		return new UpdateCategoryDto(categoryVo.getName(), categoryVo.getName(), categoryVo.getResult());
	}

	@DeleteMapping("/{storeId}/category")
	public DeleteCategoryDto deleteCategory(@PathVariable Long storeId, @RequestBody DeleteCategoryDto deleteCategoryDto) {

		CategoryVo categoryVo = categoryService.delete(storeId, deleteCategoryDto.getName());

		return new DeleteCategoryDto(categoryVo.getName(), categoryVo.getResult());
	}

}
