package fingerorder.webapp.domain.review.dto;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReviewRemoveRequest {

    @NotNull
    private Long reviewId;

}
