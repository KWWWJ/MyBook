package com.project.mybookplace.upload;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
public class UploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file) {
        try {
            // 파일 저장 폴더 설정
            String uploadDirectory = "./src/main/resources/static/upload/";

            // 클래스패스 내의 리소스에 접근하기 위해 Resource 객체를 생성
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(uploadDirectory);
            Path uploadPath = Path.of(resource.getURI());

            // UUID를 사용하여 파일 이름 생성
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String savedFileName = UUID.randomUUID() + extension;

            // 파일 저장 경로 설정
            Path filePath = uploadPath.resolve(savedFileName);

            // 파일 저장
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 저장 성공 시 응답
            return ResponseEntity.ok("{\"success\": true, \"fileName\": \"" + savedFileName + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
            // 파일 저장 실패 시 응답
            return ResponseEntity.status(500).body("{\"success\": false, \"error\": \"File upload failed.\"}");
        }
    }
}