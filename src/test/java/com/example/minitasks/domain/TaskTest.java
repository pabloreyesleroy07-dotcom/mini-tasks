package com.example.minitasks.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void newTaskShouldNotBeDone() {
        var task = new Task("Comprar leche");
        assertThat(task.isDone()).isFalse();
    }
}