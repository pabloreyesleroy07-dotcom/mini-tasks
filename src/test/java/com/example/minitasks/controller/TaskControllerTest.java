package com.example.minitasks.controller;

import com.example.minitasks.domain.Task;
import com.example.minitasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TaskControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TaskRepository taskRepository;

    private Long existingTaskId;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        taskRepository.deleteAll();
        Task task = taskRepository.save(new Task("Aprender Spring Boot"));
        this.existingTaskId = task.getId();
    }

    @Test
    void getById_whenTaskExists_shouldReturn200AndTaskJson() throws Exception {
        mockMvc.perform(get("/v1/tasks/{id}", existingTaskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingTaskId))
                .andExpect(jsonPath("$.title").value("Aprender Spring Boot"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void getById_whenTaskDoesNotExist_shouldReturn404() throws Exception {
        mockMvc.perform(get("/v1/tasks/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_filterByDone_shouldReturnMatchingTasks() throws Exception {
        Task completedTask = new Task("Subir cambios a GitHub");
        completedTask.setDone(true);
        taskRepository.save(completedTask);

        mockMvc.perform(get("/v1/tasks").param("done", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Subir cambios a GitHub"))
                .andExpect(jsonPath("$[0].done").value(true));
    }
}