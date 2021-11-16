package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.jwt.JwtProvider;
import com.example.springsecurityexample.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
   private final JwtProvider jwtProvider;

    private static final List<Employee> EMPLOYEES = Arrays.asList(
            new Employee(1, "Mr. Smith"),
            new Employee(2, "Mr. Proper"),
            new Employee(3, "Mr. Bean")
    );

    @GetMapping("{id}")
    public Employee getEmployee(@PathVariable("id") Integer employeeId) {
        return EMPLOYEES.stream()
                .filter(employee -> employeeId.equals(employee.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee " + employeeId + "not found"
                ));
    }
    @GetMapping("update/token")
    public void updateToken(HttpServletRequest request, HttpServletResponse response,  Authentication authResult) throws IOException {
        String access_token = jwtProvider.createToken(authResult);
        String refresh_token = jwtProvider.createRefreshToken(authResult);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

    }
}

