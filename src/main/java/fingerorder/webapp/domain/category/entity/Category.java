package fingerorder.webapp.domain.category.entity;

import fingerorder.webapp.domain.menu.entity.Menu;
import fingerorder.webapp.domain.store.entity.Store;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;

@Entity
@Getter
@Table(uniqueConstraints = {@UniqueConstraint( name = "name_unique", columnNames = {"name"})})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "category")
    private List<Menu> menus = new ArrayList<>();

    public void editName(String name) {
        this.name = name;
    }

    public void setCategoryAndStore(Store store) {
        this.store = store;
        store.getCategories().add(this);
    }

    protected Category() {

    }

    public Category(String name) {
        this.name = name;
    }
}
