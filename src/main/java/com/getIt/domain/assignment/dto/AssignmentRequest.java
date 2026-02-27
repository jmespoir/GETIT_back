package com.getit.domain.assignment.dto;

import com.getit.domain.assignment.TaskType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssignmentRequest {

    @NotNull
    private Integer week;

    @NotNull
    private TaskType type;

    private String comment;
}
