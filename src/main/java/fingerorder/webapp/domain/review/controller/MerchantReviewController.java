package fingerorder.webapp.domain.review.controller;

import fingerorder.webapp.domain.review.dto.ReviewCommentRequest;
import fingerorder.webapp.domain.review.dto.ReviewCommentResponse;
import fingerorder.webapp.domain.review.dto.ReviewCommentUpdateRequest;
import fingerorder.webapp.domain.review.dto.ReviewCommentUpdateResponse;
import fingerorder.webapp.domain.review.dto.ReviewResponse;
import fingerorder.webapp.domain.review.service.MerchantReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store/{storeId}/review")
public class MerchantReviewController {

    private final MerchantReviewService merchantReviewService;

//     사장 : 손님이 등록한 모든 리뷰 조회
    @GetMapping
    public List<ReviewResponse> searchAllReview(@PathVariable("storeId") Long storeId) {
        return merchantReviewService.searchAllReview(storeId);
    }

    //사장 : 손님이 등록한 리뷰에 댓글 추가하기
    @PostMapping
    public ResponseEntity<?> registerComment(
        @Validated @RequestBody ReviewCommentRequest reviewCommentRequest,
        BindingResult bindingResult,
        @PathVariable("storeId") Long storeId) {
        ReviewCommentResponse response = merchantReviewService.registerComment(
            reviewCommentRequest, storeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //사장 : 댓글 수정하기
    @PutMapping // storId 필요 없음(o)
    public ResponseEntity<?> updateComment(
        @Validated @RequestBody ReviewCommentUpdateRequest reviewCommentUpdateRequest,
        BindingResult bindingResult,
        @PathVariable("storeId") Long storeId) {
        ReviewCommentUpdateResponse response = merchantReviewService.updateComment(
            reviewCommentUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //사장 : 댓글 삭제하기
    // 이것도 프론트에서 할 수 있기는 한데 parentId가 notNull인 애만 삭제 가능하도록 할지 고민
    @DeleteMapping // storeId 필요 없음(o)
    public ResponseEntity<?> deleteComment(@RequestParam Long reviewId,
        @PathVariable("storeId") Long storeId) {
        merchantReviewService.deleteComment(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
