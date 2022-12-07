package fingerorder.webapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserEditDto {
	private String email;
	private String nickName;
	private String type;
}
