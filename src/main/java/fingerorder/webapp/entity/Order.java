package fingerorder.webapp.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalPrice;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();


    private Order(Member member, Store store, List<OrderMenu> orderMenus) {
        this.member = member;
        this.store = store;

        for (OrderMenu orderMenu : orderMenus) {
            addMenuItems(orderMenu);
        }
    }

    public static Order createOrder(Member member, Store store, List<OrderMenu> orderMenus) {
        return new Order(member, store, orderMenus);
    }

    private void addMenuItems(OrderMenu orderMenu) {
        orderMenu.addOrder(this);
        this.orderMenus.add(orderMenu);
    }

}
