pipeline {
    agent any

    environment {
        // 📦 CONFIGURACIÓN GENERAL
        PROJECT_NAME     = 'mall-tcp'               // Nombre del proyecto (usado para JAR y servicio)
        BUILD_DIR        = 'build/libs'                 // Carpeta donde se genera el JAR
        JAR_PATTERN      = "mall-*.jar"      // Patrón del nombre del JAR
        SERVICE_NAME     = "${PROJECT_NAME}.service"    // Nombre del servicio systemd

        // 🌍 CONFIGURACIÓN DEL SERVIDOR
        SERVER_USER      = "root"
        SERVER_HOST      = "173.212.201.115"
        SERVER_PORT      = "3625"
        SERVER_PATH      = "/home/${PROJECT_NAME}/"

        // 🔐 CREDENCIALES
        SSH_CREDENTIAL_ID  = 'server-kryos-sh'          // ID de la credencial SSH
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                script {
                    def currentBranch = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                    echo "Se ha traído la rama: ${currentBranch}"
                }
            }
        }


        stage('Build') {
            steps {
                echo 'Dando permisos de ejecución a gradlew...'
                sh 'chmod +x gradlew'
                echo "Construyendo el proyecto ${PROJECT_NAME}..."
                sh './gradlew clean build -x test'
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo 'Buscando el archivo JAR generado...'
                    def jarFile = sh(
                        script: "find ${BUILD_DIR} -name '${JAR_PATTERN}' | head -n 1",
                        returnStdout: true
                    ).trim()

                    if (jarFile) {
                        echo "JAR encontrado: ${jarFile}"

                        withCredentials([sshUserPrivateKey(credentialsId: "${SSH_CREDENTIAL_ID}", keyFileVariable: 'SSH_KEY')]) {
                            def jarName = jarFile.tokenize('/').last()

                            echo "Copiando ${jarName} al servidor remoto..."
                            sh "scp -i $SSH_KEY -P ${SERVER_PORT} ${jarFile} ${SERVER_USER}@${SERVER_HOST}:${SERVER_PATH}${jarName}"

                            echo 'Dando permisos al archivo copiado...'
                            sh "ssh -i $SSH_KEY -p ${SERVER_PORT} ${SERVER_USER}@${SERVER_HOST} 'chmod 777 ${SERVER_PATH}${jarName}'"

                            echo "Reiniciando el servicio ${SERVICE_NAME} en el servidor..."
                            sh "ssh -i $SSH_KEY -p ${SERVER_PORT} ${SERVER_USER}@${SERVER_HOST} 'sudo systemctl restart ${SERVICE_NAME}'"
                        }
                    } else {
                        error "❌ No se encontró el archivo JAR que coincida con el patrón '${JAR_PATTERN}'."
                    }
                }
            }
        }
    }

    post {
        success {
            echo '✅ El pipeline se ejecutó correctamente.'
        }
        failure {
            echo '❌ Error en la ejecución del pipeline.'
            script {
                def errorMessage = currentBuild.currentResult == 'FAILURE'
                    ? "Error en el pipeline: ${currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.shortDescription}"
                    : "Error desconocido."
                echo errorMessage

                echo 'Obteniendo logs del servicio remoto...'
                sh "echo '⚠️ Este comando es local, no se puede obtener journalctl del servidor remoto aquí sin SSH.'"

                echo 'Obteniendo logs de Jenkins (workspace)...'
                sh 'cat $WORKSPACE/logs/*.log > jenkins-logs.txt || echo "No se encontraron logs en el workspace."'

                archiveArtifacts artifacts: '*.txt', allowEmptyArchive: true
            }
        }
    }
}
