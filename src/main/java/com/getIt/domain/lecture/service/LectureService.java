package com.getit.domain.lecture.service;

import com.getit.domain.lecture.dto.LectureDetailResponseDto;
import com.getit.domain.lecture.dto.LectureResponseDto;
import com.getit.domain.lecture.entity.Lecture;
import com.getit.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<LectureResponseDto> getLectures() {
        return lectureRepository.findAll().stream()
                .map(LectureResponseDto::from)
                .collect(Collectors.toList());
    }

    public LectureDetailResponseDto getLectureDetail(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));
        return LectureDetailResponseDto.from(lecture);
    }
}
