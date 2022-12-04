package fingerorder.webapp.entity;


import fingerorder.webapp.dto.UserDto;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private String nickname;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;

    public UserDto toUserDto() {
        return UserDto.builder()
            .email(this.email)
            .nickName(this.nickname)
            .status(this.status)
            .created_at(this.created_at)
            .updated_at(this.updated_at)
            .build();
    }
}
