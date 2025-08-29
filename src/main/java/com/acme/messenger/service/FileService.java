package com.acme.messenger.service;

import com.acme.messenger.domain.FileEntity;
import com.acme.messenger.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Value("${app.file-storage-path:/var/messenger/uploads}")
    private String storageRoot;

    public FileEntity uploadFile(MultipartFile file, UUID ownerId, UUID channelId) throws IOException {
        UUID fileId = UUID.randomUUID();
        String userDir = storageRoot + "/" + ownerId;
        Files.createDirectories(Paths.get(userDir));

        String filePath = userDir + "/" + fileId + "_" + file.getOriginalFilename();
        File targetFile = new File(filePath);

        file.transferTo(targetFile);

        targetFile.setReadable(false, false);
        targetFile.setWritable(false, false);
        targetFile.setReadable(true, true);
        targetFile.setWritable(true, true);

        FileEntity entity = new FileEntity();
        entity.setId(fileId);
        entity.setChannelId(channelId);
        entity.setUploaderId(ownerId);
        entity.setOriginalName(file.getOriginalFilename());
        entity.setMimeType(file.getContentType());
        entity.setSize(file.getSize());
        entity.setStoragePath(filePath);
        entity.setCreatedAt(Instant.now());

        return fileRepository.save(entity);
    }

    public FileEntity getFile(UUID fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("File not found"));
    }
}
