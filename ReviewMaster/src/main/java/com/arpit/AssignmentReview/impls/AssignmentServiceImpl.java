package com.arpit.AssignmentReview.impls;
import com.arpit.AssignmentReview.dtos.AssignmentCreateDto;
import com.arpit.AssignmentReview.dtos.AssignmentSubmitDto;
import com.arpit.AssignmentReview.entities.Assignment;
import com.arpit.AssignmentReview.entities.Status;
import com.arpit.AssignmentReview.entities.User;
import com.arpit.AssignmentReview.repository.AssignmentRepository;
import com.arpit.AssignmentReview.repository.UserRepo;
import com.arpit.AssignmentReview.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepo userRepo;

    Status status = new Status();

    @Override
    public Assignment getAssignmetById(Long id) {
        return assignmentRepository.findById(id).get();
    }

    @Override
    public Assignment createAssignment(User user, AssignmentCreateDto createDto) {

        User userget = userRepo.findById(createDto.getId()).get();
        System.out.println(userget);
        Assignment assignment = new Assignment();
        assignment.setStatus(createDto.getStatus());
        assignment.setUser(userget);
        assignment.setName(createDto.getName());
        assignment.setBranch(createDto.getBranch());
        assignment.setGithubUrl(createDto.getGithubUrl());
        assignment.setStatus("needs to be submitted");
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment submitAssignment(AssignmentSubmitDto dto) throws Exception {
        Optional<Assignment> found = assignmentRepository.findById(dto.getId());
        Assignment newAssignment = found.get();
        newAssignment.setGithubUrl(dto.getGithubUrl());
        newAssignment.setStatus(dto.getStatus());
        newAssignment.setName(dto.getName());
        newAssignment.setBranch(dto.getBranch());
        String status = dto.getStatus();
        if(status.equals("needs to be submitted")){
            status = "submitted";
        }
        if(status.equals("In Review")){
            status = "needs update";
        }
        newAssignment.setStatus(status);
        newAssignment.setCodeReviewUrl(dto.getCodeReviewUrl());
        return assignmentRepository.save(newAssignment);
    }

    @Override
    public void deleteAssignment(Long assignmentId) {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        if(assignment.isEmpty()) return ;
        assignmentRepository.delete(assignment.get());
    }

    @Override
    public Assignment editAssignment(Assignment assignment) {
        return null;
    }

    @Override
    public List<Assignment> getAssignments(User user) {
        return assignmentRepository.findByUser(user);
    }

    @Override
    public List<Assignment> getAvailable() {
        return assignmentRepository.findAssignmentsWithReviewerNullAndStatusSubmitted("submitted");
    }

    @Override
    public List<Assignment> getAssignmentsByReviewer(User user) {
        return assignmentRepository.findByReviewer(user);
    }

    @Override
    public void claim(Long assignmentId, User user) {
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        assignment.setReviewer(user);
        assignment.setStatus("in review");
        assignmentRepository.save(assignment);
    }

    @Override
    public void declaim(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        assignment.setReviewer(null);
        assignment.setStatus("submitted");
        assignmentRepository.save(assignment);
    }

    @Override
    public void reject(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        assignment.setReviewer(null);
        assignment.setStatus("rejected");
        assignmentRepository.save(assignment);
    }

    @Override
    public void complete(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        assignment.setStatus("completed");
        assignmentRepository.save(assignment);
    }

    @Override
    public void addReview(Long assignmentId, String reviewUrl) {
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        assignment.setStatus("needs update");
        assignment.setCodeReviewUrl(reviewUrl);
        assignmentRepository.save(assignment);
    }

}
