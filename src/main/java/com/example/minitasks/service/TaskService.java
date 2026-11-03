package com.example.minitasks.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.minitasks.domain.Task;
import com.example.minitasks.dto.TaskRequest;
import com.example.minitasks.dto.TaskResponse;
import com.example.minitasks.exception.TaskNotFoundException;
import com.example.minitasks.repository.TaskRepository;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse getById(Long id) {
        return taskRepository.findById(id)
                .map(TaskResponse::fromEntity)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<TaskResponse> getAll(Boolean done) {
        List<Task> tasks;
        if (done != null) {
            tasks = taskRepository.findByDone(done);
        } else {
            tasks = taskRepository.findAll();
        }
        return tasks.stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Transactional
    public TaskResponse create(TaskRequest request) {
        Task task = new Task(request.title());
        Task saved = taskRepository.save(task);
        return TaskResponse.fromEntity(saved);
    }
}