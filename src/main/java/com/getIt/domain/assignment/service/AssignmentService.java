package com.getit.domain.assignment.service;

import com.getit.domain.assignment.dto.AssignmentRequest;
import com.getit.domain.assignment.dto.AssignmentResultDto;
import com.getit.domain.assignment.entity.Assignment;
import com.getit.domain.assignment.entity.AssignmentFile;
import com.getit.domain.assignment.entity.Task;
import com.getit.domain.assignment.repository.AssignmentFileRepository;
import com.getit.domain.assignment.repository.AssignmentRepository;
import com.getit.domain.assignment.repository.TaskRepository;
import com.getit.domain.member.entity.Member;
import com.getit.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentService {

    @Value("${file.upload.allowed-extensions}")
    private List<String> allowedExtensions;

    @Value("${file.upload.max-count}")
    private long maxFileCount;

    private final AssignmentRepository assignmentRepository;
    private final AssignmentFileRepository assignmentFileRepository;
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public AssignmentResultDto createAssignment(Long memberId, List<MultipartFile> files, AssignmentRequest dto) {
        if (files == null || files.isEmpty() || (files.size() == 1 && files.get(0).isEmpty())) {
            throw new IllegalArgumentException("첨부된 파일이 없습니다. 최소 1개 이상의 파일을 업로드해야 합니다.");
        }
        if (files.size() > maxFileCount) {
            throw new IllegalArgumentException("파일은 최대 " + maxFileCount + "개 까지만 첨부 가능합니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Task task = taskRepository.findByTypeAndWeek(dto.getType(), dto.getWeek())
                .orElseThrow(() -> new IllegalArgumentException("해당 task를 찾을 수 없습니다."));

        Assignment assignment = Assignment.builder()
                .task(task)
                .member(member)
                .build();

        try {
            assignmentRepository.saveAndFlush(assignment);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("이미 제출된 과제입니다.");
        }

        String dirName = fileStorageService.createAssignmentDir();

        List<String> successFileNames = new ArrayList<>();
        List<String> failedFileNames = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String extension = StringUtils.getFilenameExtension(fileName);

                if (!StringUtils.hasText(fileName)) {
                    log.warn("올바르지 않은 이름의 파일 스킵됨({})", fileName);
                    failedFileNames.add(fileName != null ? fileName : "Unknown_File");
                } else if (file.isEmpty()) {
                    log.warn("비어있는 파일 스킵됨({})", fileName);
                    failedFileNames.add(fileName);
                }

                String filePath = fileStorageService.storeFile(file, dirName);

                AssignmentFile assignmentFile = AssignmentFile.builder()
                        .assignment(assignment)
                        .fileName(fileName)
                        .filePath(filePath)
                        .build();
                assignmentFileRepository.save(assignmentFile);

                successFileNames.add(fileName);
            }

            if (successFileNames.isEmpty()) {
                throw new IllegalArgumentException("유효한 파일이 없습니다.");
            }

            return AssignmentResultDto.builder()
                    .successFiles(successFileNames)
                    .failedFiles(failedFileNames)
                    .build();

        } catch (Exception e) {
            fileStorageService.deleteDir(dirName); // 롤백
            throw new IllegalStateException("저장 중 오류 발생으로 전체 롤백되었습니다.", e); // 트랜잭션 동작
        }
    }
}
