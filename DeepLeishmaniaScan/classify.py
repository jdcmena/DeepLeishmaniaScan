from __future__ import division # /: float div, //: integer div
from PIL import Image
import os
import argparse
import h5py
import numpy as np
import glob
import time
import pydot
import jsonreader
import json as simplejson
#import matplotlib.pyplot as plt
from keras.models import Sequential, model_from_json, Model
from keras.layers import Convolution2D, MaxPooling2D, GlobalAveragePooling2D, Activation, Dropout, Flatten, Dense, Reshape
from keras.layers.advanced_activations import PReLU
from keras.optimizers import SGD, RMSprop
from keras import backend as K
from keras.preprocessing.image import ImageDataGenerator, array_to_img, img_to_array, load_img
from keras.applications.inception_v3 import InceptionV3
#from keras.utils.visualize_util import plot
from keras.utils import np_utils
import keras

parser = argparse.ArgumentParser(description='classification script')
parser.add_argument('filepath')
parser.add_argument('imgPath')
catchedVars = vars(parser.parse_args())

def runModel(runConfigJson, imagePath):

    train_data_dir='conjuntoDeDatos'##'data/train'

    hiperparameters = jsonreader.readFileHip(runConfigJson) #TODO check array values

    modelPath = hiperparameters[0]
    nb_epoch_var = hiperparameters[1]
    samples_per_epoch_var = hiperparameters[2]
    lrate = hiperparameters[3]
    momentum_var = hiperparameters[4]
    #decay_lR_var = hiperparameters[4]
    nesterov_var = hiperparameters[5]
    batch_size_var = int(round((samples_per_epoch_var/10),0))
    img_width = 150
    img_height = 150
    nb_validation_samples = int(round((batch_size_var/2),0))


    print('loading model architecture...')
    json_file = open("models/"+str(modelPath)+"/"+str(modelPath)+"-arch.json", "r")
    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = model_from_json(loaded_model_json)
    print('loading model weights...')
    loaded_model.load_weights("models/"+str(modelPath)+"/"+str(modelPath)+'.h5')

    seq = Sequential()
    seq.add(loaded_model)

    decayRC = lrate/nb_epoch_var
    sgd = SGD(lr=lrate, momentum=momentum_var, decay=decayRC, nesterov=nesterov_var)
    seq.compile(loss='categorical_crossentropy',
                  optimizer=sgd,
                  metrics=['accuracy','mean_absolute_error'])





    prediction_datagen = ImageDataGenerator()

    prediction_generator = prediction_datagen.flow_from_directory(
        train_data_dir,
        target_size=(img_width, img_height),
        batch_size=50,
        class_mode='categorical',
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )

    img = load_img(imagePath,False, (150, 150))
    x = img_to_array(img).reshape((1,150,150,3))
    #prediction = loaded_model.predict_classes(x,batch_size=1, verbose=1)
    print("---------predict----------")
    p = seq.predict(x)
    print(p)

    print("---------predict Classes---------1")
    pe = seq.predict_classes(x)
    print(pe)


    print("---------predict Classes---------2")
    print(pe[:2])

    print("---------predict Generator---------")
    predictionR = seq.predict_generator(prediction_generator, 1)
    print(predictionR)
    print("---------evaluate generator---------")
    evR = seq.evaluate_generator(prediction_generator, 1)
    print(evR)


#If you have a multi-class classification, I'm sure you must have used a softmax activation in the end (as your output).
#The 'predictions' you see should be the softmax scores of the input. You can use model.predict_classes method, or use np.argmax(predictions) to get your class index/id.
#
#http://stackoverflow.com/questions/42461406/loading-a-pre-trained-keras-model-and-predicting
#http://stackoverflow.com/questions/42758394/how-to-expain-the-output-of-multi-class-classification
#https://groups.google.com/forum/#!searchin/keras-users/classes|sort:relevance/keras-users/HVfK7IURwOY/vVksw33gCQAJ
#http://stackoverflow.com/questions/38192354/obtaining-a-prediction-in-keras
#http://stackoverflow.com/questions/38000336/how-to-get-labels-ids-in-keras-when-training-on-multiple-classes
#
#

    y_classes = np_utils.to_categorical(p, 2)
    y_classes_moar = np_utils.to_categorical(predictionR, 2)
    print("-------to_Categorical----------")
    print(p)
    print(".-------classes-------")
    print(y_classes)
    print(y_classes_moar)

        
        

    #rint('predicting...')
    #
    #predictionR = loaded_model.predict_generator(prediction_generator, 1)
    #print(predictionR)
    #evR = loaded_model.evaluate_generator(prediction_generator, 1)
    #print(evR)

    print('finished')

runModel(catchedVars['filepath'],catchedVars['imgPath'])


##http://machinelearningmastery.com/grid-search-hyperparameters-deep-learning-models-python-keras/
#stackoverflow.com/questions/4171638/keras-output-of-model-predict-proba/41729210#41729210