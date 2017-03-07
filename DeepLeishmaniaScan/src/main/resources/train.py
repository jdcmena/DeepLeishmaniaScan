from __future__ import division # /: float div, //: integer div
from PIL import Image
import h5py
import numpy as np
import glob
import time
import pydot
import jsonreader
import matplotlib.pyplot as plt
import json as simplejson
from keras.models import Sequential, model_from_json, Model
from keras.layers import Convolution2D, MaxPooling2D, GlobalAveragePooling2D, Activation, Dropout, Flatten, Dense, Reshape
from keras.layers.advanced_activations import PReLU
from keras.optimizers import SGD, RMSprop
from keras import backend as K
from keras.preprocessing.image import ImageDataGenerator, array_to_img, img_to_array, load_img
from keras.applications.inception_v3 import InceptionV3
from keras.utils.visualize_util import plot

##runConfigJson has hyperparameters
## modelIdJson has model's file routes, .h5 and arch.json paths
##dataParentDirString has dataset route and validation folders

def runModel(runConfigJson):
    
    hiperparameters = jsonreader.readFileHip(runConfigJson) #TODO check array values
    
    modelPath = hiperparameters[0]
    nb_epoch_var = hiperparameters[1]
    samples_per_epoch_var = hiperparameters[2]
    lrate = hiperparameters[3]
    momentum_var = hiperparameters[4]
    #decay_lR_var = hiperparameters[4]
    nesterov_var = hiperparameters[5]
    batch_size_var = 30
    img_width = 150
    img_height = 150
    nb_validation_samples = samples_per_epoch_var/4

    train_data_dir='conjuntoDeDatos/train'##'data/train'
    validation_data_dir='conjuntoDeDatos/validation'##'data/validation'

    ##prediction_data_dir=dataParentDirString+'/prediction'## data/prediction' #
    
    json_file = open('inceptionV3.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    inceptionModel = model_from_json(loaded_model_json)
    
    # load weights into base model
    inceptionModel.load_weights("inceptionV3.h5")
    
    
    
    ###adding more layers
    
    x = inceptionModel.output
    x = GlobalAveragePooling2D()(x)
    x = Dense(2048, activation='relu')(x)
    x = Dropout(0.8)(x)
    x = Dense(1024, activation='relu')(x)
    predict = Dense(1, activation='sigmoid')(x)
    
    loaded_model = Model(input=inceptionModel.input, output=predict)
    
    for layer in loaded_model.layers[:172]:
        layer.trainable = False
        
    for layer in loaded_model.layers[172:]:
        layer.trainable = True
    
    
    ##########---------calculated decay better
    
    decayRC = lrate/nb_epoch_var
    sgd = SGD(lr=lrate, momentum=momentum_var, decay=decayRC, nesterov=nesterov_var)
    loaded_model.compile(loss='binary_crossentropy',
                  optimizer=sgd,
                  metrics=['accuracy','mean_absolute_error'])
    
    print('compilation successful')
    train_datagen = ImageDataGenerator(
            horizontal_flip=True,
            vertical_flip=True,
            rotation_range=360
    )
    
    #test_datagen = ImageDataGenerator()
    
    eval_generator = train_datagen.flow_from_directory(
        train_data_dir,
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='binary',
        shuffle=True
    )
    
    history = loaded_model.fit_generator(
        eval_generator,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        #validation_data = validation_,
        nb_val_samples=nb_validation_samples,
        verbose=1,
        ##callbacks=callbacks_list
        )
    
    
    print("Saving model...")
    model_json = loaded_model.to_json()
    with open(modelPath+"/"+modelPath+".json", "w") as json_file:
        json_file.write(simplejson.dumps(simplejson.loads(model_json), indent=4))
    
    loaded_model.save_weights(modelPath+"/"+modelPath+'.h5') # save weights
    
    print(modelPath+ " saved successfuly")
    
    
    #validation_generator = test_datagen.flow_from_directory(
    #    validation_data_dir,
    #    target_size=(img_width, img_height),
    #    batch_size=batch_size_var,
    #    class_mode='binary',
    #    shuffle=True
    #)
    
    #prediction_generator = test_datagen.flow_from_directory(
    #    prediction_data_dir,
    #    target_size=(img_width, img_height),
    #    batch_size=1,
    #    class_mode='binary'
    #    )
    
    
    
    evaluation = loaded_model.evaluate_generator(eval_generator, val_samples=50,max_q_size=10, nb_worker=1, pickle_safe=False)
    print("Accuracy: %.2f%%" % (evaluation[1]*100))
    
    #pred = loaded_model.predict_generator(eval_generator, val_samples=50,max_q_size=10, nb_worker=1, pickle_safe=False)
    #for element in pred:
    #    print(element)
    
    
    #rint('predicting...')
    #
    #predictionR = loaded_model.predict_generator(prediction_generator, 1)
    #print(predictionR)
    #evR = loaded_model.evaluate_generator(prediction_generator, 1)
    #print(evR)
    
    print('finished')