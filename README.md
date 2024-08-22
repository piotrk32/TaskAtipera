# GitHub API Consumer

This Spring Boot application provides an API to retrieve non-forked repositories for a given GitHub user along with their branches and last commit SHA.

## Requirements

- Java 21
- Maven or Gradle
- Internet connection (for accessing the GitHub API)
- A GitHub account for generating a personal access token (PAT)

## Setup

1. Clone the repository:
    ```
    git clone https://github.com/yourusername/github-api-consumer.git
    cd github-api-consumer
    ```

2. Build the project:
    ```
    ./gradlew build
    ```

3. Generate a GitHub Personal Access Token (PAT):

To avoid hitting rate limits when accessing the GitHub API, you need to generate a personal access token (PAT) to authenticate your requests.

Log in to GitHub:

Go to GitHub and log in to your account.
Go to Developer Settings:

In the top right corner of GitHub, click your profile picture, then click Settings.
Scroll down to Developer settings.
Generate a New Token:

Click on Personal access tokens and then Tokens (classic).
Click Generate new token.
Give your token a descriptive name, like "GitHub API Consumer Token".
Under Scopes, select the appropriate permissions. For basic API requests, you can select repo and read:org.
Generate and Copy the Token:

Click Generate token.
Important: Copy the token immediately as you won't be able to see it again.

4. Set the Personal Access Token in application.properties:
   ```
   github.api.token=your_token

   ```

5. Run the application:
    ```
    ./gradlew bootRun
    ```

## API Documentation

### Get User Repositories

You can endpoint using swagger : http://localhost:8080/swagger-ui/index.html

**Endpoint**: `/api/repos/{username}`  
**Method**: GET  
**Headers**: `Accept: application/json`  
**Response**:
```json
[
    {
        "name": "repo-name",
        "owner": {
            "login": "owner-login"
        },
        "branches": [
            {
                "name": "branch-name",
                "commit": {
                    "sha": "commit-sha"
                }
            }
        ]
    }
    
    
]
