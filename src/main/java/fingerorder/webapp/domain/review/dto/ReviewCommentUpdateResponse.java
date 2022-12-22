package fingerorder.webapp.domain.review.dto;

import fingerorder.webapp.domain.review.entity.Review;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewCommentUpdateResponse {

    private String content;
    private LocalDateTime updatedAt;

    public ReviewCommentUpdateResponse(Review review) {
        this.content = review.getContent();
        this.updatedAt = review.getUpdatedAt();
    }
}
