package fingerorder.webapp.domain.member.exception;

public class InvalidPasswordFormatException extends MemberException {

    public InvalidPasswordFormatException() {
        super(MemberErrorCodeException.INVALID_PASSWORD_FORMAT);
    }
}
