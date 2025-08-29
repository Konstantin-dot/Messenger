package com.acme.messenger.controller;

import com.acme.messenger.domain.FileEntity;
import com.acme.messenger.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService storage;

    @PostMapping(value = "/{id}/files", consumes = {"multipart/form-data"})
    public ResponseEntity<FileEntity> upload(@PathVariable UUID id,
                                             @RequestParam("file") MultipartFile file,
                                             Principal p) throws Exception {
        FileEntity saved = storage.store(id, p.getName(), file);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{channelId}/files")
    public ResponseEntity<List<FileEntity>> listFiles(@PathVariable UUID channelId) {
        return ResponseEntity.ok(storage.getFilesByChannel(channelId));
    }

    @GetMapping("/{channelId}/files/{fileId}")
    public ResponseEntity<FileSystemResource> download(@PathVariable UUID channelId,
                                                       @PathVariable UUID fileId) {
        FileEntity file = storage.getFile(fileId);
        FileSystemResource resource = new FileSystemResource(file.getStoragePath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getMimeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getOriginalName() + "\"")
                .body(resource);
    }
}
