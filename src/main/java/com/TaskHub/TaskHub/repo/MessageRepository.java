package com.TaskHub.TaskHub.repo;

import com.TaskHub.TaskHub.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Здесь можно добавить дополнительные методы, если требуется
}

