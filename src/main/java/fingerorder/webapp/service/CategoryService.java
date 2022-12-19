package fingerorder.webapp.service;

import fingerorder.webapp.dto.CategoryDto;
import fingerorder.webapp.entity.Category;
import fingerorder.webapp.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Transactional(readOnly = true)
public class CategoryService {
	public final CategoryRepository categoryRepository;

	public CategoryDto create(Long categoryId, String categoryName) {
		Category category = new Category(categoryId, categoryName);

		categoryRepository.save(category);

		return new CategoryDto(categoryName);
	}

	public CategoryDto get(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
			throw new IllegalArgumentException("");
		});

		return new CategoryDto(category.getName());
	}

	public CategoryDto update(Long categoryId, String categoryName) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
			throw new IllegalArgumentException("");
		});

		category.editName(categoryName);

		return new CategoryDto(categoryName);
	}

	public CategoryDto delete(Long categoryId) {
		categoryRepository.deleteById(categoryId);
	}

}
