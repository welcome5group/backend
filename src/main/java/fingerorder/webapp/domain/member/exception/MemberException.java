package fingerorder.webapp.domain.member.exception;

import fingerorder.webapp.core.exception.ServiceException;
import lombok.Getter;

@Getter
public class MemberException extends ServiceException {

    private final MemberErrorCodeException memberErrorCodeException;

    protected MemberException(MemberErrorCodeException memberErrorCodeException) {
        super(memberErrorCodeException);
        this.memberErrorCodeException = memberErrorCodeException;
    }
}
