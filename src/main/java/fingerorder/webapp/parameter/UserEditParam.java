package fingerorder.webapp.parameter;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserEditParam {
	private String email;
	private String nickName;
	private String type;
}
