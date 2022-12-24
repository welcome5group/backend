package fingerorder.webapp.domain.review.service;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.review.dto.ReviewRequest;
import fingerorder.webapp.domain.review.entity.Review;
import fingerorder.webapp.domain.review.repository.ReviewRepository;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.repository.StoreRepository;
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

    @Transactional
    public ResponseEntity<?> save(ReviewRequest reviewRequest) {
        Member member = findMemberById(reviewRequest.getMemberId());
        Store store = findStoreById(reviewRequest.getStoreId());

        Review review = new Review(member, store, reviewRequest);

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
            .orElseThrow(() -> new RuntimeException());
    }
}
