pipeline {
    agent any

    stages {

        stage('Identify Services'){
            steps{
                script{
                    sh export SERVICE_FOLDERS=$(git --diff)
                }
            }
        }

        stage('Trigger Build'){
            steps{
                script{
                    sh foreach childservice in SERVICE_FOLDERS
                        build childservice
                    end foreach
                }
            }
        }
    }
}