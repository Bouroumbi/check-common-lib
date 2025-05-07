pipeline {
    agent any

    tools {
        maven 'Maven 3.9'      // Doit exister dans "Global Tool Configuration"
        jdk 'JDK 21'           // Idem
    }

    stages {
        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('Deploy to OSSRH') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    withCredentials([
                        usernamePassword(credentialsId: 'ossrh', usernameVariable: 'OSSRH_USERNAME', passwordVariable: 'OSSRH_PASSWORD'),
                        string(credentialsId: 'gpg-passphrase', variable: 'GPG_PASSPHRASE')
                    ]) {
                        sh '''
                            mvn clean deploy \
                              --settings $MAVEN_SETTINGS \
                              -Psign-artifacts,gpg \
                              -Dgpg.passphrase=$GPG_PASSPHRASE
                        '''
                    }
                }
            }
        }
    }
}
