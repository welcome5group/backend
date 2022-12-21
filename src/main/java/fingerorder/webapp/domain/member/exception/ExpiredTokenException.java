package fingerorder.webapp.domain.member.exception;

public class ExpiredTokenException extends MemberException{

	public ExpiredTokenException() {
		super(MemberErrorCodeException.EXPIRED_TOKEN);
	}
}
