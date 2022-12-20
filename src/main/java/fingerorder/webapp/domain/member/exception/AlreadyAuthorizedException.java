package fingerorder.webapp.domain.member.exception;

public class AlreadyAuthorizedException extends MemberException{

	public AlreadyAuthorizedException() {
		super(MemberErrorCodeException.ALREADY_AUTHORIZED_EXCEPTION);
	}
}
