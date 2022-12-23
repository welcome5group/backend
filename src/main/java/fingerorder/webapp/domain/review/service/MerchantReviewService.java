package fingerorder.webapp.domain.review.service;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.exception.MemberFindException;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.menu.exception.MenuFindException;
import fingerorder.webapp.domain.review.dto.ReviewCommentRequest;
import fingerorder.webapp.domain.review.dto.ReviewCommentResponse;
import fingerorder.webapp.domain.review.dto.ReviewCommentUpdateRequest;
import fingerorder.webapp.domain.review.dto.ReviewCommentUpdateResponse;
import fingerorder.webapp.domain.review.dto.ReviewResponse;
import fingerorder.webapp.domain.review.dto.ReviewResponse.Comment;
import fingerorder.webapp.domain.review.entity.Review;
import fingerorder.webapp.domain.review.repository.ReviewRepository;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.exception.StoreFindException;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MerchantReviewService {

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

//    public void deleteReviewAndComment(Long reviewId, Long storeId) { //회원의 리뷰 삭제
//        Review findReview = reviewRepository.findById(reviewId).orElseThrow(()
//            -> new RuntimeException("리뷰가 존재하지 않습니다."));
//
//        Store findStore = storeRepository.findById(storeId).orElseThrow(StoreFindException::new);
//
//        Optional<Review> review = reviewRepository.findByParentIdAndStore(memberId, findStore);
//
//        review.ifPresent(r -> reviewRepository.delete(r));
//
//        reviewRepository.delete(findReview);
//
//    }

    //점주의 회원 리뷰에 대한 댓글 생성
    @Transactional
    public ReviewCommentResponse registerComment(ReviewCommentRequest reviewCommentRequest,
        Long storeId) {

        Review review = new Review(reviewCommentRequest);
        Review savedReview = reviewRepository.save(review);
        Member member = memberRepository.findById(reviewCommentRequest.getMemberId()).orElseThrow(
            MenuFindException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(
            StoreFindException::new);
        savedReview.addMember(member);
        savedReview.addStore(store);
        return savedReview.toReviewCommentResponse(savedReview);
    }

    @Transactional
    //점주의 회원 리뷰에 대한 댓글 수정
    public ReviewCommentUpdateResponse updateComment(
        ReviewCommentUpdateRequest reviewCommentUpdateRequest) {
        Review findReview = reviewRepository.findById(reviewCommentUpdateRequest.getReviewId())
            .orElseThrow(()
                -> new RuntimeException("리뷰가 존재하지 않습니다."));
        Review review = findReview.updateComment(reviewCommentUpdateRequest);
        return review.toReviewCommentUpdateResponse(review);
    }

    @Transactional
    //점주의 회원 리뷰에 대한 댓글 삭제
    public void deleteComment(Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId).orElseThrow(()
            -> new RuntimeException("리뷰가 존재하지 않습니다."));

        reviewRepository.delete(findReview);

    }

    @Transactional //트랜잭션을 써야 변경감지가 된다!! -> 트러블슈팅에 넣기
    //손님이 등록한 모든 리뷰 조회 (사장 관점에서)
    public List<ReviewResponse> searchAllReview(Long storeId) {
        List<ReviewResponse> reviewResponses = searchReviewByStore(storeId);
        Store store = storeRepository.findById(storeId).orElseThrow(StoreFindException::new);
        List<Comment> comments = searchReviewByParentId(store);

        for (ReviewResponse reviewResponse : reviewResponses) {
            for (Comment comment : comments) {
                if (comment.getParentId().equals(reviewResponse.getReviewId())) {
                    reviewResponse.setComment(comment);
                    break;
                }
            }
        }
        return reviewResponses;
    }



    private List<ReviewResponse> searchReviewByStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreFindException::new);

        List<Review> reviews = reviewRepository.findAllByStoreAndParentIdIsNull(store);
        return reviews.stream().map(
                r -> new ReviewResponse(r.getId(), r.getContent(), r.getMember().getNickName(),
                    r.getUpdatedAt()))
            .collect(Collectors.toList());
    }


    private List<Comment> searchReviewByParentId(Store store) {

        List<Review> reviews = reviewRepository.findAllByParentIdIsNotNullAndStore(store);

        return reviews.stream().map(
                r -> new Comment(r.getMember().getNickName(), r.getId(), r.getParentId(),
                    r.getUpdatedAt()))
            .collect(Collectors.toList());
    }
}
