package fingerorder.webapp.domain.category.vo;

import fingerorder.webapp.domain.category.status.CategoryStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryVo {
	private final String name;
	private final CategoryStatus result;
}
