package fingerorder.webapp.domain.member.exception;

public class KaKaoAuthException extends MemberException{

	public KaKaoAuthException() {
		super(MemberErrorCodeException.KAKAO_LOGIN);
	}
}
