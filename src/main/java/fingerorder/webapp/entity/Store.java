package fingerorder.webapp.entity;

//import fingerorder.webapp.dto.StoreDto;
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

//    public Store changeStore(StoreDto storeDto) {
//        this.name = storeDto.getName();
//        this.updatedAt = showUpdatedAt();
//        this.tableCount = storeDto.getTableCount();
//        this.storeLocation = storeDto.getStoreLocation();
//        return this;
//    }

    public LocalDateTime showCreateAt() {

        return LocalDateTime.now();

    }

    public LocalDateTime showUpdatedAt() {

        return LocalDateTime.now();

    }
}
