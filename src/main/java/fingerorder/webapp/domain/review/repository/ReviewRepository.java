package fingerorder.webapp.domain.review.repository;

import fingerorder.webapp.domain.review.entity.Review;
import fingerorder.webapp.domain.store.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByParentId(Long reviewId);

//    Optional<Review> findByParentIdAndStore(Long parentId, Store store);

    List<Review> findAllByStoreAndParentIdIsNull(Store store);

    List<Review> findAllByParentIdIsNotNull();
}
