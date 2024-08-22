package com.example.taskatipera.services;

import com.example.taskatipera.models.Branch;
import com.example.taskatipera.models.Commit;
import com.example.taskatipera.models.RepositoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
=======
import org.springframework.http.HttpStatus;
>>>>>>> new-origin/master
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

<<<<<<< HEAD
import java.io.IOException;
=======
>>>>>>> new-origin/master
import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubService.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String GITHUB_API_URL = "https://api.github.com";

<<<<<<< HEAD
    @Value("${github.api.token}")
    private String gitHubToken;

    public GitHubService() {
        restTemplate.getInterceptors().add(new BearerTokenInterceptor());
    }

=======
>>>>>>> new-origin/master
    public List<RepositoryInfo> getUserRepositories(String username) {
        String url = GITHUB_API_URL + "/users/" + username + "/repos";
        RepositoryInfo[] repositories;

        try {
            repositories = restTemplate.getForObject(url, RepositoryInfo[].class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.warn("User not found: {}", username);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
<<<<<<< HEAD
            } else if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                logger.error("GitHub API rate limit exceeded.");
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "GitHub API rate limit exceeded. Please try again later.");
=======
>>>>>>> new-origin/master
            } else {
                logger.error("Error occurred while fetching repositories for user: {}", username, e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching repositories");
            }
        }

        List<RepositoryInfo> filteredRepos = new ArrayList<>();
        for (RepositoryInfo repo : repositories) {
            if (!repo.isFork()) {
                List<Branch> branches = getBranches(repo.getOwner().getLogin(), repo.getName());
                repo.setBranches(branches);
                filteredRepos.add(repo);
            }
        }
        return filteredRepos;
    }

    private List<Branch> getBranches(String owner, String repoName) {
        String url = GITHUB_API_URL + "/repos/" + owner + "/" + repoName + "/branches";
        Branch[] branches = restTemplate.getForObject(url, Branch[].class);

        if (branches == null) {
            logger.warn("No branches found for repository: {} owned by {}", repoName, owner);
            return new ArrayList<>();
        }

        List<Branch> branchList = new ArrayList<>();
        for (Branch branch : branches) {
<<<<<<< HEAD
            if (branch.getCommit() != null) {
                branchList.add(branch);
=======
            Commit commit = branch.getCommit();
            if (commit != null) {
                branchList.add(branch);
            } else {
                logger.warn("Branch {} in repository {} has no commit", branch.getName(), repoName);
>>>>>>> new-origin/master
            }
        }
        return branchList;
    }
<<<<<<< HEAD

    private class BearerTokenInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + gitHubToken);
            return execution.execute(request, body);
        }
    }
=======
>>>>>>> new-origin/master
}
