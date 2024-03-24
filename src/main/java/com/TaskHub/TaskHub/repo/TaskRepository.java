package com.TaskHub.TaskHub.repo;

import com.TaskHub.TaskHub.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Здесь можно добавить дополнительные методы, если требуется
}
