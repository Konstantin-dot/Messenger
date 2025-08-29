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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileRepository fileRepository;

    @Value("${app.upload-dir:/var/messenger/uploads}")
    private String uploadRoot;

    public FileEntity store(UUID channelId, String username, MultipartFile file) throws IOException {
        UUID fileId = UUID.randomUUID();
        UUID uploaderId = UUID.nameUUIDFromBytes(username.getBytes());
        Path dirPath = Paths.get(uploadRoot, channelId.toString(), uploaderId.toString());
        Files.createDirectories(dirPath);

        String filename = fileId + "_" + file.getOriginalFilename();
        Path filePath = dirPath.resolve(filename);

        file.transferTo(filePath.toFile());

        File f = filePath.toFile();
        f.setReadable(false, false);
        f.setWritable(false, false);
        f.setReadable(true, true);
        f.setWritable(true, true);

        FileEntity entity = new FileEntity();
        entity.setId(fileId);
        entity.setChannelId(channelId);
        entity.setUploaderId(uploaderId);
        entity.setOriginalName(file.getOriginalFilename());
        entity.setMimeType(file.getContentType());
        entity.setSize(file.getSize());
        entity.setStoragePath(filePath.toAbsolutePath().toString());
        entity.setCreatedAt(Instant.now());

        return fileRepository.save(entity);
    }

    public FileEntity getFile(UUID fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("File not found"));
    }

    public Path getFilePath(UUID fileId) {
        return Paths.get(getFile(fileId).getStoragePath());
    }

    public java.util.List<FileEntity> getFilesByChannel(UUID channelId) {
        return fileRepository.findByChannelId(channelId);
    }
}
