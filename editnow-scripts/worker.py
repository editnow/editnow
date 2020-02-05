from argparse import ArgumentParser
import json, os
import subprocess
from subprocess import PIPE
import pika
from pika.exceptions import AMQPConnectionError
import base64
import logging

ACTION_QUEUE_NAME = 'action'
COMPLETED_ACTION_QUEUE_NAME = 'completedAction'
SCRIPTS_FOLDER_PATH = './scripts/'
IMAGES_FOLDER_PATH = './images/'
SUCCESS_OUTPUT = 0

args = []
rabbitMQConnection = None
dbConnection = None


def getFilePathByActionName(name):
    acs = {
        'GRAYSCALE': 'preprocessing/grayscale_opencv.py',
        'ROTATE': 'preprocessing/rotate_opencv.py',
        'RESIZE': 'preprocessing/resize_opencv.py',
        'MEDIAN_BLUR': 'preprocessing/median_blur_opencv.py',
        'GAUSSIAN_BLUR': 'preprocessing/gaussian_blur_opencv.py',
        'BILATERAL_FILTER': 'preprocessing/bilateral_filter_opencv.py',
        'THRESHOLD': 'segmentation/threshold_opencv.py',
        'PREWITT': 'edges/prewitt_scikit.py',
        'ROBERTS': 'edges/roberts_scikit.py',
        'SCHARR': 'edges/scharr_scikit.py',
        'SOBEL': 'edges/sobel_scikit.py',
        'MORPHOLOGY_TRANSFORM': 'morphology/morphology_transform_opencv.py'
    }
    return acs.get(name, None)


def parseArguments():
    global args
    parser = ArgumentParser()
    parser.add_argument('-l', '--local', default=False,
                        help='Configure local environment if true - else configure for cloud (PCF)')
    args = parser.parse_args()


def prepareEnvironment():
    # global amqp_uri
    initRabbitMQConnection()
    createDirectoriesIfNotExists()


def initRabbitMQConnection():
    global rabbitMQConnection, args

    amqp_uri = pika.ConnectionParameters('localhost')
    if not args.local:
        amqp_uri = pika.URLParameters(json.loads(os.environ['VCAP_SERVICES'])['cloudamqp'][0]['credentials']['uri'])

    try:
        rabbitMQConnection = pika.BlockingConnection(amqp_uri)
    except AMQPConnectionError:
        print(' RabbitMQ connection problem')


def createDirectoriesIfNotExists():
    if not os.path.exists(os.path.dirname(IMAGES_FOLDER_PATH)):
        os.makedirs(os.path.dirname(IMAGES_FOLDER_PATH))
        print('Path: \'' + IMAGES_FOLDER_PATH + '\' created')


def runScriptAndWaitForFinish(actionScriptName, args):
    scriptPath = SCRIPTS_FOLDER_PATH + actionScriptName

    logInfo("Script args: " + str(args))
    scriptProcess = subprocess.Popen([scriptPath] + args, shell=False, stdout=PIPE, stderr=PIPE)
    scriptProcessWorking = scriptProcess.poll() is None
    while scriptProcessWorking:
        scriptProcessWorking = scriptProcess.poll() is None
    if scriptProcess.returncode != 0:
        return getErrorMessage(scriptProcess)
    return SUCCESS_OUTPUT


def getErrorMessage(scriptProcess):
    output, error = scriptProcess.communicate()
    logInfo("ERROR")
    logInfo(error)
    strError = str(error)
    startMessage = strError.find(' error: ') - 8
    return str(error[startMessage:len(strError)])


def handleActionWithException(ch, method, properties, body):
    try:
        handleAction(ch, method, properties, body)
    except Exception as e:
        logging.error('Error at %s', str(e), exc_info=e)
        #TODO send message about fail


def handleAction(ch, method, properties, body):
    bodyDict = json.loads(body.decode('utf-8'))
    actionName = bodyDict['actionName']
    logInfo('Handle action: ' + actionName)

    actionScriptName = getFilePathByActionName(actionName)

    if actionScriptName is not None:
        inputImageName = bodyDict['inputImageName']
        saveImageFromBase64(inputImageName, bodyDict['imageBase64'])
        outputImageName = bodyDict['outputImageName']
        parameters = prepareParameters(bodyDict['parameterDtos'])

        args = [IMAGES_FOLDER_PATH, inputImageName, outputImageName]
        args.extend(parameters)

        output = runScriptAndWaitForFinish(actionScriptName, args)

        logInfo('Finished script for action: ' + actionName + '\n')

        data = prepareData(output, bodyDict['actionId'], outputImageName)

        sendData(data)


def prepareParameters(parameterDtos):
    parameters = []
    for parameterDto in parameterDtos:
        parameters.append(parameterDto['value'])
    return parameters


def saveImageFromBase64(inputImageName, imageBase64):
    imagePath = IMAGES_FOLDER_PATH + inputImageName
    with open(imagePath, 'wb') as image:
        image.write(base64.b64decode(imageBase64))


def logInfo(text):
    print(text)


def sendData(data):
    completedActionChannel = rabbitMQConnection.channel()
    completedActionChannel.queue_declare(queue=COMPLETED_ACTION_QUEUE_NAME, durable=True)
    completedActionChannel.basic_publish(exchange='', routing_key=COMPLETED_ACTION_QUEUE_NAME, body=json.dumps(data))
    completedActionChannel.close()


def prepareData(output, actionId, outputImageName):
    resultData = {'actionId': actionId}
    if isinstance(output, int) and output == SUCCESS_OUTPUT:
        with open(IMAGES_FOLDER_PATH + outputImageName, 'rb') as image:
            resultData['imageBase64'] = base64.b64encode(image.read()).decode('ascii')
    else:
        resultData['errorMsg'] = output
    return resultData


if __name__ == '__main__':
    parseArguments()
    prepareEnvironment()

    actionChannel = rabbitMQConnection.channel()
    actionChannel.queue_declare(queue=ACTION_QUEUE_NAME, durable=True)
    actionChannel.basic_consume(queue=ACTION_QUEUE_NAME, auto_ack=True, on_message_callback=handleActionWithException)

    print('\n\nWaiting for messages on action queue..')
    actionChannel.start_consuming()

