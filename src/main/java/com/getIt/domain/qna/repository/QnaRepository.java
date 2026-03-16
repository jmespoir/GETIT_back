package com.getit.domain.qna.repository;

import com.getit.domain.qna.entity.Qna;
import com.getit.domain.qna.entity.QnaAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    List<Qna> findByLectureIdOrderByCreatedAtDesc(Long lectureId);

    @Query("SELECT q FROM Qna q JOIN FETCH q.member m LEFT JOIN FETCH m.memberInfo WHERE q.lecture.id = :lectureId ORDER BY q.createdAt DESC")
    List<Qna> findByLectureIdAndMemberIdOrderByCreatedAtAsc(Long lectureId, Long memberId);
}