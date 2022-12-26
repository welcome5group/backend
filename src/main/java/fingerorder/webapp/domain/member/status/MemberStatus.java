package fingerorder.webapp.domain.member.status;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MemberStatus {

    ACTIVATE,
    UNAUTHORIZED,
    DELETED;
    @JsonCreator
    public static MemberStatus from(String s) {
        return MemberStatus.valueOf(s.toUpperCase());
    }
}
