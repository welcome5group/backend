package fingerorder.webapp.domain.member.exception;

public class LoginInfoErrorException extends MemberException{

	public LoginInfoErrorException() {
		super(MemberErrorCodeException.LOGIN_INFO_ERROR);
	}
}
