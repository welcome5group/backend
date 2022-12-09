package fingerorder.webapp.entity;


import fingerorder.webapp.status.UserStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "member")
    private List<Store> stores = new ArrayList<>();


    @Builder
    public Member(String email, String password, String nickname, UserStatus status,
        UserType userType) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.status = status;
        this.userType = userType;
    }

    protected Member() {

    }

    public void addStore(Store store) {
        this.stores.add(store);
        store.changeMember(this);

    }
}
