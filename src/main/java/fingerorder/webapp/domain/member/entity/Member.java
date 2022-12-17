package fingerorder.webapp.domain.member.entity;

import fingerorder.webapp.domain.member.dto.UserDto;
import fingerorder.webapp.domain.member.status.UserStatus;
import fingerorder.webapp.domain.member.status.UserType;
import fingerorder.webapp.domain.store.entity.Store;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "member")
    private List<Store> stores = new ArrayList<>();

    @Builder
    public Member(String email, String password, String nickName, UserStatus status,
        UserType userType) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.status = status;
        this.userType = userType;
    }

    protected Member() {

    }

    public void addStore(Store store) {
        this.stores.add(store);
        store.changeMember(this);

    }


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
