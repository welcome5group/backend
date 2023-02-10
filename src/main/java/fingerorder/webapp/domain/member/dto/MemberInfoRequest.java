package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.status.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoRequest {
    private String email;
    private MemberType type;
}
