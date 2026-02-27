package com.getit.domain.assignment.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AssignmentResultDto {
    private List<String> successFiles;
    private List<String> failedFiles;
}
