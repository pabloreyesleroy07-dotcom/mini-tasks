package com.example.minitasks.controller;

import com.example.minitasks.domain.Task;
import com.example.minitasks.exception.TaskNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>(List.of(
            new Task(1L, "Aprender Spring Boot", false)
    ));

    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}