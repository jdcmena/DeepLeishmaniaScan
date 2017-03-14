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
from keras.utils.visualize_util import plot

parser = argparse.ArgumentParser(description='classification script')
parser.add_argument('filepath')
parser.add_argument('imgPath')
catchedVars = vars(parser.parse_args())

def runModel(runConfigJson, imagePath):

    hiperparameters = jsonreader.readFileHip(runConfigJson) #TODO check array values
    modelPath = hiperparameters[0]

    print('loading model architecture...')
    json_file = open("models/"+str(modelPath)+"/"+str(modelPath)+"-arch.json", "r")
    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = model_from_json(loaded_model_json)
    print('loading model weights...')
    loaded_model.load_weights("models/"+str(modelPath)+"/"+str(modelPath)+'.h5')

    img = load_img(imagePath,False, (150, 150))
    x = img_to_array(img).reshape((1,150,150,3))
    prediction = loaded_model.predict(x,batch_size=1, verbose=1)
    print(prediction)

        
        

    #rint('predicting...')
    #
    #predictionR = loaded_model.predict_generator(prediction_generator, 1)
    #print(predictionR)
    #evR = loaded_model.evaluate_generator(prediction_generator, 1)
    #print(evR)

    print('finished')

runModel(catchedVars['filepath'],catchedVars['imgPath'])