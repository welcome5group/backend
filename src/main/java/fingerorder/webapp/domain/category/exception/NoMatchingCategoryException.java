package fingerorder.webapp.domain.category.exception;

public class NoMatchingCategoryException extends CategoryException {

    public NoMatchingCategoryException() {
        super(CategoryErrorCode.NO_MATCHING_CATEGORY);
    }
}
