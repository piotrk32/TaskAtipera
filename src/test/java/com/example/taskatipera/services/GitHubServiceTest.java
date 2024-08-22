package com.example.taskatipera.services;

import com.example.taskatipera.models.Branch;
import com.example.taskatipera.models.Commit;
import com.example.taskatipera.models.Owner;
import com.example.taskatipera.models.RepositoryInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class GitHubServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubService gitHubService;

    @Mock
    private ClientHttpRequestInterceptor tokenInterceptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserRepositories_Success() {

        RepositoryInfo repo1 = new RepositoryInfo();
        repo1.setName("Repo1");
        repo1.setFork(false);

        Owner owner1 = new Owner();
        owner1.setLogin("owner1");
        repo1.setOwner(owner1);

        RepositoryInfo[] repos = {repo1};

        Branch branch1 = new Branch();
        branch1.setName("main");
        Commit commit1 = new Commit();
        commit1.setSha("abc123");
        branch1.setCommit(commit1);

        Branch[] branches = {branch1};

        when(restTemplate.getForObject(anyString(), eq(RepositoryInfo[].class))).thenReturn(repos);
        when(restTemplate.getForObject(anyString(), eq(Branch[].class))).thenReturn(branches);

        List<RepositoryInfo> result = gitHubService.getUserRepositories("validUser");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Repo1", result.get(0).getName());
        assertEquals("owner1", result.get(0).getOwner().getLogin());
        assertEquals("main", result.get(0).getBranches().get(0).getName());
        assertEquals("abc123", result.get(0).getBranches().get(0).getCommit().getSha());
    }

    @Test
    public void testGetUserRepositories_UserNotFound() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(RepositoryInfo[].class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            gitHubService.getUserRepositories("invalidUser");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    @Test
    public void testGetUserRepositories_RateLimitExceeded() {

        when(restTemplate.getForObject(anyString(), eq(RepositoryInfo[].class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            gitHubService.getUserRepositories("validUser");
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("GitHub API rate limit exceeded. Please try again later.", exception.getReason());
    }

    @Test
    void testGetUserRepositories_InvalidCredentials() {
        when(restTemplate.getForObject(anyString(), eq(RepositoryInfo[].class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> gitHubService.getUserRepositories("validUser")
        );

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }
    @Test
    public void testGetBranches_NoBranches() {

        when(restTemplate.getForObject(anyString(), eq(Branch[].class)))
                .thenReturn(new Branch[0]);
        List<Branch> result = gitHubService.getBranches("owner", "repo");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
