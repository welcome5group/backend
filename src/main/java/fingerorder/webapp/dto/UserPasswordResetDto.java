package fingerorder.webapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPasswordResetDto {
	private String email;
	private String password;
	private String rePassword;
	private String hashValue;
}
