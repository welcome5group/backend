<<<<<<< HEAD
//package fingerorder.webapp.entity;
//
//import fingerorder.webapp.status.UserStatus;
//import java.time.LocalDateTime;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import lombok.Getter;
//
//@Entity
//@Getter
//public class Merchant {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "merchant_id")
//    private Long id;
//    private String email;
//    private String password;
//    private UserStatus status;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    private LocalDateTime deletedAt;
//    private String nickname;
//
//
//}
=======
package fingerorder.webapp.entity;
import fingerorder.webapp.domain.category.status.UserStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id")
    private Long id;
    private String email;
    private String password;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String nickname;


}
>>>>>>> e14231d (feat: Category 예외처리 및 패키지 구조 변경)
