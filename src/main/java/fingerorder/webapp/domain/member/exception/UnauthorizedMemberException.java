package fingerorder.webapp.domain.member.exception;

public class UnauthorizedMemberException extends MemberException{

	public UnauthorizedMemberException() {
		super(MemberErrorCodeException.UNAUTHORIZED_MEMBER);
	}
}
