package fingerorder.webapp.domain.review.dto;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String content;
}
