package fingerorder.webapp.domain.category.dto;

import fingerorder.webapp.domain.category.status.CategoryStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class DeleteCategoryDto {
	private final String name;
	private final CategoryStatus result;
}
