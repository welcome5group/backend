package fingerorder.webapp.domain.category.exception;

import fingerorder.webapp.core.exception.SqlException;
import lombok.Getter;

@Getter
public class CategorySqlException extends SqlException {

	private final CategoryErrorCode categoryErrorCode;

	protected CategorySqlException(CategoryErrorCode categoryErrorCode) {
		super(categoryErrorCode.getMessage(), categoryErrorCode);
		this.categoryErrorCode = categoryErrorCode;
	}
}