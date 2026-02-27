package com.getit.domain.assignment.controller;

import com.getit.domain.assignment.dto.AssignmentRequest;
import com.getit.domain.assignment.dto.AssignmentResultDto;
import com.getit.domain.assignment.dto.AssignmentTemporaryResponse;
import com.getit.domain.assignment.service.AssignmentService;
import com.getit.domain.auth.dto.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@PreAuthorize( "hasRole('MEMBER') ")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentTemporaryResponse<AssignmentResultDto>> createAssignment(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestPart(value = "files") List<MultipartFile> files,
            @RequestPart(value = "request") @Valid AssignmentRequest request
            ) {
        AssignmentResultDto result = assignmentService
                .createAssignment(principalDetails.getMember().getId(), files, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AssignmentTemporaryResponse.success(
                        result.getFailedFiles().isEmpty()
                        ? "모든 과제 파일이 성공적으로 제출되었습니다."
                        : "일부 파일 업로드에 성공했습니다.",
                        result
                ));
    }
}
