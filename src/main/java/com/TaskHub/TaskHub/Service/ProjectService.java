package com.TaskHub.TaskHub.Service;

import com.TaskHub.TaskHub.entities.Project;

import java.util.List;


public interface ProjectService {
    List<Project> getAllProjects();
    void addProject(Project project);
    // Здесь могут быть добавлены дополнительные методы для работы с проектами, если это необходимо
}


