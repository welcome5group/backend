package fingerorder.webapp.domain.member.exception;

public class NotAuthorizedException extends MemberException {

    public NotAuthorizedException() {
        super(MemberErrorCodeException.NOT_AUTHORIZED_EXCEPTION);
    }
}
