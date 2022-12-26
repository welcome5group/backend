package fingerorder.webapp.domain.review.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequest {

    private Long reviewId;

    private Long memberId;

    private Long storeId;

    private Long ordersId;

    private Long parentId;

    private String content;

}
