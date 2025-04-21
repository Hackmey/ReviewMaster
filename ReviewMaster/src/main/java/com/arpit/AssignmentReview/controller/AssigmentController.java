package com.arpit.AssignmentReview.controller;

import com.arpit.AssignmentReview.dtos.AssignmentCreateDto;
import com.arpit.AssignmentReview.dtos.AssignmentReviewRequestDto;
import com.arpit.AssignmentReview.dtos.AssignmentSubmitDto;
import com.arpit.AssignmentReview.dtos.ClaimDto;
import com.arpit.AssignmentReview.entities.Assignment;
import com.arpit.AssignmentReview.entities.User;
import com.arpit.AssignmentReview.repository.AssignmentRepository;
import com.arpit.AssignmentReview.repository.UserRepo;
import com.arpit.AssignmentReview.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment")
public class AssigmentController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createAssignment(@AuthenticationPrincipal User user,@RequestBody AssignmentCreateDto dto){
        Assignment assignment = assignmentService.createAssignment(user, dto);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    @PostMapping("/submit")
    public ResponseEntity<Assignment> submitAssignment(AssignmentSubmitDto dto) throws Exception {
        Assignment assignment = assignmentService.submitAssignment(dto);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }


    @PutMapping("/edit")
    public ResponseEntity<Assignment> editAssignment(@RequestBody AssignmentSubmitDto dto) throws Exception {

        Assignment assignment = assignmentService.submitAssignment(dto);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }


    @GetMapping("/student")
    public ResponseEntity<?> getAssignments(User user){
        return new ResponseEntity<>(assignmentService.getAssignments(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignments(@PathVariable Long id){
        return new ResponseEntity<>(assignmentService.getAssignmetById(id), HttpStatus.OK);
    }

    @GetMapping("/codeReviewer")
    public ResponseEntity<?> getReviewAssignments(User user){
        return new ResponseEntity<>(assignmentService.getAssignmentsByReviewer(user), HttpStatus.OK);
    }

    @GetMapping("/demo/{userid}")
    public ResponseEntity<List<Assignment>> getDemoAssignments(@PathVariable Long userid){
        User user = userRepo.findById(userid).get();
        return new ResponseEntity<>(assignmentService.getAssignments(user), HttpStatus.OK);
    }

    @GetMapping("/codeReview")
    public ResponseEntity<?> getReviewAssn(User user){
        return new ResponseEntity<>(assignmentRepository.findAssignmentsWithReviewer(), HttpStatus.OK);
    }

    @GetMapping("/Reviews")
    public ResponseEntity<?> getReviews(User user){
        return new ResponseEntity<>(assignmentRepository.findAllSubmitted(), HttpStatus.OK);
    }


    @PutMapping("/claim")
    public ResponseEntity<Assignment> editAssignment(@RequestBody ClaimDto claimDto) throws Exception {
        Assignment assignment =assignmentRepository.findById(claimDto.getAssId()).get();
        assignment.setStatus("In Review");
        User claimer = userRepo.findById(claimDto.getReviewerId()).get();
        assignment.setReviewer(claimer);
        assignmentRepository.save(assignment);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    @PutMapping("/editUrl")
    public ResponseEntity<Assignment> editAssignment(@RequestBody AssignmentReviewRequestDto dto) throws Exception {

        Assignment assignment = assignmentRepository.findById(dto.getId()).get();
        assignment.setCodeReviewUrl(dto.getCodeReviewUrl());
//        System.out.println(dto.getId() + " "  +
        Assignment na = assignmentRepository.save(assignment);
        return new ResponseEntity<>(na, HttpStatus.CREATED);

    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) throws Exception {
        assignmentRepository.deleteById(id);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

}
