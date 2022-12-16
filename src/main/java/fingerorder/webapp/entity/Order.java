package fingerorder.webapp.entity;

import fingerorder.webapp.domain.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;
    private LocalDateTime createdAt;
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Order(int totalPrice, Member member, Store store) {
        this.totalPrice = totalPrice;
        this.member = member;
        this.store = store;
    }

    protected Order() {
    }
    //    @OneToMany(mappedBy = "order")
//    private List<OrderMenu> orderMenus = new ArrayList<>();


}
