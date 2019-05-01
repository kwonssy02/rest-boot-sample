package com.autoever.pilot.files;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.File;
import java.io.IOException;

@RestController
public class FileController {

    @PostMapping("/api/file")
    public ResponseEntity fileUpload(@RequestPart MultipartFile sourceFile) throws IOException {
        String sourceFileName = sourceFile.getOriginalFilename();
        System.out.println(sourceFileName);
        System.out.println(new String(sourceFile.getBytes(), "UTF-8"));
//        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();



        return ResponseEntity.ok("ok");
    }
}
