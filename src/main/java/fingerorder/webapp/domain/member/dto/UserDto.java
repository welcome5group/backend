package fingerorder.webapp.domain.member.dto;

import fingerorder.webapp.domain.member.status.UserType;
import fingerorder.webapp.domain.member.status.UserStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String email;
	private String nickName;
	private UserStatus status;
	private UserType userType;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
