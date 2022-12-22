package fingerorder.webapp.domain.review.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCommentUpdateRequest {

    @NotNull
    private Long reviewId;
    @NotEmpty
    private String content;

}
