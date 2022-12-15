package fingerorder.webapp.entity;

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
import lombok.Getter;

@Entity
@Getter
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

    public void setCategoryAndStore(Store store, Category category) {
        this.store = store;
        store.getCategories().add(category);
    }

    protected Category() {

    }

    public Category(String name) {
        this.name = name;
    }
}
