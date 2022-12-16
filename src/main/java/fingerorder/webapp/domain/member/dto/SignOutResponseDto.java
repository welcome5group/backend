package fingerorder.webapp.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignOutResponseDto {
	private String email;
}
