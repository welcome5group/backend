package fingerorder.webapp.domain.review.repository;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.review.entity.Review;
import fingerorder.webapp.domain.store.entity.Store;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByParentId(Long reviewId);

//    Optional<Review> findByParentIdAndStore(Long parentId, Store store);

    List<Review> findAllByStoreAndParentIdIsNull(Store store);

    List<Review> findAllByParentIdIsNotNull();

    List<Review> findAllByMember(Member member);

    List<Review> findAllByParentIdIsNotNullAndStore(Store store);

    Optional<Review> findOneByParentId(Long id);

    boolean existsByMemberIdAndOrderId(Long memberId, Long orderId);

}
