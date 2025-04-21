package com.arpit.AssignmentReview.dtos;

public class ClaimDto {
    private Long reviewerId;
    private Long assId;

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getAssId() {
        return assId;
    }

    public void setAssId(Long assId) {
        this.assId = assId;
    }
}
