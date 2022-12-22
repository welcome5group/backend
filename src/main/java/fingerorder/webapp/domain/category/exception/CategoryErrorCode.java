package fingerorder.webapp.domain.category.exception;

import fingerorder.webapp.core.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {

    CATEGORY_NOT_FOUND("C01", "생성된 카테고리명이 없습니다."),
    STORE_NOT_FOUND("C02", "일치하는 가게 정보가 없습니다."),
    NO_MATCHING_CATEGORY("C03", "일치하는 카테고리명이 없습니다."),
    NO_PROPER_CATEGORY("C04", "카테고리명을 정확히 입력해주세요."),
    NO_UNIQUE_CATEGORY("C05", "이미 존재하는 카테고리명입니다.");

    private final String code;
    private final String message;
}
