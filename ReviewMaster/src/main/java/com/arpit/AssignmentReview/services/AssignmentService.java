package com.arpit.AssignmentReview.services;

import com.arpit.AssignmentReview.dtos.AssignmentCreateDto;
import com.arpit.AssignmentReview.dtos.AssignmentSubmitDto;
import com.arpit.AssignmentReview.entities.Assignment;
import com.arpit.AssignmentReview.entities.User;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {

    //Common
    Assignment getAssignmetById(Long id);


    //Student

    Assignment createAssignment(User user, AssignmentCreateDto createDto);

    Assignment submitAssignment(AssignmentSubmitDto dto) throws Exception;

    void deleteAssignment(Long assignmentId);
    Assignment editAssignment(Assignment assignment);
    List<Assignment> getAssignments(User user);



    //Code reviewer
    List<Assignment> getAvailable();
    List<Assignment> getAssignmentsByReviewer(User user);
    void claim(Long assignmentId, User user);
    void declaim(Long assignmentId);
    void reject(Long assignmentId);
    void complete(Long assignmentId);
    void addReview(Long assignmentId, String reviewUrl);
}
