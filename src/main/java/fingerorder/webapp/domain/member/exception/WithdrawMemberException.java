package fingerorder.webapp.domain.member.exception;

public class WithdrawMemberException extends MemberException{

	public WithdrawMemberException() {
		super(MemberErrorCodeException.WITHDRAW_MEMBER);
	}
}
