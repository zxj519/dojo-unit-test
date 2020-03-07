def duration = arguments.duration ?: 30
def timeoutUnits = arguments.timeoutUnits ?: 'MINUTES'
def gitCommit = null

def getGitCommit() {
  return sh(returnStdout: true, script: "git rev-list --abbrev-commit --max-count=1 HEAD").trim()
}

pipeline {
  options {
    timeout(time: duration, unit: timeoutUnits)
  }
  agent none
  stages {
    stage('Clone') {
      steps {
        script {
          checkout scm
          gitCommit = getGitCommit()
        }
      }
    }
    stage('compile & test') {
      steps {
        script {
          sh 'mvn compile'
          sh 'mvn test'
        }
      }
    }
    stage('build package') {
      steps {
        script {
          sh 'mvn package'
        }
      }
    }
  }
}