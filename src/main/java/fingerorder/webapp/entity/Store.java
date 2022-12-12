package fingerorder.webapp.entity;

<<<<<<< HEAD
import fingerorder.webapp.dto.StoreCreateRequest;
import fingerorder.webapp.dto.StoreResponse;
import fingerorder.webapp.dto.StoreUpdateRequest;
=======
//import fingerorder.webapp.dto.StoreDto;
>>>>>>> a420c83 (feat: 카테고리 CRUD 기능 구현 및 테스트)
import java.time.LocalDateTime;
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
<<<<<<< HEAD
import lombok.Builder;
=======
>>>>>>> a420c83 (feat: 카테고리 CRUD 기능 구현 및 테스트)
import lombok.Getter;

@Entity
@Getter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int tableCount;
    private String storeLocation;

<<<<<<< HEAD

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Category> categories = new ArrayList<>();

    public Store(StoreCreateRequest storeCreateRequest) {
        this.name = storeCreateRequest.getName();
        this.createdAt = showCreateAt();
        this.updatedAt = showUpdatedAt();
        this.storeLocation = storeCreateRequest.getStoreLocation();
    }
=======
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @OneToMany(mappedBy = "store")
    private List<Category> categories = new ArrayList<>();



//    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
//    private List<Menu> menus = new ArrayList<>();

//    public Store(StoreDto storeDto) {
//        this.name = storeDto.getName();
//        this.createdAt = showCreateAt();
//        this.updatedAt = showUpdatedAt();
//        this.tableCount = storeDto.getTableCount();
//        this.storeLocation = storeDto.getStoreLocation();
//    }
>>>>>>> a420c83 (feat: 카테고리 CRUD 기능 구현 및 테스트)

    @Builder
    public Store(String name, LocalDateTime createdAt, LocalDateTime updatedAt, int tableCount,
        String storeLocation) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tableCount = tableCount;
        this.storeLocation = storeLocation;
    }

    protected Store() {

    }

<<<<<<< HEAD
    public void changeStore(StoreUpdateRequest storeUpdateRequest) {
        this.name = storeUpdateRequest.getName();
        this.updatedAt = showUpdatedAt();
        this.storeLocation = storeUpdateRequest.getStoreLocation();
        this.tableCount = storeUpdateRequest.getTableCount();
    }
=======
//    public Store changeStore(StoreDto storeDto) {
//        this.name = storeDto.getName();
//        this.updatedAt = showUpdatedAt();
//        this.tableCount = storeDto.getTableCount();
//        this.storeLocation = storeDto.getStoreLocation();
//        return this;
//    }
>>>>>>> a420c83 (feat: 카테고리 CRUD 기능 구현 및 테스트)


    public LocalDateTime showCreateAt() {

        return LocalDateTime.now();

    }

    public LocalDateTime showUpdatedAt() {

        return LocalDateTime.now();

    }

    public StoreResponse toStoreRequestDto(Store store) {
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
