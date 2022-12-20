package fingerorder.webapp.domain.member.dto;

import lombok.Data;

@Data
public class SignOutDto {
	private String accessToken;
	private String refreshToken;
}
