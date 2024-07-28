package com.study.tesma.service;

import com.study.tesma.entity.File;
import com.study.tesma.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private static String UPLOADED_FOLDER = "/var/uploaded_files/";

    @Autowired
    private FileRepository fileRepository;

    public void storeFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        Files.write(path, bytes);

        File file1 = new File();

        file1.setFileName(file.getOriginalFilename());
        file1.setFilePath(path.toString());
        fileRepository.save(file1);
    }
}
