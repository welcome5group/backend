package fingerorder.webapp.domain.review.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequest {

    @NotNull
    private Long reviewId;

    @NotNull
    private Long memberId;

    @NotNull
    private Long storeId;

    @NotNull
    private Long ordersId;

    @NotNull
    private Long parentId;

    @NotEmpty
    private String content;

}
