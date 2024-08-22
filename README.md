# GitHub API Consumer

This Spring Boot application provides an API to retrieve non-forked repositories for a given GitHub user along with their branches and last commit SHA.

## Requirements

- Java 21
- Maven or Gradle
- Internet connection (for accessing the GitHub API)

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

3. Run the application:
    ```
    ./gradlew bootRun
    ```

## API Documentation

### Get User Repositories

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
