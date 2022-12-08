package fingerorder.webapp.dto;

import lombok.Data;

@Data
public class SignInDto {
	private String email;
	private String password;
	private String type;
}
