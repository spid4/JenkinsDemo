pipeline {
    agent any

    stages {

        stage('Docker build'){
            steps{
                script{
                    docker.build('jenkins/demo')
                }
            }
        }

        stage('Docker push'){
            steps{
                script{
                    tag=$(date '+%s')
                    docker.withRegistry('https://003656774475.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:karthik-aws') {
                        docker.image('jenkins/demo').push(tag)
                    }
                }
            }
        }
    }
}