pipeline {
    agent any

    tools {
        maven 'Maven 3.9'   // Configure-le dans "Global Tool Configuration"
        jdk 'JDK 21'        
    }

    environment {
        GPG_KEY_ID = credentials('gpg-key-id')
        GPG_SECRET = credentials('gpg-secret')
        OSSRH_USERNAME = credentials('ossrh-username')
        OSSRH_PASSWORD = credentials('ossrh-password')
    }

    stages {
        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('Sign') {
            steps {
                sh 'mvn verify -Psign-artifacts'
            }
        }

        stage('Deploy') {
            steps {
                sh 'mvn clean deploy -Psign-artifacts'
            }
        }
    }
}
