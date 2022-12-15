package fingerorder.webapp.domain.category.exception;

public class NoProperCategoryException extends CategoryException {

	protected NoProperCategoryException() {
		super(CategoryErrorCode.NO_PROPER_CATEGORY);
	}
}
