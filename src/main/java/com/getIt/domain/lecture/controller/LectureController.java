package com.getit.domain.lecture.controller;

import com.getit.domain.lecture.dto.LectureDetailResponseDto;
import com.getit.domain.lecture.dto.LectureResponseDto;
import com.getit.domain.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/lectures")
    public ResponseEntity<List<LectureResponseDto>> getLectures() {
        return ResponseEntity.ok(lectureService.getLectures());
    }

    @GetMapping("/lecture/{id}")
    public ResponseEntity<LectureDetailResponseDto> getLectureDetail(@PathVariable Long id) {
        return ResponseEntity.ok(lectureService.getLectureDetail(id));
    }
}
