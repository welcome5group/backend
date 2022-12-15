package fingerorder.webapp.domain.category.exception;

import fingerorder.webapp.core.exception.ServiceException;
import lombok.Getter;

@Getter
public class CategoryException extends ServiceException {
	private final CategoryErrorCode categoryErrorCode;

	protected CategoryException(CategoryErrorCode categoryErrorCode) {
		super(categoryErrorCode);
		this.categoryErrorCode = categoryErrorCode;
	}
}
