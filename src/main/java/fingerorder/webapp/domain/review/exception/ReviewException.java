package fingerorder.webapp.domain.review.exception;

import fingerorder.webapp.core.exception.ServiceException;
import lombok.Getter;

@Getter
public class ReviewException extends ServiceException {

    private final ReviewErrorCode reviewErrorCode;

    protected ReviewException(ReviewErrorCode reviewErrorCode) {
        super(reviewErrorCode);
        this.reviewErrorCode = reviewErrorCode;
    }

}
