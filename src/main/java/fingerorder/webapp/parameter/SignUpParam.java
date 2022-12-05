package fingerorder.webapp.parameter;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpParam {
	private String email;
	private String password;
	private String nickName;
	private String type;
}
