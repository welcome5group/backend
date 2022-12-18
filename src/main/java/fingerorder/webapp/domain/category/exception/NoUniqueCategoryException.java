package fingerorder.webapp.domain.category.exception;

import static fingerorder.webapp.domain.category.exception.CategoryErrorCode.NO_UNIQUE_CATEGORY;

import fingerorder.webapp.core.exception.SqlException;
import org.hibernate.exception.ConstraintViolationException;

public class NoUniqueCategoryException extends SqlException {

//	public NoUniqueCategoryException() {
//		super(CategoryErrorCode.NO_UNIQUE_CATEGORY);
//	}
	public NoUniqueCategoryException() {
		super(NO_UNIQUE_CATEGORY.getMessage(), NO_UNIQUE_CATEGORY);
	}
}
