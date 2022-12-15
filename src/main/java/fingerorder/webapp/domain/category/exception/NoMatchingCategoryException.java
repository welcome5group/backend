package fingerorder.webapp.domain.category.exception;

public class NoMatchingCategoryException extends CategoryException {

	protected NoMatchingCategoryException() {
		super(CategoryErrorCode.NO_MATCHING_CATEGORY);
	}
}
