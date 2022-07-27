
pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
         stage('Clone repository') { 
            steps { 
                script{
                checkout scm
                }
            }
        }

        stage('Build') { 
            steps { 
                script{
                service1 = docker.build("service1.app")
                service2= docker.build("service2.app")
                }
            }
        }
        stage('Test'){
            steps {
                 echo 'Empty'
            }
        }
        stage('Push') {
            steps {
                script{
                        docker.withRegistry('https://720766170633.dkr.ecr.us-east-2.amazonaws.com', 'ecr:us-east-2:aws-credentials') {
                    service1.app.push("${env.BUILD_NUMBER}")
                    app.push("latest")
                    }
                }
            }
        }
        stage('Deploy'){
            steps {
                 sh 'kubectl apply -f deployment.yml'
            }
        }
        stage('SonarQube analysis') {
            steps {
                script{
                def scannerHome = tool 'sonarscan';
                withSonarQubeEnv('sonarqube') {
                    sh "${tool("sonarscan")}/bin/sonar-scanner \
                        -Dsonar.projectKey=app \
                        -Dsonar.projectName=app"
                    }
                }
            }
        }

    }
}
        
