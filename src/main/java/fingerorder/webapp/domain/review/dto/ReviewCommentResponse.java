package fingerorder.webapp.domain.review.dto;

import fingerorder.webapp.domain.review.entity.Review;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewCommentResponse {


    private Long memberId;
    private String nickName;
    private String content;
    private Long parentId;
    private LocalDateTime createdAt;

    public ReviewCommentResponse(Review review) {
        this.memberId = review.getMember().getId();
        this.nickName = review.getMember().getNickName();
        this.content = review.getContent();
        this.parentId = review.getParentId();
        this.createdAt = review.getCreatedAt();
    }
}
