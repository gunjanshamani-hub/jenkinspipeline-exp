/**
 * Jenkins Declarative Pipeline for the Java compilation experiment (Experiment 6).
 * This pipeline pulls the source code from GitHub, compiles it, runs it, and cleans up.
 *
 * Repository: https://github.com/Harshita-lalchandani16/Jenkins-pipeline1.git
 * Class Name: HelloWorld (to execute HelloWorld.java)
 *
 * NOTE: This version assumes HelloWorld.java is located inside a 'src' subdirectory.
 */
pipeline {
    agent any

    environment {
        CLASS_NAME = 'HelloWorld'
        GIT_REPO   = 'https://github.com/gunjanshamani-hub/jenkinspipeline-exp.git'
        // Define the subdirectory where the Java source file is located
        SOURCE_DIR = 'src' 
    }

    triggers {
        cron('H 2 * * *')
    }

    stages {
        stage('Checkout Source Code') {
            steps {
                echo "Cloning repository: ${env.GIT_REPO}"
                git branch: 'main', url: env.GIT_REPO
            }
        }

        stage('Compile Java Code') {
            steps {
                echo "Compiling Java code in the ${env.SOURCE_DIR} directory..."
                // Execute compilation command from the workspace root, referencing the subdirectory
                bat "javac ${env.SOURCE_DIR}/${env.CLASS_NAME}.java"
            }
        }

        stage('Run Java Application') {
            steps {
                echo "Running the compiled Java application..."
                // The class file is created in 'src' but running must be done from the root
                // The '-cp' (classpath) argument tells Java where to find the compiled .class file.
                bat "java -cp ${env.SOURCE_DIR} ${env.CLASS_NAME}"
            }
        }

        stage('Clean Up') {
            steps {
                echo "Removing the generated class file..."
                // Removes the generated .class file from the subdirectory
                bat "del ${env.SOURCE_DIR}\\${env.CLASS_NAME}.class" 
            }
        }
    }
}
