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
import matplotlib.pyplot as plt
import json as simplejson
from keras.models import Sequential, model_from_json, Model
from keras.layers import Convolution2D, MaxPooling2D, GlobalAveragePooling2D, Activation, Dropout, Flatten, Dense, Reshape
from keras.layers.advanced_activations import PReLU
from keras.optimizers import SGD, RMSprop
from keras import backend as K
from keras.preprocessing.image import ImageDataGenerator, array_to_img, img_to_array, load_img
from keras.applications.inception_v3 import InceptionV3
from keras.utils import np_utils

#from keras.utils.visualize_util import plot

##runConfigJson has hyperparameters
## modelIdJson has model's file routes, .h5 and arch.json paths
##dataParentDirString has dataset route and validation folders

parser = argparse.ArgumentParser(description='training script')
parser.add_argument('filepath')
catchedVars = vars(parser.parse_args())



def runModel(runConfigJson):
    
    hiperparameters = jsonreader.readFileHip(runConfigJson) #TODO check array values
    
    modelPath = hiperparameters[0]
    nb_epoch_var = hiperparameters[1]
    samples_per_epoch_var = hiperparameters[2]
    lrate = hiperparameters[3]
    momentum_var = hiperparameters[4]
    #decay_lR_var = hiperparameters[4]
    nesterov_var = hiperparameters[5]
    if samples_per_epoch_var>= 100:
        batch_size_var = int(round((samples_per_epoch_var/10),0))
    else:
        batch_size_var = int(round((samples_per_epoch_var/5),0))

    img_width = 150
    img_height = 150
    nb_validation_samples = int(round((batch_size_var/2),0))

    ##train_data_dir='conjuntoDeDatos'##'data/train'
    ##validation_data_dir='conjuntoDeDatos'##'data/validation'

    train_data_dir=['fold1','fold2','fold3','fold4','fold5']
    validation_data_dir=['fold-test1','fold-test2','fold-test3','fold-test4','fold-test5']

    
    #loaded_model.save_weights("inceptionV3_1.h5")
    ##prediction_data_dir=dataParentDirString+'/prediction'## data/prediction' #
    
    json_file = open('inceptionV3_1.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    inceptionModel = model_from_json(loaded_model_json)
    
    # load weights into base model
    inceptionModel.load_weights("InceptionV3_1.h5")
    
    
    
    ###adding more layers
    
    x = inceptionModel.output
    x = GlobalAveragePooling2D()(x)
    x = Dense(2048, activation='relu')(x)
    x = Dropout(0.9)(x)
    x = Dense(1024, activation='relu')(x)
    predict = Dense(2, activation='softmax')(x)
    
    loaded_model = Model(input=inceptionModel.input, output=predict)
    
    for layer in loaded_model.layers[:150]:
        layer.trainable = False
        
    for layer in loaded_model.layers[150:]:
        layer.trainable = True
    
    
    ##########---------calculated decay better
    
    decayRC = lrate/nb_epoch_var
    sgd = SGD(lr=lrate, momentum=momentum_var, decay=decayRC, nesterov=nesterov_var)
    loaded_model.compile(loss='categorical_crossentropy',
                  optimizer=sgd,
                  metrics=['accuracy','mean_absolute_error'])
    
    print('compilation successful')

    train_datagen = ImageDataGenerator(
        horizontal_flip=True,
        vertical_flip=True,
        rotation_range=360
    )
    
    test_datagen = ImageDataGenerator()

    ##FOLDS

    print("fold1")
    train_generator_1 = train_datagen.flow_from_directory(
        train_data_dir[0],
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )
    print("fold2")
    train_generator_2 = train_datagen.flow_from_directory(
        train_data_dir[1],
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )
    print("fold3")
    train_generator_3 = train_datagen.flow_from_directory(
        train_data_dir[2],
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )
    print("fold4")
    train_generator_4 = train_datagen.flow_from_directory(
        train_data_dir[3],
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )
    print("fold5")
    train_generator_5 = train_datagen.flow_from_directory(
        train_data_dir[4],
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )
    
    #class_dictionary = train_generator.class_indices
    #print(class_dictionary)

    print("fold-test1")
    validation_generator_1 = test_datagen.flow_from_directory(
        validation_data_dir[0],
        target_size=(img_width, img_height),
        batch_size=nb_validation_samples,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )
    print("fold-test2")
    validation_generator_2 = test_datagen.flow_from_directory(
        validation_data_dir[1],
        target_size=(img_width, img_height),
        batch_size=nb_validation_samples,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )
    print("fold-test3")
    validation_generator_3 = test_datagen.flow_from_directory(
        validation_data_dir[2],
        target_size=(img_width, img_height),
        batch_size=nb_validation_samples,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )
    print("fold-test4")
    validation_generator_4 = test_datagen.flow_from_directory(
        validation_data_dir[3],
        target_size=(img_width, img_height),
        batch_size=nb_validation_samples,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )
    print("fold-test5")
    validation_generator_5 = test_datagen.flow_from_directory(
        validation_data_dir[4],
        target_size=(img_width, img_height),
        batch_size=nb_validation_samples,
        class_mode='categorical',
        shuffle=True,
        classes=['cutaneousLeishmaniasis','ISICArchive']
    )


    print("initializing fit...")

    accuracy_sets = [0.0,0.0,0.0,0.0,0.0]
    
    ########Fold 1
    temp_1 = loaded_model.fit_generator(
        train_generator_1,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        validation_data = validation_generator_1,
        nb_val_samples=nb_validation_samples,
        verbose=1
    )
    
    evaluation = loaded_model.evaluate_generator(validation_generator_1, val_samples=100,max_q_size=10, nb_worker=4, pickle_safe=True)
    accuracy_sets[0]='{0:.3g}'.format(evaluation[1]*100)
    print("Accuracy fold 1: %.2f%%" % (evaluation[1]*100))
    
    ########Fold 2
    temp_2 = loaded_model.fit_generator(
        train_generator_2,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        validation_data = validation_generator_2,
        nb_val_samples=nb_validation_samples,
        verbose=1
    )
    
    evaluation = loaded_model.evaluate_generator(validation_generator_2, val_samples=100,max_q_size=10, nb_worker=4, pickle_safe=True)
    accuracy_sets[1]='{0:.3g}'.format(evaluation[1]*100)
    print("Accuracy fold 2: %.2f%%" % (evaluation[1]*100))
    
    ########Fold 3
    temp_3 = loaded_model.fit_generator(
        train_generator_3,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        validation_data = validation_generator_3,
        nb_val_samples=nb_validation_samples,
        verbose=1
    )
    
    evaluation = loaded_model.evaluate_generator(validation_generator_3, val_samples=100,max_q_size=10, nb_worker=4, pickle_safe=True)
    accuracy_sets[2]='{0:.3g}'.format(evaluation[1]*100)
    print("Accuracy fold 3: %.2f%%" % (evaluation[1]*100))
    
    ########Fold 4
    temp_4 = loaded_model.fit_generator(
        train_generator_4,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        validation_data = validation_generator_4,
        nb_val_samples=nb_validation_samples,
        verbose=1
    )
    
    evaluation = loaded_model.evaluate_generator(validation_generator_4, val_samples=100,max_q_size=10, nb_worker=4, pickle_safe=True)
    accuracy_sets[3]='{0:.3g}'.format(evaluation[1]*100)
    print("Accuracy fold 4: %.2f%%" % (evaluation[1]*100))
    
    ########Fold 5
    temp_5 = loaded_model.fit_generator(
        train_generator_5,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        validation_data = validation_generator_5,
        nb_val_samples=nb_validation_samples,
        verbose=1
    )
    
    evaluation = loaded_model.evaluate_generator(validation_generator_5, val_samples=100,max_q_size=10, nb_worker=4, pickle_safe=True)
    accuracy_sets[4]='{0:.3g}'.format(evaluation[1]*100)
    print("Accuracy fold 5: %.2f%%" % (evaluation[1]*100))
    var_total = 0.0
    for element in accuracy_sets:
        var_total = float(var_total)+float(element)



    gblAcc = var_total/5.0

    print("Global Accuracy:"+str(gblAcc)+"%")

    #history = loaded_model.fit_generator(
    #    train_generator,
    #    nb_epoch = nb_epoch_var,
    #    samples_per_epoch = samples_per_epoch_var,
    #    validation_data = validation_generator,
    #    nb_val_samples=nb_validation_samples,
    #    verbose=1
    #)

    #print(loaded_model.summary())

    #eval_generator = train_datagen.flow_from_directory(
    #train_data_dir,
    #target_size=(img_width, img_height),
    #batch_size=batch_size_var,
    #class_mode='categorical',
    #shuffle=True
    #)

    #predict_gen = test_datagen.flow_from_directory(
    #train_data_dir,
    #target_size=(img_width, img_height),
    #batch_size=batch_size_var,
    #class_mode='categorical',
    #shuffle=True
    #)

    print("Saving model...")
    model_json = loaded_model.to_json()
    with open("models/"+str(modelPath)+"/"+str(modelPath)+"-arch.json", "w") as json_file:
        json_file.write(simplejson.dumps(simplejson.loads(model_json), indent=4))
    
    loaded_model.save_weights("models/"+str(modelPath)+"/"+str(modelPath)+'.h5') # save weights

    print(str(modelPath)+" saved successfuly")

    #evaluation = loaded_model.evaluate_generator(eval_generator, val_samples=100,max_q_size=10, nb_worker=4, pickle_safe=True)
    #print("Accuracy: %.2f%%" % (evaluation[1]*100))
    
    #pred = loaded_model.predict_generator(eval_generator, val_samples=2,max_q_size=10, nb_worker=1, pickle_safe=False)
    #for element in pred:
    #    print(element)
    
    
    #rint('predicting...')
    
    #predictionR = loaded_model.predict_generator(predict_gen, 1)
    #print(predictionR)

    #evR = loaded_model.evaluate_generator(predict_gen, 1)
    #print(evR)
    
    ##print('finished')

runModel(catchedVars['filepath'])