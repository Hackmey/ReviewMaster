package com.arpit.AssignmentReview.repository;

import com.arpit.AssignmentReview.entities.Assignment;
import com.arpit.AssignmentReview.entities.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByUser(User user);

    List<Assignment> findByReviewer(User user);

    @Query("SELECT a FROM Assignment a WHERE a.reviewer.id IS NULL AND a.status = :status")
    List<Assignment> findAssignmentsWithReviewerNullAndStatusSubmitted(@Param("status") String status);

    @Query("SELECT a FROM Assignment a WHERE a.reviewer.id IS NOT NULL")
    List<Assignment> findAssignmentsWithReviewer();

    @Query("SELECT a FROM Assignment a WHERE a.reviewer.id IS NULL")
    List<Assignment> findAllSubmitted();

}
