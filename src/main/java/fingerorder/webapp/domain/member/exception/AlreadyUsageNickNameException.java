package fingerorder.webapp.domain.member.exception;

public class AlreadyUsageNickNameException extends MemberException{

	public AlreadyUsageNickNameException() {
		super(MemberErrorCodeException.ALREADY_USAGE_NICKNAME);
	}
}
