pipeline {
    agent any
    environment {
            API_ACCESS_KEY = credentials('ncp-api-access-key')
            API_SECRET_KEY = credentials('ncp-api-secret-key')
        }

    stages {
        stage('Prepare') {
            steps {
                echo 'Preparing...'
                git branch: 'main', url: 'https://github.com/f-lab-edu/nutri-diary.git'
            }

            post {
                success {
                    echo 'Preparation completed successfully!'
                }
                failure {
                    echo 'Preparation failed!'
                }
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }

            post {
                success {
                    echo 'Build completed successfully!'
                }
                failure {
                    echo 'Build failed!'
                }
            }
        }

        stage('Upload') {
            steps {
                sh './script/upload.sh'
            }

            post {
                success {
                    echo 'Upload completed successfully!'
                }
                failure {
                    echo 'Upload failed!'
                }
            }

        stage('Deploy') {
            steps {
                sh './script/deploy.sh'
            }

            post {
                success {
                    echo 'Deploy completed successfully!'
                }
                failure {
                    echo 'Deploy failed!'
                }
            }
        }
    }

    post {
        success {
            echo 'Build, Test, and Deploy completed successfully!'
        }
        failure {
            echo 'Build or Deploy failed!'
        }
    }
}
