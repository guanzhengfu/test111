pipeline {
    agent any
    triggers {
        GenericTrigger(
                genericVariables: [
                        [key: 'ref', value: '$.ref']
                ],
                causeString: 'Triggered on $ref',
                token: 'abc123',
                printContributedVariables: true,
                printPostContent: true,
                silentResponse: false,
                regexpFilterText: '$ref',
        )
    }
    stages {
        stage('Checkout') {
            steps {
                checkout([
                        $class                           : 'GitSCM',
                        branches                         : [[name: '*/master']],
                        doGenerateSubmoduleConfigurations: false
                ])
            }
        }

        stage('Confirm qa') {
            options {
                timeout(time: 840, unit: 'SECONDS')
            }

            input {
                message 'Do you want to delpoy to qa?'
                ok 'Yes, go ahead.'
            }

            steps {
                sh 'chmod 777 test.sh'
                sh './test.sh'
                echo 'qa'
            }
        }

        stage('Confirm staging') {
            options {
                timeout(time: 840, unit: 'SECONDS')
            }

            input {
                message 'Do you want to delpoy to staging?'
                ok 'Yes, go ahead.'
            }

            steps {
                echo 'staging'
            }
        }

        stage('Confirm prod') {
            options {
                timeout(time: 840, unit: 'SECONDS')
            }

            input {
                message 'Do you want to delpoy to prod?'
                ok 'Yes, go ahead.'
            }

            steps {
                echo 'prod'
            }
        }
    }
}