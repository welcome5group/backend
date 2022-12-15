package fingerorder.webapp.domain.category.exception;

public class StoreNotFoundException extends CategoryException {

	protected StoreNotFoundException() {
		super(CategoryErrorCode.STORE_NOT_FOUND);
	}
}
