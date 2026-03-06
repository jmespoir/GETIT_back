package com.getit.domain.admin.assignment.service;

import com.getit.domain.admin.assignment.dto.response.AdminAssignmentListResponse;

import java.util.List;

public interface AdminAssignmentService {

    //  부원들이 제출한 전체 과제 조회
    List<AdminAssignmentListResponse> getAllAssignments();

}