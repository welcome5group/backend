package fingerorder.webapp.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String email;
	private String nickName;
	private String status;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
}
