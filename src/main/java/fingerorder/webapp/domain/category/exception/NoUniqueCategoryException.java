package fingerorder.webapp.domain.category.exception;

public class NoUniqueCategoryException extends CategoryException {

	protected NoUniqueCategoryException() {
		super(CategoryErrorCode.NO_UNIQUE_CATEGORY);
	}
}
