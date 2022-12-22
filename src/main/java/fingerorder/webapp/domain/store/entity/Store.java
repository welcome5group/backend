package fingerorder.webapp.domain.store.entity;

import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.menu.entity.Menu;
import fingerorder.webapp.domain.store.dto.StoreCreateRequest;
import fingerorder.webapp.domain.store.dto.StoreResponse;
import fingerorder.webapp.domain.store.dto.StoreUpdateRequest;
import fingerorder.webapp.entity.BaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer tableCount;
    private String storeLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)

    private List<Category> categories = new ArrayList<>();

    public Store(StoreCreateRequest storeCreateRequest) {
        this.name = storeCreateRequest.getName();
        this.createdAt = showCreateAt();
        this.updatedAt = showUpdatedAt();
        this.storeLocation = storeCreateRequest.getStoreLocation();
    }

    @Builder
    public Store(String name, LocalDateTime createdAt, LocalDateTime updatedAt,
        Integer tableCount,
        String storeLocation) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tableCount = tableCount;
        this.storeLocation = storeLocation;
    }

    protected Store() {

    }

    public void changeStore(StoreUpdateRequest storeUpdateRequest) {
        this.name = storeUpdateRequest.getName();
        this.updatedAt = showUpdatedAt();
        this.storeLocation = storeUpdateRequest.getStoreLocation();
        this.tableCount = storeUpdateRequest.getTableCount();
    }

    public LocalDateTime showCreateAt() {
        return LocalDateTime.now();
    }

    public LocalDateTime showUpdatedAt() {
        return LocalDateTime.now();
    }

    public StoreResponse toStoreRequest(Store store) {
        return new StoreResponse(store.getId(), store.getName(), store.getStoreLocation());
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public void addMenu(Menu menu) {
        this.menus.add(menu);
        menu.changeStore(this);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        category.changeStore(this);
    }
}
