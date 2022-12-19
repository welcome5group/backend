package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.status.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {
	private String email;
	private String nickName;
	private UserType type;
}
