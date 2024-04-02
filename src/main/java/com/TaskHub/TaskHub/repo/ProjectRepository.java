package com.TaskHub.TaskHub.repo;

import com.TaskHub.TaskHub.entities.Project;
import com.TaskHub.TaskHub.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjectName(String projectName);
    // Здесь можно добавить дополнительные методы, если требуется
}
