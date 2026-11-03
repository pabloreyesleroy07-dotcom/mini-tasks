package com.example.minitasks.dto;

import com.example.minitasks.domain.Task;

public record TaskResponse(Long id, String title, boolean done) {

    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.isDone());
    }
}