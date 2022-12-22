package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.status.MemberStatus;
import fingerorder.webapp.domain.member.status.MemberType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String email;
    private String nickName;
    private MemberStatus status;
    private MemberType memberType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
