package fingerorder.webapp.entity;

import fingerorder.webapp.domain.review.dto.ReviewResponse;
import fingerorder.webapp.domain.review.service.MerchantReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberReviewController {

    private final MemberReviewService memberReviewService;
    private final MerchantReviewService merchantReviewService;

    // 내가 작성한 리뷰 조회
//    public void searchMyReview() {
//
//    }

    // 리뷰 등록
    @PostMapping("/review")
    public ResponseEntity<?> registerReview(@RequestBody ReviewCreateRequest reviewCreateRequest) {
        ReviewCreateResponse response = memberReviewService.registerReview(reviewCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 리뷰 수정
    @PutMapping("/review")
    public ResponseEntity<?> updateReview(@RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        ReviewUpdateResponse response = memberReviewService.updateReview(
            reviewUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 리뷰 삭제 -> 리뷰 삭제에 대한 권한을 parentId가 null인 애만 삭제하게 할까?
    // 어차피 프론트에서 버튼을 안만드면 그만이니, 그냥 냅둘까?
    @DeleteMapping("/review")
    public ResponseEntity<?> deleteReview(@RequestParam Long reviewId) {
        memberReviewService.deleteReviewAndComment(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //지금 내가 접속한 식당에 대한 리뷰 조회 -> 이거 사장관점에서 매장 모든 리뷰 조회와 동일하다!!!!
    // 아직은 오류 존재 , 코드 수정 필요함
    @PostMapping(("/store/{storeId}/review"))
    public ResponseEntity<List<ReviewResponse>> searchAllReview(@PathVariable Long storeId) {

        List<ReviewResponse> response = merchantReviewService.searchAllReview(storeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
