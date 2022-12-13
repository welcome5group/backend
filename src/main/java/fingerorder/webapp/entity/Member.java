package fingerorder.webapp.entity;

import fingerorder.webapp.dto.UserDto;
import fingerorder.webapp.status.UserStatus;
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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String uuid;
    private String email;
    private String password;
    private String nickName;
    private String userType;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public void editNickName(String nickName) {
        this.nickName = nickName;
        this.createdAt = LocalDateTime.now();
    }

    public void resetPassword(String password) {
        this.password = password;
        this.updatedAt = LocalDateTime.now();
    }

    public void giveUUID(String uuid) {
        this.uuid = uuid;
    }

    public UserDto toUserDto() {
        return UserDto.builder()
            .email(this.email)
            .nickName(this.nickName)
            .userType(this.userType)
            .status(this.status)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .build();
    }
}
