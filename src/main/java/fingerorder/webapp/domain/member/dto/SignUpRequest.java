package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.status.MemberType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
	private String email;
	private String password;
	private String profile;
	private String nickName;
	private MemberType type;
}
