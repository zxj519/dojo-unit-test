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
    stage('Compile & Test') {
      steps {
        script {
          sh './gradlew clean test'
        }
      }
    }
    stage('Test Coverage Check') {
      steps {
        script {
          sh './gradlew jacocoTestCoverageVerification'
        }
      }
    }
    stage('Build package') {
      steps {
        script {
          sh './gradlew build' -i
        }
      }
    }
  }
  post {
    always {
      script {
        sh 'ls -lrt'
        junit 'build/test-results/test/*.xml'
        jacoco(
            execPattern: '**/jacoco/jacoco.exec',
            classPattern: '**/classes/main/java/**',
            sourcePattern: '**/src/main/java/**',
            inclusionPattern: '**/*.class',
            exclusionPattern: '**/src/test/**',
            runAlways: true
        )
      }
    }
  }
}