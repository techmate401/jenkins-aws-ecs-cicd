pipeline {
    agent any
    environment {
        AWS_ACCOUNT_ID = credentials('AWS_ACCOUNT_ID')
        AWS_DEFAULT_REGION = "us-east-1"
        IMAGE_REPO_NAME = "hello-nginx"
        IMAGE_TAG = "v1"
        AWS_CREDENTIALS_ID = 'aws-ecr-cred'
        ECS_CLUSTER_NAME = 'test-cluster'
        ECS_SERVICE_NAME = 'service-task'
        ECS_TASK_DEFINITION_NAME = 'my-task-3'
        AWS_CREDENTIALS_FILE = credentials('AWS_CREDENTIALS_FILE')
    }
    stages {
        stage('Logging into AWS ECR') {
            steps {
                script {
                    withCredentials([
                        [ $class: 'AmazonWebServicesCredentialsBinding',
                          accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                          secretKeyVariable: 'AWS_SECRET_ACCESS_KEY',
                          credentialsId: AWS_CREDENTIALS_ID ]
                    ]) {
                        def ecrLogin = sh(script: "aws ecr get-login-password --region ${AWS_DEFAULT_REGION}", returnStdout: true).trim()
                        sh "aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
                    }
                }
            }
        }
        stage('Cloning Git') {
            steps {
                script {
                    checkout scm: [$class: 'GitSCM', branches: [[name: '*/master']], userRemoteConfigs: [[url: 'https://github.com/harshnagdev/docker-simple-webpage.git']]]
                }
            }
        }
        stage('Building image') {
            steps {
                script {
                    def dockerImage = docker.build("${IMAGE_REPO_NAME}:${IMAGE_TAG}")
                }
            }
        }
        stage('Pushing to ECR') {
            steps {
                script {
                    sh "docker tag ${IMAGE_REPO_NAME}:${IMAGE_TAG} ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                    sh "docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                }
            }
        }
        stage('Update ECS Task Definition') {
            steps {
               withCredentials([
                    [ $class: 'AmazonWebServicesCredentialsBinding',
                      credentialsId: AWS_CREDENTIALS_ID,
                      accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                      secretKeyVariable: 'AWS_SECRET_ACCESS_KEY' ]
                ]) {
                    script {
                        def ecsTaskDefinitionJson = readFile(AWS_CREDENTIALS_FILE).trim()
                        sh "aws ecs register-task-definition --region ${AWS_DEFAULT_REGION} --cli-input-json '${ecsTaskDefinitionJson}'"
                    }
                }
            }
        }
       stage('Deploy to ECS') {
    steps {
        withCredentials([
            [ $class: 'AmazonWebServicesCredentialsBinding',
              credentialsId: AWS_CREDENTIALS_ID,
              accessKeyVariable: 'AWS_ACCESS_KEY_ID',
              secretKeyVariable: 'AWS_SECRET_ACCESS_KEY' ]
        ]) {
            script {
                def latestRevision = sh(script: "aws ecs describe-task-definition --region ${AWS_DEFAULT_REGION} --task-definition ${ECS_TASK_DEFINITION_NAME} --query 'taskDefinition.revision'", returnStdout: true).trim()
                sh "aws ecs update-service --region ${AWS_DEFAULT_REGION} --cluster ${ECS_CLUSTER_NAME} --service ${ECS_SERVICE_NAME} --task-definition ${ECS_TASK_DEFINITION_NAME}:${latestRevision}"
            }
        }
             }
    }

}
}






