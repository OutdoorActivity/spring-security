package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.jwt.JwtProvider;
import com.example.springsecurityexample.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final JwtProvider jwtProvider;
    private static final List<Task> TASKS = Arrays.asList(
            new Task(1L, "Create app", "Need new application"),
            new Task(2L, "Update properties", "Update properties of db in dev stand")
    );


    @PreAuthorize("hasAnyRole('EMPLOYEE', 'TRAINEE')")
    @GetMapping("{id}")
    public Task getTask(@PathVariable("id") Long taskId) {
        return TASKS.stream()
                .filter(task -> taskId.equals(task.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Task " + taskId + "not found"
                ));
    }



    @PreAuthorize("hasAuthority('task:write')")
    @PutMapping("{id}")
    public void updateTask(@PathVariable("id") Long taskId) {
        System.out.println("Task updated");
    }

}