package fingerorder.webapp.domain.member.status;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MemberSignUpType {
    NORMAL, KAKAO;

    @JsonCreator
    public static MemberSignUpType from(String s) {
        return MemberSignUpType.valueOf(s.toUpperCase());
    }
}
