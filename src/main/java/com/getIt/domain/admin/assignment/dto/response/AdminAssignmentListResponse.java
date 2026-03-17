package com.getit.domain.admin.assignment.dto.response;

import com.getit.domain.assignment.Status;
import com.getit.domain.assignment.TrackType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class AdminAssignmentListResponse {

    // Assignment 정보
    private Long assignmentId;
    private Long memberId;
    private Status status;
    private LocalDateTime submittedAt;
    private LocalDateTime updatedAt;
    private String comment;

    // Task 정보
    private Long taskId;
    private Integer week;
    private TrackType trackType;
    private String taskTitle;
    private LocalDateTime deadline;

    // 파일 목록
    private List<FileInfo> files;

    @Getter
    @Builder
    public static class FileInfo {

        private Long fileId;
        private String fileName;
        // ADMIN일지라도 클라이언트에 서버 내부 경로를 알려주는 것은 좋지 않을 것 같음.
        // private String filePath;

    }
}