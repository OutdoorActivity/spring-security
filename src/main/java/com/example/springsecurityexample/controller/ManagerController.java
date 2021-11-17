package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.jwt.JwtProvider;
import com.example.springsecurityexample.model.Employee;
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
@RequestMapping("/manager/api")
@RequiredArgsConstructor
public class ManagerController {
    private final JwtProvider jwtProvider;
    private static final List<Employee> EMPLOYEES = Arrays.asList(
            new Employee(1, "Mr. Smith"),
            new Employee(2, "Mr. Proper"),
            new Employee(3, "Mr. Bean")
    );

    private static final List<Task> TASKS = Arrays.asList(
            new Task(1L, "Create app", "Need new application"),
            new Task(2L, "Update properties", "Update properties of db in dev stand")
    );


    @PreAuthorize("hasAnyRole('SCRUMMASTER', 'MANAGER')")
    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") Integer employeeId) {
        return EMPLOYEES.stream()
                .filter(employee -> employeeId.equals(employee.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee " + employeeId + "not found"
                ));
    }

    @PreAuthorize("hasAnyRole('SCRUMMASTER', 'MANAGER')")
    @PostMapping("/task/{id}")
    public void createTask(@PathVariable("id") String taskId, @RequestBody Task task) {
        System.out.println("Created new task" + task);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/task/{id}")
    public Task getTask(@PathVariable("id") Integer taskId) {
        return TASKS.stream()
                .filter(task -> taskId.equals(task.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Task " + taskId + "not found"
                ));
    }

    @DeleteMapping("/employee/{id}")
    public void fireEmployee(@PathVariable("id") Integer employeeId) {
        System.out.println("Emplyee " + employeeId + "is fired");
    }



}