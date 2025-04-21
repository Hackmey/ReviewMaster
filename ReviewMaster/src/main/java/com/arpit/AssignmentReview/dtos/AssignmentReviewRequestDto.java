package com.arpit.AssignmentReview.dtos;

import com.arpit.AssignmentReview.entities.User;

public class AssignmentReviewRequestDto {
    private Long id;
    private String codeReviewUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeReviewUrl() {
        return codeReviewUrl;
    }

    public void setCodeReviewUrl(String codeReviewUrl) {
        this.codeReviewUrl = codeReviewUrl;
    }
}

