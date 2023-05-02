pipeline {
    agent any
    
    environment {   
        dockerImage = ''
        registry = "eyadrihmi/achat"
    }
    
    stages {
        stage('GIT') { 
            steps {
                git url:'https://github.com/eyadrihmi/projet_Devops.git'
            }
        }
	
	
      
        stage('UNIT Testing') {
            steps {
                sh 'mvn test'
            }
        }
	
      
        stage('Integration testing') {
            steps {
                sh 'mvn verify -DskipUnitTests'
            }
        }

         stage('Maven Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        
        stage("Running Tests with Mockito & JUnit Test") {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('SonarQube analysis') {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'sonar-api') {
                        sh 'mvn clean package sonar:sonar'
                    }
                }  
            }
        }
         
	
        stage('Docker Image Build'){
            steps{
                script{
                    sh 'docker image build -t $JOB_NAME:v1.$BUILD_ID .'
		    sh 'docker image tag $JOB_NAME:v1.$BUILD_ID eyadrihmi/$JOB_NAME:v1.$BUILD_ID'
		    sh 'docker image tag $JOB_NAME:v1.$BUILD_ID eyadrihmi/$JOB_NAME:latest'
                }  
            }
        }
        
        stage('Push image to the DockerHub'){
            steps{
                script{
                  withCredentials([string(credentialsId: 'github_cred', variable: 'docker_hub_cred')]) {
		        sh 'docker login -u eyadrihmi -p ${docker_hub_cred} docker.io'
			sh 'docker image push eyadrihmi/$JOB_NAME:v1.$BUILD_ID'
			sh 'docker image push eyadrihmi/$JOB_NAME:latest' 
                    }
                }  
            }
        }
    }
}
