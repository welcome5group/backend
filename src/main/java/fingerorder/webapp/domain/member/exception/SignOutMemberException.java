package fingerorder.webapp.domain.member.exception;

public class SignOutMemberException extends MemberException {

    public SignOutMemberException() {
        super(MemberErrorCodeException.SIGN_OUT_MEMBER);
    }
}
