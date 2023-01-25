package fingerorder.webapp.domain.review.controller;

import fingerorder.webapp.domain.review.dto.EditMemberReviewRequest;
import fingerorder.webapp.domain.review.dto.MemberReviewResponse;
import fingerorder.webapp.domain.review.dto.SaveMemberReviewRequest;
import fingerorder.webapp.domain.review.service.MemberReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/review")
public class MemberReviewController {

    private final MemberReviewService memberReviewService;

    @PostMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> saveReview(@RequestBody SaveMemberReviewRequest request) {
        return ResponseEntity.ok(memberReviewService.saveReview(request));
    }

    @PutMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> editReview(@RequestBody EditMemberReviewRequest request) {
        return ResponseEntity.ok(memberReviewService.editReview(request));
    }

    @DeleteMapping("{reviewId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> removeReview(@PathVariable("reviewId") Long reviewId) {
        return ResponseEntity.ok(memberReviewService.removeReview(reviewId));
    }

    @GetMapping("/{memberId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<List<MemberReviewResponse>> findReviews(
        @PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(memberReviewService.findReviews(memberId));
    }

}
