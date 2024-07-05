# Jenkins AWS ECS CI/CD Pipeline

This repository contains the configuration and scripts for a Jenkins CI/CD pipeline designed to deploy Docker applications to AWS ECS. The pipeline automates the process of building Docker images, pushing them to AWS ECR, and updating ECS task definitions and services.

## Key Features

- **Jenkins Pipeline Configuration:** Automates the entire build and deployment process using Jenkins.
- **AWS Integration:** Seamlessly integrates with AWS services, including ECR for Docker image storage and ECS for container orchestration.
- **Docker Support:** Builds and manages Docker images for deployment.
- **Secure Credentials Management:** Uses Jenkins credentials binding for secure handling of AWS credentials.
- **Automated Deployments:** Updates ECS task definitions and services with new revisions for streamlined deployments.

## Getting Started

### Prerequisites

1. **Jenkins:** Ensure Jenkins is installed and configured.
2. **AWS Account:** An active AWS account with ECS and ECR set up.
3. **Docker:** Docker installed on the Jenkins agent.

### Jenkins Configuration

1. **Install Jenkins Plugins:**
   - Go to `Manage Jenkins` > `Manage Plugins`.
   - Install the following plugins:
     - Pipeline
     - Amazon ECR
     - Git

2. **Add AWS Credentials to Jenkins:**
   - Go to `Manage Jenkins` > `Manage Credentials` > `(global)` > `Add Credentials`.
   - Select `AWS Credentials` from the Kind dropdown.
   - Fill in your AWS Access Key ID and Secret Access Key.
   - Click `OK`.

3. **Add AWS Credentials File to Jenkins:**
   - Go to `Manage Jenkins` > `Manage Credentials` > `(global)` > `Add Credentials`.
   - Select `Secret file` from the Kind dropdown.
   - Upload your AWS credentials file (e.g., `~/.aws/credentials`).
   - Click `OK`.

4. **Create Jenkins Pipeline Job:**
   - Create a new Pipeline job in Jenkins.
   - In the Pipeline section, select `Pipeline script from SCM`.
   - Configure your Git repository URL and branch.

### Running the Pipeline

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/techmate401/jenkins-aws-ecs-cicd.git
   cd jenkins-aws-ecs-cicd

