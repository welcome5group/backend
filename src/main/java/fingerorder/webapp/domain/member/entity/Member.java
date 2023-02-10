package fingerorder.webapp.domain.member.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import fingerorder.webapp.domain.member.dto.MemberResponse;
import fingerorder.webapp.domain.member.status.MemberSignUpType;
import fingerorder.webapp.domain.member.status.MemberStatus;
import fingerorder.webapp.domain.member.status.MemberType;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.entity.BaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String uuid;
    private String email;
    private String password;
    private String nickName;
    private String profile;
    @Enumerated(STRING)
    private MemberStatus status;

    @Enumerated(STRING)
    private MemberSignUpType memberSignUpType; // 카카오 회원가입, 일반 회원가입
    private LocalDateTime deletedAt;

    @Enumerated(STRING)
    private MemberType memberType;

    @OneToMany(mappedBy = "member")
    private List<Store> stores = new ArrayList<>();

    public void addStore(Store store) {
        this.stores.add(store);
        store.changeMember(this);
    }

    public void editNickName(String nickName) {
        this.nickName = nickName;
    }

    public void editPassword(String password) {
        this.password = password;
    }

    public void editUUID(String uuid) {
        this.uuid = uuid;
    }

    public void editProfile(String profile) {
        this.profile = profile;
    }

    public void editMemberStatus(MemberStatus status) {
        this.status = status;

        if (status == MemberStatus.DELETED) {
            this.deletedAt = LocalDateTime.now();
        }
    }

    public MemberResponse toMemberResponse() {
        return new MemberResponse(this.id, this.email, this.nickName, this.profile, this.status,
                this.memberType, this.getCreatedAt(), this.getUpdatedAt());
    }

}
