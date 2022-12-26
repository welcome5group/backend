package fingerorder.webapp.domain.review.exception;

import fingerorder.webapp.core.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

    NO_UNIQUE_REVIEW("R01", "이미 작성한 리뷰가 존재합니다."),
    NO_AUTH_REVIEW("R02", "손님만 작성할 수 있습니다.");

    private final String code;
    private final String message;

}
