package fingerorder.webapp.dto;

import lombok.Data;

@Data
public class SignOutDto {
	private String accessToken;
	private String refreshToken;
}
