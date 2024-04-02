package com.TaskHub.TaskHub.Service;

import com.TaskHub.TaskHub.entities.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    void addTask(Task task);
}
