package fingerorder.webapp.dto;

import fingerorder.webapp.status.UserStatus;
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
	private String userType;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
