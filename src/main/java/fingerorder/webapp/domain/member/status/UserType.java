package fingerorder.webapp.domain.member.status;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserType {

    MERCHANT, MEMBER, GUEST;

    @JsonCreator
    public static UserType from(String s) {
        return UserType.valueOf(s.toUpperCase());
    }
}
