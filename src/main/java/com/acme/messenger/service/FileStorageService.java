package com.acme.messenger.service;


import com.acme.messenger.domain.UserEntity;
import com.acme.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileStorageService {
    @Value("${storage.root:./uploads}")
    private Path root;


    private final UserRepository userRepo;

    public Path store(UUID channelId, String username, MultipartFile file) throws IOException {
        Files.createDirectories(root);
        UserEntity user = userRepo.findByEmail(username).orElseThrow();
        var date = LocalDate.now();
        var dir = root.resolve(channelId.toString()).resolve(String.format("%04d/%02d/%02d", date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        Files.createDirectories(dir);
        var id = UUID.randomUUID();
        var safe = Path.of(file.getOriginalFilename()).getFileName().toString();
        var target = dir.resolve(id + "_" + safe);
        try (var is = file.getInputStream()) {
            Files.copy(is, target);
        }
        var sha = DigestUtils.sha256Hex(Files.newInputStream(target));
// In a real app you'd save metadata to DB
        return target;
    }
}