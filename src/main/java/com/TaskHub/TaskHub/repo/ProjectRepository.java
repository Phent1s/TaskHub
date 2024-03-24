package com.TaskHub.TaskHub.repo;

import com.TaskHub.TaskHub.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Здесь можно добавить дополнительные методы, если требуется
}
