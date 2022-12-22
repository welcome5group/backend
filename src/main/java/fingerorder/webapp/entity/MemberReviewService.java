package fingerorder.webapp.entity;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.exception.MemberFindException;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.review.entity.Review;
import fingerorder.webapp.domain.review.repository.ReviewRepository;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.exception.StoreFindException;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    //    public void registerReview(ReviewRegisterRequest reviewRegisterRequest) {  //회원의 리뷰 등록
//
//        Review review = new Review(reviewRegisterRequest);
//        Review savedReview = reviewRepository.save(review);
//
//    }

//    public void updateReview(ReviewUpdateRequest reviewUpdateRequest) { //회원의 리뷰 수정
//        Review findReview = reviewRepository.findById(reviewUpdateRequest.getMemberId()).orElseThrow(()
//            -> new RuntimeException("리뷰가 존재하지 않습니다."));
//        findReview.updateReview(reviewUpdateRequest);
//
//    }


    public void searchReview() {

    }

    @Transactional
    public ReviewCreateResponse registerReview(ReviewCreateRequest reviewCreateRequest) {
        Review review = new Review(reviewCreateRequest);
        Review savedReview = reviewRepository.save(review);
        Member member = memberRepository.findById(reviewCreateRequest.getMemberId())
            .orElseThrow(MemberFindException::new);
        Store store = storeRepository.findById(reviewCreateRequest.getStoreId())
            .orElseThrow(StoreFindException::new);
        savedReview.addMember(member);
        savedReview.addStore(store);
        return review.toReviewCreateResponse(savedReview);
    }

    public ReviewUpdateResponse updateReview(ReviewUpdateRequest reviewUpdateRequest) {
        Review findReview = reviewRepository.findById(reviewUpdateRequest.getReviewId())
            .orElseThrow(()
                -> new RuntimeException("리뷰가 존재하지 않습니다."));
        Review review = findReview.updateReview(reviewUpdateRequest);
        return review.toReviewUpdateResponse(review);
    }

    public void deleteReviewAndComment(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()
            -> new RuntimeException("리뷰가 존재하지 않습니다."));

        List<Review> comments = reviewRepository.findByParentId(reviewId);

        reviewRepository.delete(review);
        reviewRepository.deleteAll(comments);

    }

    public void searchAllReview(Long storeId) {

    }
}
