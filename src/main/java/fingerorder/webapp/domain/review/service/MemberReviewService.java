package fingerorder.webapp.domain.review.service;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import fingerorder.webapp.domain.review.dto.EditMemberReviewRequest;
import fingerorder.webapp.domain.review.dto.MemberReviewResponse;
import fingerorder.webapp.domain.review.dto.RemoveMemberReviewRequest;
import fingerorder.webapp.domain.review.dto.SaveMemberReviewRequest;
import fingerorder.webapp.domain.review.entity.Review;
import fingerorder.webapp.domain.review.exception.NoUniqueReviewException;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberReviewService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void saveReview(SaveMemberReviewRequest request) {
        Member member = findMemberById(request.getMemberId());

        boolean findReview = reviewRepository.existsByMember(member);
        if (findReview) {
            throw new NoUniqueReviewException();
        }

        Store store = findStoreById(request.getStoreId());
        Order order = findOrderById(request.getOrderId());

        Review review = new Review(member, store, order, request);

        reviewRepository.save(review);
    }

    @Transactional
    public void editReview(EditMemberReviewRequest request) {
        Review review = findReviewById(request.getReviewId());

        review.updateReview(request);

        reviewRepository.save(review);
    }

    @Transactional
    public void removeReview(RemoveMemberReviewRequest request) {
        Review review = findReviewById(request.getReviewId());

        reviewRepository.delete(review);
    }

    public List<MemberReviewResponse> findReviews(Long memberId) {
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
