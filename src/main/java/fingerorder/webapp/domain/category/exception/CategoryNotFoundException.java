package fingerorder.webapp.domain.category.exception;

public class CategoryNotFoundException extends CategoryException {

    public CategoryNotFoundException() {
        super(CategoryErrorCode.CATEGORY_NOT_FOUND);
    }
}
