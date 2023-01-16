package fingerorder.webapp.domain.order.entity;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import fingerorder.webapp.domain.menu.entity.Menu;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "orders_menu_id")
    private Long id;

    private int count;

    private int totalPrice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private OrderMenu(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
        calculateTotalPrice(menu.getPrice(), count);
    }

    public static OrderMenu createOrderMenu(Menu menu, int count) {
        return new OrderMenu(menu, count);
    }

    public void addOrder(Order order) {
        this.order = order;
    }

    private void calculateTotalPrice(int price, int count) {
        this.totalPrice = price * count;
    }

}