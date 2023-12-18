package com.project.mybookplace.upload;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
public class UploadController {
	
	 private static final String DELETE_EVENT_DIRECTORY = "/usr/local/apache-tomcat-10.1.17/webapps/ROOT/WEB-INF/classes/static/upload/event/";
	 private static final String DELETE_BOOK_DIRECTORY = "/usr/local/apache-tomcat-10.1.17/webapps/ROOT/WEB-INF/classes/static/upload/book/";


    @PostMapping("upload/event")
    public ResponseEntity<String> handleFileUploadEvent(@RequestPart("file") MultipartFile file) {
        try {
            String uploadDirectory = "/usr/local/apache-tomcat-10.1.17/webapps/ROOT/WEB-INF/classes/static/upload/event/";

            Path uploadPath = Paths.get(uploadDirectory);

            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String savedFileName = UUID.randomUUID() + extension;

            
            Path filePath = uploadPath.resolve(savedFileName);

            
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 저장 성공 시 응답
            return ResponseEntity.ok("{\"success\": true, \"redirect\": \"/upload\",  \"fileName\": \"" + savedFileName + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
//            // 파일 저장 실패 시 응답
            return ResponseEntity.status(500).body("{\"success\": false, \"error\": \"File upload failed.\"}");
        }
    }
    
    @PostMapping("upload/book")
    public ResponseEntity<String> handleFileUploadBook(@RequestPart("file") MultipartFile file) {
        try {
            String uploadDirectory = "/usr/local/apache-tomcat-10.1.17/webapps/ROOT/WEB-INF/classes/static/upload/book/";

            Path uploadPath = Paths.get(uploadDirectory);

            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String savedFileName = UUID.randomUUID() + extension;

            
            Path filePath = uploadPath.resolve(savedFileName);

            
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 저장 성공 시 응답
            return ResponseEntity.ok("{\"success\": true, \"redirect\": \"/upload\",  \"fileName\": \"" + savedFileName + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
//            // 파일 저장 실패 시 응답
            return ResponseEntity.status(500).body("{\"success\": false, \"error\": \"File upload failed.\"}");
        }
    }
    
    @DeleteMapping("deleteEevnt/{filename}")
    public String deleteFileEvent(@PathVariable(name = "filename") String filename) {
        // 파일 삭제 로직
    	System.out.println(filename);
        File fileToDelete = new File(DELETE_EVENT_DIRECTORY + filename);
        System.out.println("Deleting file: " + fileToDelete.getAbsolutePath());
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
            	System.out.println("File deleted successfully");
                // 파일 삭제 성공
                return "redirect:/upload";
            }else {
            	 System.out.println("File deletion failed");
			}{
            	
            }
        }
        // 파일 삭제 실패 또는 파일이 존재하지 않음
        return "redirect:/upload";
    }
    
    @DeleteMapping("deleteBook/{filename}")
    public String deleteFileBook(@PathVariable(name = "filename") String filename) {
        // 파일 삭제 로직
    	System.out.println(filename);
        File fileToDelete = new File(DELETE_BOOK_DIRECTORY + filename);
        System.out.println("Deleting file: " + fileToDelete.getAbsolutePath());
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
            	System.out.println("File deleted successfully");
                // 파일 삭제 성공
                return "redirect:/upload";
            }else {
            	 System.out.println("File deletion failed");
			}{
            	
            }
        }
        // 파일 삭제 실패 또는 파일이 존재하지 않음
        return "redirect:/upload";
    }
}