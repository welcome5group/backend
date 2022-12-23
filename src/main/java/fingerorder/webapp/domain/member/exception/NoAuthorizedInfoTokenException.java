package fingerorder.webapp.domain.member.exception;

public class NoAuthorizedInfoTokenException extends MemberException {

    public NoAuthorizedInfoTokenException() {
        super(MemberErrorCodeException.NO_AUTHORIZED_INFO_TOKEN);
    }
}
