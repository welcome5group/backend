package fingerorder.webapp.domain.review.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ReviewResponse {

    private Long reviewId;
    private String content;
    private String nickName;
    private LocalDateTime createdAt;
    private List<String> menuNames = new ArrayList<>();

    private Comment comment;


    public ReviewResponse(Long reviewId, String content, String nickName, LocalDateTime createdAt, List<String> menuNames) {
        this.reviewId = reviewId;
        this.content = content;
        this.nickName = nickName;
        this.createdAt = createdAt;
        this.menuNames = menuNames;
    }

    @Data
    @AllArgsConstructor
    public static class Comment {

        private String nickName;
        private Long reviewId;
        private Long parentId;
        private LocalDateTime updatedAt;
        private String content;

    }
}
