package fingerorder.webapp.domain.review.controller;

import fingerorder.webapp.domain.review.dto.ReviewRequest;
import fingerorder.webapp.domain.review.service.MemberReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<?> save(@RequestBody ReviewRequest reviewRequest) {
        return memberReviewService.save(reviewRequest);
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody ReviewRequest reviewRequest) {
        return memberReviewService.edit(reviewRequest);
    }

    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody ReviewRequest reviewRequest) {
        return memberReviewService.remove(reviewRequest);
    }
    
}
