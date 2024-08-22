package com.example.taskatipera.controllers;

import com.example.taskatipera.models.RepositoryInfo;
import com.example.taskatipera.services.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping("/repos/{username}")
    public ResponseEntity<List<RepositoryInfo>> getUserRepositories(@PathVariable String username) {
        List<RepositoryInfo> repositories = gitHubService.getUserRepositories(username);
        return ResponseEntity.ok(repositories);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(Map.of("status", ex.getStatusCode().value(), "message", ex.getReason()));
    }
}