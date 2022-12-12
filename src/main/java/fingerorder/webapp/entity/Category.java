package fingerorder.webapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    public Category(String name) {
        this.name = name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

<<<<<<< HEAD
    @OneToMany(mappedBy = "category")
    private List<Menu> menus = new ArrayList<>();
=======
    public void editName(String name) {
        this.name = name;
    }

    public void setCategoryAndStore(Store store, Category category) {
        this.store = store;
        store.getCategories().add(category);
    }
>>>>>>> a420c83 (feat: 카테고리 CRUD 기능 구현 및 테스트)

    protected Category() {

    }

    public void changeStore(Store store) {
        this.store = store;
        store.getCategories().add(this);

    }

    public Category(String name) {
        this.name = name;
    }
}
