package fingerorder.webapp.domain.review.service;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import fingerorder.webapp.domain.review.dto.MemberReviewResponse;
import fingerorder.webapp.domain.review.dto.ReviewRequest;
import fingerorder.webapp.domain.review.entity.Review;
import fingerorder.webapp.domain.review.repository.ReviewRepository;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberReviewService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ResponseEntity<?> save(ReviewRequest reviewRequest) {
        Member member = findMemberById(reviewRequest.getMemberId());
        Store store = findStoreById(reviewRequest.getStoreId());
        Order order = findOrderById(reviewRequest.getOrdersId());

        Review review = new Review(member, store, order, reviewRequest);

        reviewRepository.save(review);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> edit(ReviewRequest reviewRequest) {
        Review review = findReviewById(reviewRequest.getReviewId());

        review.updateReview(reviewRequest);

        reviewRepository.save(review);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> remove(ReviewRequest reviewRequest) {
        Review review = findReviewById(reviewRequest.getReviewId());

        reviewRepository.delete(review);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public List<MemberReviewResponse> find(Long memberId) {
        Member member = findMemberById(memberId);

        List<Review> findReviews = reviewRepository.findAllByMember(member);

        List<MemberReviewResponse> reviews = new ArrayList<>();
        for (Review review : findReviews) {
            Optional<Review> comment = reviewRepository.findOneByParentId(review.getId());
            reviews.add(new MemberReviewResponse().createResponse(review, comment));
        }

        return reviews;
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    private Store findStoreById(Long id) {
        return storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    private Review findReviewById(Long id) {
        return reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("리뷰 아이디가 없습니다."));

    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

}
