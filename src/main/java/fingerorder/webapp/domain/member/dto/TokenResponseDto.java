package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.status.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private Long id;
    private String email;
    private String nickName;
    private MemberType memberType;

    public void insertUserInfo(Member findMember) {
        this.id = findMember.getId();
        this.email = findMember.getEmail();
        this.nickName = findMember.getNickName();
        this.memberType = findMember.getMemberType();
    }
}
