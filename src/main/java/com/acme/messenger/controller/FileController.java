package com.acme.messenger.controller;


import com.acme.messenger.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;
import java.security.Principal;
import java.util.UUID;


@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class FileController {
    private final FileStorageService storage;


    @PostMapping(value = "/{id}/files", consumes = {"multipart/form-data"})
    public ResponseEntity<?> upload(@PathVariable UUID id, @RequestParam("file") MultipartFile file, Principal p) throws Exception {
        Path path = storage.store(id, p.getName(), file);
        return ResponseEntity.ok().body("/uploads/" + path.getFileName());
    }
}