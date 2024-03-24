package com.TaskHub.TaskHub.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

     // Многие проекты могут быть созданы одним пользователем
    @Column(name = "project_id") // Связь с таблицей Users по столбцу user_id
    private Long projectId;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Date createdAt;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Task(){}

    public Task(Long taskId, String taskName, String description, Long projectId, Long assignedTo, Date dueDate, String status, Date createdAt) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
        this.dueDate = dueDate;
        this.status = status;
        this.createdAt = createdAt;
    }
}