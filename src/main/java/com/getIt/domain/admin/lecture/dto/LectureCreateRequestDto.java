package com.getit.domain.admin.lecture.dto;

import com.getit.domain.assignment.TrackType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureCreateRequestDto {

    private String title;
    private String description;
    private Integer week;
    private TrackType type;
    private String videoUrl;
}
