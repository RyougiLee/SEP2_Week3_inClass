pipeline {
    agent any

    tools {

        jdk 'jdk21'
        maven 'maven3'
    }

    environment {
        IMAGE_NAME = "calculator-app"
        BUILD_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Compile') {
            steps {
                echo 'Starting Maven Build...'

                bat 'mvn clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running JUnit Tests...'
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Coverage (JaCoCo)') {
            steps {
                echo 'Generating Coverage Report...'

                jacoco execPattern: 'target/*.exec'
            }
        }

        stage('Package JAR') {
            steps {
                echo 'Packaging application into JAR...'
                bat 'mvn package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker Image: ${IMAGE_NAME}:${BUILD_TAG}"

                bat "docker build -t ${IMAGE_NAME}:${BUILD_TAG} ."
                bat "docker tag ${IMAGE_NAME}:${BUILD_TAG} ${IMAGE_NAME}:latest"
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}