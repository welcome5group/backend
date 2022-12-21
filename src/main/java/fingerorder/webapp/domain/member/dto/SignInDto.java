package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.status.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
	private String email;
	private String password;
	private MemberType type;
}
