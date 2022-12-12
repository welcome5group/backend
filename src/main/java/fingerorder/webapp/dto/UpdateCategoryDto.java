package fingerorder.webapp.dto;

import fingerorder.webapp.status.CategoryStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateCategoryDto {
	private final String categoryName;
	private final String updateName;
	private final CategoryStatus result;
}
