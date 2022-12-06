package fingerorder.webapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {
	private String email;
	private String type;
}
