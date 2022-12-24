package fingerorder.webapp.domain.review.entity;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.review.dto.ReviewCommentRequest;
import fingerorder.webapp.domain.review.dto.ReviewCommentResponse;
import fingerorder.webapp.domain.review.dto.ReviewCommentUpdateRequest;
import fingerorder.webapp.domain.review.dto.ReviewCommentUpdateResponse;
import fingerorder.webapp.domain.review.dto.ReviewRequest;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.entity.BaseEntity;
//import fingerorder.webapp.entity.ReviewCreateRequest;
//import fingerorder.webapp.entity.ReviewCreateResponse;
//import fingerorder.webapp.entity.ReviewUpdateRequest;
//import fingerorder.webapp.entity.ReviewUpdateResponse;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    //    private String nickName;
    @Column(length = 1000)
    private String content;
//    private LocalDateTime deletedAt;

    private Long parentId; //comment 의 parentId는 review_id 이다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Review(ReviewCommentRequest reviewCommentRequest) {
        this.parentId = reviewCommentRequest.getParentId();
        this.content = reviewCommentRequest.getContent();
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
    }

    protected Review() {

    }

    public Review(Member member, Store store, ReviewRequest reviewRequest) {
        this.member = member;
        this.store = store;
        this.content = reviewRequest.getContent();
    }

    public void addMember(Member member) {
        this.member = member;

    }

    //    public void updateReview(ReviewUpdateRequest reviewUpdateRequest) {
//        this.content = reviewUpdateRequest.getContent();
//        this.updatedAt = LocalDateTime.now();
//
//    }

//    public Review updateReview(ReviewUpdateRequest reviewUpdateRequest) {
//        this.content = reviewUpdateRequest.getContent();
//        return this;
//
//    }


    public Review updateComment(ReviewCommentUpdateRequest reviewCommentUpdateRequest) {
        this.content = reviewCommentUpdateRequest.getContent();
        return this;
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeStore(Store store) {
        this.store = store;

    }

    public ReviewCommentResponse toReviewCommentResponse(Review review) {
        return new ReviewCommentResponse(review);

    }

    public ReviewCommentUpdateResponse toReviewCommentUpdateResponse(Review review) {
        return new ReviewCommentUpdateResponse(review);
    }

//    public ReviewCreateResponse toReviewCreateResponse(Review review) {
//        return new ReviewCreateResponse(review);
//
//    }
//
//    public ReviewUpdateResponse toReviewUpdateResponse(Review review) {
//        return new ReviewUpdateResponse(review);
//    }


    public void addStore(Store store) {
        this.store = store;
    }
}
