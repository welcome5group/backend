package fingerorder.webapp.domain.member.exception;

import fingerorder.webapp.core.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCodeException implements ErrorCode {
	INVALID_EMAIL_FORMAT("M01","잘못된 이메일 형식입니다."),
	INVALID_PASSWORD_FORMAT("M02","잘못된 비밀번호 형식입니다."),
	ALREADY_USAGE_EMAIL("M03","이미 사용중인 이메일 입니다."),
	NO_EXIST_MEMBER("M04","존재하지 않는 사용자 입니다."),
	UNAUTHORIZED_MEMBER("M05","허가되지 않은 사용자의 요청입니다."),
	NO_AUTHORIZED_INFO_TOKEN("M06","권한 정보가 없는 토큰 입니다."),
	SIGN_OUT_MEMBER("M07","로그아웃된 사용자 입니다."),
	ALREADY_USAGE_NICKNAME("M08","이미 사용 중인 Nick Name 입니다."),
	LOGIN_INFO_ERROR("M09","로그인 정보가 올바르지 않습니다."),
	ALREADY_AUTHORIZED_EXCEPTION
		("M10","해당 사이트에 같은 이메일을 사용하는 사용자가 있습니다."),
	EXPIRED_TOKEN("M11","토큰이 만료 되었습니다. 재 로그인 해주세요."),

	NOT_AUTHORIZED_EXCEPTION("M12","인증처리가 완료되지 않은 사용자 입니다. 이메일 인증"
		+ "완료 후 로그인 해주세요"),
	KAKAO_LOGIN("M20","카카오 로그인 인증 관련 에러");


	private final String code;
	private final String message;
}
