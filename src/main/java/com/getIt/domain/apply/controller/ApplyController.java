package com.getit.domain.apply.controller;

import com.getit.domain.auth.dto.PrincipalDetails;
import com.getit.domain.apply.dto.ApplyRequest;
import com.getit.domain.apply.dto.ApplyResponse;
import com.getit.domain.apply.service.ApplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applies")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping
    public ResponseEntity<ApplyResponse> submit(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody ApplyRequest request) {
        applyService.submit(principalDetails.getMember().getId(), request);
        return ResponseEntity.ok(ApplyResponse.success("지원서가 제출되었습니다."));
    }
}
