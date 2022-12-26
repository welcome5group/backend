package fingerorder.webapp.domain.category.controller;

import fingerorder.webapp.domain.category.dto.CreateCategoryDto;
import fingerorder.webapp.domain.category.dto.DeleteCategoryDto;
import fingerorder.webapp.domain.category.dto.GetCategoryDto;
import fingerorder.webapp.domain.category.dto.UpdateCategoryDto;
import fingerorder.webapp.domain.category.exception.NoProperCategoryException;
import fingerorder.webapp.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<GetCategoryDto> getCategory(
        @PathVariable Long storeId) {

        return ResponseEntity.ok(categoryService.getCategory(storeId));
    }

    @PostMapping("/{storeId}/category")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<CreateCategoryDto> createCategory(
        @PathVariable Long storeId, @Validated @RequestBody CreateCategoryDto createCategoryDto,
        BindingResult bindingResult) {

        validateCategoryName(bindingResult);

        return ResponseEntity.ok(categoryService.createCategory(
            storeId, createCategoryDto.getName()));
    }

    @PutMapping("/{storeId}/category")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<UpdateCategoryDto> updateCategory(
        @PathVariable Long storeId, @Validated @RequestBody UpdateCategoryDto updateCategoryDto,
        BindingResult bindingResult) {

        validateCategoryName(bindingResult);

        return ResponseEntity.ok(categoryService.updateCategory(
            storeId, updateCategoryDto.getCategoryName(), updateCategoryDto.getUpdateName()));
    }

    @DeleteMapping("/{storeId}/category")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<DeleteCategoryDto> deleteCategory(
        @PathVariable Long storeId, @Validated @RequestBody DeleteCategoryDto deleteCategoryDto,
        BindingResult bindingResult) {

        validateCategoryName(bindingResult);

        return ResponseEntity.ok(
            categoryService.deleteCategory(storeId, deleteCategoryDto.getName()));
    }

    public void validateCategoryName(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NoProperCategoryException();
        }
    }
}
