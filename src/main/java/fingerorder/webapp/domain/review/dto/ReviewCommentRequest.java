package fingerorder.webapp.domain.review.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewCommentRequest {


    @NotNull
    private Long parentId;
    @NotNull
    private Long memberId;
    @NotEmpty
    private String content;


}
