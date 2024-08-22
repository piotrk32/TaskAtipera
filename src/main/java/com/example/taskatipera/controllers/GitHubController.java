package com.example.taskatipera.controllers;

<<<<<<< HEAD
import com.example.taskatipera.exceptions.ErrorResponse;
import com.example.taskatipera.models.RepositoryInfo;
import com.example.taskatipera.services.GitHubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
=======
import com.example.taskatipera.models.RepositoryInfo;
import com.example.taskatipera.services.GitHubService;
>>>>>>> new-origin/master
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
<<<<<<< HEAD
@Tag(name = "GitHub Repositories", description = "API for accessing GitHub repositories and their branches")
=======
>>>>>>> new-origin/master
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

<<<<<<< HEAD
    @Operation(summary = "Get repositories", description = "Retrieve non-forked repositories for a given GitHub username, including branch names and last commit SHA.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved repositories",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RepositoryInfo.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
=======
>>>>>>> new-origin/master
    @GetMapping("/repos/{username}")
    public ResponseEntity<List<RepositoryInfo>> getUserRepositories(@PathVariable String username) {
        List<RepositoryInfo> repositories = gitHubService.getUserRepositories(username);
        return ResponseEntity.ok(repositories);
    }

    @ExceptionHandler(ResponseStatusException.class)
<<<<<<< HEAD
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
=======
>>>>>>> new-origin/master
    public ResponseEntity<?> handleException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(Map.of("status", ex.getStatusCode().value(), "message", ex.getReason()));
    }
}