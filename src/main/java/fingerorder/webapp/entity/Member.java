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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String nickName;
    private String status;
    private String userType;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;

    public void editNickName(String nickName) {
        this.nickName = nickName;
        this.updated_at = LocalDateTime.now();
    }

    public UserDto toUserDto() {
        return UserDto.builder()
            .email(this.email)
            .nickName(this.nickName)
            .userType(this.userType)
            .status(this.status)
            .created_at(this.created_at)
            .updated_at(this.updated_at)
            .build();
    }
}
