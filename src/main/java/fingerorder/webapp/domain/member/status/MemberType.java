package fingerorder.webapp.domain.member.status;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MemberType {

    MERCHANT, MEMBER, GUEST;
    @JsonCreator
    public static MemberType from(String s) {
        return MemberType.valueOf(s.toUpperCase());
    }
}
