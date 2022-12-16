package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.entity.UserType;
import lombok.Data;

@Data
public class SignInDto {
	private String email;
	private String password;
	private UserType type;
}
