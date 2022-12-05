package fingerorder.webapp.entity;

import fingerorder.webapp.dto.UserDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Merchant{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "merchant_id")
	private Long id;
	private String email;
	private String password;
	private String nickName;
	private String status;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private LocalDateTime deleted_at;

	public void editNickName(String nickName) {
		this.nickName = nickName;
	}

	public UserDto toUserDto() {
		return UserDto.builder()
			.email(this.email)
			.nickName(this.nickName)
			.status(this.status)
			.created_at(this.created_at)
			.updated_at(this.updated_at)
			.build();
	}
}
