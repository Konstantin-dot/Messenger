package com.acme.messenger.repository;

import com.acme.messenger.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    List<FileEntity> findByChannelId(UUID channelId);
}
