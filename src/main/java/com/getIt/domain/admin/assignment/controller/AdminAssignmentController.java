package com.getit.domain.admin.assignment.controller;

import com.getit.domain.admin.assignment.dto.response.AdminAssignmentListResponse;
import com.getit.domain.admin.assignment.service.AdminAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/assignments")
@RequiredArgsConstructor
public class AdminAssignmentController {

    private final AdminAssignmentService adminAssignmentService;

    //  부원들이 제출한 전체 과제 조회
    //  GET /api/admin/assignments/all
    @GetMapping("/all")
    public ResponseEntity<List<AdminAssignmentListResponse>> getAllAssignments() {

        List<AdminAssignmentListResponse> assignments =
                adminAssignmentService.getAllAssignments();

        return ResponseEntity.ok(assignments);
    }
}