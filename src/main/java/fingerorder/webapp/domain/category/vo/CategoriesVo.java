package fingerorder.webapp.domain.category.vo;

import fingerorder.webapp.domain.category.status.CategoryStatus;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoriesVo {
	private final List<String> names;
	private final CategoryStatus result;
}
