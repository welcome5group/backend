package fingerorder.webapp.domain.member.exception;

public class NoExistMemberException extends MemberException{

	public NoExistMemberException() {
		super(MemberErrorCodeException.NO_EXIST_MEMBER);
	}
}
