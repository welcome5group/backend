package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
	private String email;
	private String password;
	private String nickName;
	private UserType type;
}
