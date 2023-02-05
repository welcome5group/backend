package fingerorder.webapp.domain.review.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReviewAddRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long storeId;

    @NotNull
    private Long orderId;

    @NotBlank
    private String content;

}
