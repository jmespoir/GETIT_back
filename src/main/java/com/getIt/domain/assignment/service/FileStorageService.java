package com.getit.domain.assignment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    private final String storagePath;

    public FileStorageService(@Value("${file.upload.dir}") String storagePath) {
        this.storagePath = storagePath;
        ensureDirExists();
    }

    private void ensureDirExists() {
        try {
            Path path = Path.of(storagePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new IllegalStateException("파일 업로드 디렉토리를 생성할 수 없습니다.", e); // 런타임 예외
        }
    }

    public String createAssignmentDir() {
        String dirName = UUID.randomUUID().toString();
        Path dirPath = Path.of(storagePath, dirName);

        try {
            Files.createDirectories(dirPath);
            return dirName;
        } catch (IOException e) {
            throw new IllegalStateException("과제 제출 디렉토리를 생성할 수 없습니다.", e);
        }
    }

    public String storeFile(MultipartFile file, String dirName) {
        String fileName = file.getOriginalFilename();
        Path filePath = Path.of(storagePath, dirName, fileName);

        try {
            file.transferTo(filePath.toFile());
            return filePath.toString();
        } catch (IOException e) {
            throw new IllegalStateException("파일 저장 중 문제가 발생했습니다.", e);
        }
    }

    // 저장 중 오류 발생 시, 해당 과정에서 만들어진 dir, file 삭제 (롤백)
    public void deleteDir(String dirName) {
        Path dirPath = Path.of(storagePath, dirName);
        try {
            FileSystemUtils.deleteRecursively(dirPath);
        } catch (IOException e) {
            log.error("롤백 실패: {}", dirPath, e);
        }
    }

}
