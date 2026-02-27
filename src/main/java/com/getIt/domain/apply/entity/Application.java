package com.getit.domain.apply.entity;

import com.getit.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String answer1;

    @Column(columnDefinition = "TEXT")
    private String answer2;

    @Column(columnDefinition = "TEXT")
    private String answer3;

    @Column(columnDefinition = "TEXT")
    private String answer4;

    @Column(columnDefinition = "TEXT")
    private String answer5;

    @Column(nullable = false)
    @Builder.Default
    private boolean isDraft = false;
}
