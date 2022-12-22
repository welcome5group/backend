package fingerorder.webapp.domain.review.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ReviewResponse {


    private Long reviewId;
    private String content;
    private String nickName;
    private LocalDateTime updatedAt;
    private Comment comment;

    public ReviewResponse(Long reviewId, String content, String nickName, LocalDateTime updatedAt) {
        this.reviewId = reviewId;
        this.content = content;
        this.nickName = nickName;
        this.updatedAt = updatedAt;
    }

    @Data
    @AllArgsConstructor
    public static class Comment {

        private String nickName;
        private Long reviewId;
        private Long parentId;
        private LocalDateTime updatedAt;


    }


}
