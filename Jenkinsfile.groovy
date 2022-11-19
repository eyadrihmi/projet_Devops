pipeline {
    agent any
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "MAVEN"
    }
        environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.1.25:8081"
        NEXUS_REPOSITORY = "deployRepo"
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
        DOCKER_HUB = "dockerhub-user-credentials"
        dockerImage = ''
        registry = "wassimslim/achat"
        DOCKER_CREDS_USR = "wassimslim"
        DOCKER_CREDS = credentials('dockerhub-user-credentials')
            }
 stages{

    stage('GIT') { 
        steps{
        git url:'https://github.com/malekbenabdellatif/projet_Devops.git', branch : 'wassim-prod'
             }
    }
    stage('MAVEN clean'){
         steps{
           echo 'Cleaning Project '
           echo "Maven Version "
           sh "mvn -Dmaven.test.failure.ignore=true clean package"
              }
      }
    stage('MAVEN COMPILE'){
            steps{
                script{
                        sh 'mvn clean install -DskipTests'
                }
            }
        }
    stage("Runing Tests with Mockito & JUNIT TEST") {
               steps{
                   sh 'mvn test'
                }
        }

    stage("MAVEN SonarQube") {
        steps{
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=admin123 -DskipTests -X'
            }
        }
    stage('MAVEN Nexus'){
        steps {
  script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
        }
        }
           stage('Building our image') {
                steps {
                    script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
            stage('Deploy our image') {
                steps {
                    script {
                        docker.withRegistry( '', DOCKER_HUB ) {
                        dockerImage.push()
                    }
                }
            }
        }
            stage('Cleaning up') {
                steps {
                    sh "docker rmi $registry:$BUILD_NUMBER"
            }
            }
           stage('Docker Compose UP SPRING BOOT & MYSQL & Angular') { 
        steps{
            sh "docker-compose -f /root/springApp-mysql/docker-compose.yml up -d"
             }
    }
        } 
      post {
        always {
            
	    emailext to: "wassim.slim@esprit.tn",
            subject: "jenkins build :${currentBuild.currentResult}: ${env.JOB_NAME}",
            body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\n More Infos can be found here : ${env.BUILD_URL}"
         }
    }
}

