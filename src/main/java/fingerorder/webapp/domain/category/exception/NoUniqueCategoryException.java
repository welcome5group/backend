package fingerorder.webapp.domain.category.exception;

public class NoUniqueCategoryException extends CategoryException {

    public NoUniqueCategoryException() {
        super(CategoryErrorCode.NO_UNIQUE_CATEGORY);
    }
}
