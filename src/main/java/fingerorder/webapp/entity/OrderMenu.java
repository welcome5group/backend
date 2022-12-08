package fingerorder.webapp.entity;

import static javax.persistence.FetchType.LAZY;

import fingerorder.webapp.status.OrderStatus;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderStatus orderStatus;

    private int count;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Builder
    private OrderMenu(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    public static OrderMenu createOrderMenu(Menu menu, int count) {
        return OrderMenu.builder()
            .menu(menu)
            .count(count)
            .build();
    }

    public void addOrder(Order order) {
        this.order = order;
    }

}
