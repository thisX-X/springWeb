package com.study.tesma.service;

import com.study.tesma.entity.Board;
import com.study.tesma.entity.File;
import com.study.tesma.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileService {
    @Value("${upload.path}")
    private String UPLOADED_FOLDER;

    @Autowired
    private FileRepository fileRepository;

    public File findById(Integer fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    public File storeFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());

        // 디렉토리가 존재하지 않으면 생성
        Path dirPath = path.getParent();
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        Files.write(path, bytes);

        File fileEntity = new File();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFilePath(path.toString());
        return fileRepository.save(fileEntity);
    }
}
