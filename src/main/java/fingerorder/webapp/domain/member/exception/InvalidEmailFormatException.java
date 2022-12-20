package fingerorder.webapp.domain.member.exception;

public class InvalidEmailFormatException extends MemberException{

	public InvalidEmailFormatException() {
		super(MemberErrorCodeException.INVALID_EMAIL_FORMAT);
	}
}
