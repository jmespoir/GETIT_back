package com.getit.domain.apply.service;

import com.getit.domain.apply.dto.ApplyDraftDataDto;
import com.getit.domain.apply.dto.ApplyRequest;
import com.getit.domain.apply.entity.Application;
import com.getit.domain.apply.repository.ApplicationRepository;
import com.getit.domain.member.entity.Member;
import com.getit.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyService {

    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void submit(Long memberId, ApplyRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Application application = Application.builder()
                .member(member)
                .answer1(request.getAnswer1())
                .answer2(request.getAnswer2())
                .answer3(request.getAnswer3())
                .answer4(request.getAnswer4())
                .answer5(request.getAnswer5())
                .isDraft(false)
                .build();

        applicationRepository.save(application);
    }

    @Transactional
    public void saveDraft(Long memberId, ApplyRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        applicationRepository.findFirstByMemberAndIsDraftTrue(member)
                .ifPresentOrElse(
                        draft -> draft.updateDraftContent(
                                request.getAnswer1(),
                                request.getAnswer2(),
                                request.getAnswer3(),
                                request.getAnswer4(),
                                request.getAnswer5()
                        ),
                        () -> {
                            Application application = Application.builder()
                                    .member(member)
                                    .answer1(request.getAnswer1())
                                    .answer2(request.getAnswer2())
                                    .answer3(request.getAnswer3())
                                    .answer4(request.getAnswer4())
                                    .answer5(request.getAnswer5())
                                    .isDraft(true)
                                    .build();
                            applicationRepository.save(application);
                        }
                );
    }

    public Optional<ApplyDraftDataDto> getDraft(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return applicationRepository.findFirstByMemberAndIsDraftTrue(member)
                .map(draft -> ApplyDraftDataDto.builder()
                        .answer1(draft.getAnswer1())
                        .answer2(draft.getAnswer2())
                        .answer3(draft.getAnswer3())
                        .answer4(draft.getAnswer4())
                        .answer5(draft.getAnswer5())
                        .agree(false)
                        .build());
    }
}
