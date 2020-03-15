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
    stage('Setup test env') {
      steps {
        script {
          sh 'docker-compose up -d'
          sh 'export PGPASSWORD=\'local\' && until psql -h 127.0.0.1 -p 5433 -U local -q -d test_db -c \'SELECT 1\'; do echo "Waiting for postgreSQL to start"; sleep 10; done && unset PGPASSWORD'
        }
      }
    }
    stage('Compile & Test') {
      steps {
        script {
          sh './gradlew clean test -Dprofile=ci-test'
        }
      }
    }
    stage('Tear Down test env') {
      steps {
        script {
          sh 'docker-compose down'
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
          sh './gradlew build'
          archiveArtifacts artifacts: 'build/libs/dojo-unit-test-0.0.1-SNAPSHOT.jar', onlyIfSuccessful: true
        }
      }
    }
  }
  post {
    always {
      script {
        junit 'build/test-results/test/*.xml'
        jacoco(
            execPattern: '**/jacoco/jacoco.exec',
            classPattern: '**/classes/java/main/**',
            sourcePattern: '**/src/main/java/**',
            inclusionPattern: '**/*.class',
            exclusionPattern: '**/src/test/**',
            runAlways: true
        )
      }
    }
  }
}