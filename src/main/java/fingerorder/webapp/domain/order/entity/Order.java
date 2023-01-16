package fingerorder.webapp.domain.order.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.order.status.OrderStatus;
import fingerorder.webapp.domain.order.status.ReviewStatus;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    private int tableNum;
    private int totalPrice;

    @Enumerated(STRING)
    private OrderStatus orderStatus;

    @Enumerated(STRING)
    private ReviewStatus reviewStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenus = new ArrayList<>();


    private Order(Member member, Store store, List<OrderMenu> orderMenus, OrderStatus orderStatus,
        ReviewStatus reviewStatus) {
        this.member = member;
        this.store = store;
        this.orderStatus = orderStatus;
        this.reviewStatus = reviewStatus;

        for (OrderMenu orderMenu : orderMenus) {
            calculateTotalPrice(orderMenu.getTotalPrice());
            addOrderMenu(orderMenu);
        }
    }

    public static Order createOrder(Member member, Store store, OrderStatus orderStatus,
        ReviewStatus reviewStatus, List<OrderMenu> orderMenus) {
        return new Order(member, store, orderMenus, orderStatus, reviewStatus);
    }

    private void addOrderMenu(OrderMenu orderMenu) {
        orderMenu.addOrder(this);
        this.orderMenus.add(orderMenu);
    }

    private void calculateTotalPrice(int price) {
        this.totalPrice += price;
    }

    public void editOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void editOrderReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
