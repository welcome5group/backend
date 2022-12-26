package fingerorder.webapp.domain.review.exception;


public class NoUniqueReviewException extends ReviewException {

    public NoUniqueReviewException() {
        super(ReviewErrorCode.NO_UNIQUE_REVIEW);
    }

}
