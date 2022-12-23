package fingerorder.webapp.domain.category.exception;

public class StoreNotFoundException extends CategoryException {

    public StoreNotFoundException() {
        super(CategoryErrorCode.STORE_NOT_FOUND);
    }
}
