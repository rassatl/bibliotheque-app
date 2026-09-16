pipeline {

  agent any

  tools {
    maven 'M3'
    jdk   'JDK17'
  }

  // Jenkins local, non exposé publiquement : pas de webhook GitHub possible.
  // Le déclenchement se fait par scrutation du dépôt toutes les 5 minutes.
  triggers {
    pollSCM('H/5 * * * *')
  }

  stages {

    stage('Checkout') {
      steps {
        git branch: 'main',
            url: 'https://github.com/VOTRE-UTILISATEUR/bibliotheque-app.git'
      }
    }

    stage('Build') {
      steps {
        sh 'mvn -B clean compile'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn -B test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }

    stage('Package') {
      steps {
        sh 'mvn -B package -DskipTests'
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }

    stage('SonarQube Analysis') {
      steps {
        // 'SonarQube' = nom du serveur declare dans Manage Jenkins > System.
        // Le jeton est stocke comme credential Jenkins, jamais en clair ici.
        withSonarQubeEnv('SonarQube') {
          sh 'mvn -B sonar:sonar -Dsonar.qualitygate.wait=true'
        }
      }
    }

    stage('Quality Gate') {
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }

  }
}
