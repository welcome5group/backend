package fingerorder.webapp.parameter;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserParam {
	private String email;
	private String type;
}
