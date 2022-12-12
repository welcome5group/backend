package fingerorder.webapp.dto;

import fingerorder.webapp.entity.UserType;
import lombok.Data;

@Data
public class SignInDto {
	private String email;
	private String password;
	private UserType type;
}
