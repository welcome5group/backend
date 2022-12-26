package fingerorder.webapp.domain.review.exception;

public class NoAuthReviewException extends ReviewException {

    public NoAuthReviewException() {
        super(ReviewErrorCode.NO_AUTH_REVIEW);
    }
}
