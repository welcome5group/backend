package fingerorder.webapp.entity;

import fingerorder.webapp.domain.category.status.OrderStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_id")
    private Long id;
    private OrderStatus orderStatus;
    private String name;
    private int count;
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
<<<<<<< HEAD
    @JoinColumn(name = "orders_id")
=======
    @JoinColumn(name = "order_id")
>>>>>>> a420c83 (feat: 카테고리 CRUD 기능 구현 및 테스트)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;


}
