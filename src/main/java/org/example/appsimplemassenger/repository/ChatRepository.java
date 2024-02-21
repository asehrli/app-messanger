package org.example.appsimplemassenger.repository;

import org.example.appsimplemassenger.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    boolean existsByIdAndReceiverId(UUID id, UUID receiver_id);
}