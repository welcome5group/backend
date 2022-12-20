package fingerorder.webapp.domain.member.exception;

public class AlreadyUsageEmailException extends MemberException{
	public AlreadyUsageEmailException() {
		super(MemberErrorCodeException.ALREADY_USAGE_EMAIL);
	}
}
