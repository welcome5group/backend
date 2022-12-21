package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.status.MemberType;
import fingerorder.webapp.domain.member.status.MemberStatus;
import java.time.LocalDateTime;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	private String email;
	private String nickName;
	private MemberStatus status;
	private MemberType memberType;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
