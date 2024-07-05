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

- **Jenkins:** Ensure Jenkins is installed and configured.
- **AWS Account:** An active AWS account with ECS and ECR set up.
- **Docker:** Docker installed on the Jenkins agent.

### Jenkins Setup

1. **Install Jenkins Plugins:**
   - Install the following Jenkins plugins:
     - `Pipeline`
     - `Amazon ECR`
     - `Git`
     - `Credentials Binding`

2. **Add AWS Credentials to Jenkins:**
   - Go to **Jenkins Dashboard** > **Manage Jenkins** > **Manage Credentials**.
   - Add AWS credentials:
     - **Kind:** AWS Credentials
     - **ID:** `aws-ecr-cred`
     - **Access Key ID:** Your AWS Access Key ID
     - **Secret Access Key:** Your AWS Secret Access Key

3. **Create GitHub Repository:**
   - Clone this repository or create a new one with a similar structure.
   - Push your code to the GitHub repository.

### Pipeline Configuration

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/jenkins-aws-ecs-cicd.git
   cd jenkins-aws-ecs-cicd
