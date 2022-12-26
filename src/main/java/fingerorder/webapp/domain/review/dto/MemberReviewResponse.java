package fingerorder.webapp.domain.review.dto;

import fingerorder.webapp.domain.order.entity.OrderMenu;
import fingerorder.webapp.domain.review.entity.Review;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberReviewResponse {

    private Long id;

    private String storeName;

    private LocalDateTime createdAt;

    private ArrayList<String> menuNames;

    private String content;

    private MemberReviewCommentResponse comment;

    public MemberReviewResponse createResponse(Review review, Optional<Review> comment) {
        return new MemberReviewResponse(
            review.getId(),
            review.getStore().getName(),
            review.getCreatedAt(),
            createMenuNames(review.getOrder().getOrderMenus()),
            review.getContent(),
            createComment(comment));
    }

    private ArrayList<String> createMenuNames(List<OrderMenu> orderMenuList) {
        ArrayList<String> names = new ArrayList<>();
        for (OrderMenu orderMenu : orderMenuList) {
            names.add(orderMenu.getMenu().getName());
        }

        return names;
    }

    private MemberReviewCommentResponse createComment(Optional<Review> comment) {
        if (comment.isEmpty()) {
            return new MemberReviewCommentResponse();
        } else {
            return new MemberReviewCommentResponse(
                comment.get().getMember().getProfile(),
                comment.get().getCreatedAt(),
                comment.get().getContent());
        }
    }

}
