package com.getit.domain.assignment.dto;

import com.getit.domain.assignment.TrackType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentSubmitRequest {

    /** 있으면 이 ID로 강의 조회 후 해당 Task에 제출 (week/type 무시). 없으면 week+type으로 강의 조회 */
    private Long lectureId;

    @Positive
    private Integer week;

    private TrackType type;

    private String comment;

    /** GitHub 저장소/링크 (선택) */
    private String githubUrl;
}
