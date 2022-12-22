package fingerorder.webapp.domain.order.entity;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.order.status.OrderStatus;
import fingerorder.webapp.domain.store.entity.Store;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    private int tableNum;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Order(int totalPrice, Member member, Store store) {
        this.totalPrice = totalPrice;
        this.member = member;
        this.store = store;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    private Order(Member member, Store store, List<OrderMenu> orderMenus, OrderStatus orderStatus) {
        this.member = member;
        this.store = store;
        this.orderStatus = orderStatus;

        for (OrderMenu orderMenu : orderMenus) {
            makeTotalPrice(orderMenu.getTotalPrice());
            addMenuItems(orderMenu);
        }
    }

    public static Order createOrder(Member member, Store store, OrderStatus orderStatus,
        List<OrderMenu> orderMenus) {
        return new Order(member, store, orderMenus, orderStatus);
    }

    private void addMenuItems(OrderMenu orderMenu) {
        orderMenu.addOrder(this);
        this.orderMenus.add(orderMenu);
    }

    private void makeTotalPrice(int price) {
        this.totalPrice += price;
    }

}

