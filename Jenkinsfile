def duration = 30
def timeoutUnits = 'MINUTES'
def gitCommit = null

def getGitCommit() {
  return sh(returnStdout: true, script: "git rev-list --abbrev-commit --max-count=1 HEAD").trim()
}

pipeline {
  options {
    timeout(time: duration, unit: timeoutUnits)
  }
  agent any
  stages {
    stage('Clone') {
      steps {
        script {
          gitCommit = getGitCommit()
          echo "Start build [${gitCommit}] "
        }
      }
    }
    stage('Verify code') {
      steps {
        script {
          sh 'mvn clean test verify'
        }
      }
    }
    stage('Build package') {
      steps {
        script {
          sh 'mvn package'
        }
      }
    }
  }
  post {
    always {
      steps {
        script {
          junit 'target/surefire-reports/*.xml'
          jacoco(
              execPattern: '**/jacoco.exec',
              classPattern: '**/target/classes/**',
              sourcePattern: '**/src/main/java/**',
              inclusionPattern: '**/*.class',
              exclusionPattern: '**/src/test/**'
          )
        }
      }
    }
  }
}