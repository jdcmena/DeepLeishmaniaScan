from __future__ import division # /: float div, //: integer div
from PIL import Image
import random
import os
import glob
import cv2
import math
import pickle
import datetime
import pandas as pd
import argparse
import h5py
import numpy as np
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
from keras.initializers import TruncatedNormal
from sklearn.metrics import confusion_matrix
from sklearn.metrics import classification_report


#from keras.utils.visualize_util import plot

##runConfigJson has hyperparameters
## modelIdJson has model's file routes, .h5 and arch.json paths
##dataParentDirString has dataset route and validation folders

parser = argparse.ArgumentParser(description='training script')
parser.add_argument('filepath')
catchedVars = vars(parser.parse_args())
#y_actual[0:100] = 0
img_width = 150
img_height = 150

np.set_printoptions(threshold=np.inf)

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

    nb_validation_samples = int(round((batch_size_var/2),0))

    ##train_data_dir='conjuntoDeDatos'##'data/train'
    ##validation_data_dir='conjuntoDeDatos'##'data/validation'

    #train_data_source='datasource'

    train_data_dir=['fold1','fold2','fold3','fold4','fold5']
    validation_data_dir=['fold-test1','fold-test2','fold-test3','fold-test4','fold-test5']

    ########use all images-override current config ##############

    samples_per_epoch_var = 5000
    batch_size_var = 50
    nb_validation_samples = 800

    ####################################

    
    #loaded_model.save_weights("inceptionV3_1.h5")
    ##prediction_data_dir=dataParentDirString+'/prediction'## data/prediction' #
    
    json_file = open('inceptionV3_1.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    inceptionModel = model_from_json(loaded_model_json)
    
    # load weights into base model
    inceptionModel.load_weights("InceptionV3_1.h5")

    #random

    #-------------
    #Seed
    init_seed = 957418
    random.seed(957418)

    #-------------

    trunc_rnd_1 = random.random()
    trunc_rnd_2 = random.random()
    trunc_rnd_3 = random.random()

    bias_rnd_1 = random.random()
    bias_rnd_2 = random.random()
    bias_rnd_3 = random.random()

    #kernel and bias initializers
    truncatedN_1= TruncatedNormal(mean=0.0, stddev=0.05, seed=trunc_rnd_1)
    truncatedN_2= TruncatedNormal(mean=0.0, stddev=0.05, seed=trunc_rnd_2)
    truncatedN_3= TruncatedNormal(mean=0.0, stddev=0.05, seed=trunc_rnd_3)

    biasInit_1= TruncatedNormal(mean=0.0, stddev=0.005, seed= bias_rnd_1)
    biasInit_2= TruncatedNormal(mean=0.0, stddev=0.005, seed= bias_rnd_2)
    biasInit_3= TruncatedNormal(mean=0.0, stddev=0.005, seed= bias_rnd_3)
    
    ##Before custom layers: 311 Layers
    x = inceptionModel.output
    x = GlobalAveragePooling2D()(x)
    x = Dense(2048, activation='tanh', kernel_initializer=truncatedN_1, bias_initializer=biasInit_1)(x)
    x = Dropout(0.9)(x)
    x = Dense(1024, activation='relu', kernel_initializer=truncatedN_2, bias_initializer=biasInit_2)(x)
    x = Dense(512, activation='relu', kernel_initializer=truncatedN_3, bias_initializer=biasInit_3)(x)
    predict = Dense(2, activation='softmax')(x)
    
    loaded_model = Model(input=inceptionModel.input, output=predict)

    ##After custom layers: 317 Layers

    for layer in loaded_model.layers[:311]:
        layer.trainable = False
        
    for layer in loaded_model.layers[311:]:
        layer.trainable = True
    
    
    ##########---------calculated decay better
    
    decayRC = lrate/nb_epoch_var
    sgd = SGD(lr=lrate, momentum=momentum_var, decay=decayRC, nesterov=nesterov_var)
    loaded_model.compile(loss='categorical_crossentropy',
                  optimizer=sgd,
                  metrics=['accuracy'])
    
    print('compilation successful')

    train_datagen = ImageDataGenerator(
        horizontal_flip=True,
        vertical_flip=True
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
        classes=['Leishmaniasis','Non_Leishmaniasis']
    )
    print("fold2")
    train_generator_2 = train_datagen.flow_from_directory(
        train_data_dir[1],
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='categorical',
        shuffle=True,
        classes=['Leishmaniasis','Non_Leishmaniasis']
    )
    print("fold3")
    train_generator_3 = train_datagen.flow_from_directory(
        train_data_dir[2],
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='categorical',
        shuffle=True,
        classes=['Leishmaniasis','Non_Leishmaniasis']
    )
    print("fold4")
    train_generator_4 = train_datagen.flow_from_directory(
        train_data_dir[3],
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='categorical',
        shuffle=True,
        classes=['Leishmaniasis','Non_Leishmaniasis']
    )
    print("fold5")
    train_generator_5 = train_datagen.flow_from_directory(
        train_data_dir[4],
        target_size=(img_width, img_height),
        batch_size=batch_size_var,
        class_mode='categorical',
        shuffle=True,
        classes=['Leishmaniasis','Non_Leishmaniasis']
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
        classes=['Leishmaniasis','Non_Leishmaniasis']
    )
    print("fold-test2")
    validation_generator_2 = test_datagen.flow_from_directory(
        validation_data_dir[1],
        target_size=(img_width, img_height),
        batch_size=nb_validation_samples,
        class_mode='categorical',
        shuffle=True,
        classes=['Leishmaniasis','Non_Leishmaniasis']
    )
    print("fold-test3")
    validation_generator_3 = test_datagen.flow_from_directory(
        validation_data_dir[2],
        target_size=(img_width, img_height),
        batch_size=nb_validation_samples,
        class_mode='categorical',
        shuffle=True,
        classes=['Leishmaniasis','Non_Leishmaniasis']
    )
    print("fold-test4")
    validation_generator_4 = test_datagen.flow_from_directory(
        validation_data_dir[3],
        target_size=(img_width, img_height),
        batch_size=nb_validation_samples,
        class_mode='categorical',
        shuffle=True,
        classes=['Leishmaniasis','Non_Leishmaniasis']
    )
    print("fold-test5")
    validation_generator_5 = test_datagen.flow_from_directory(
        validation_data_dir[4],
        target_size=(img_width, img_height),
        batch_size=nb_validation_samples,
        class_mode='categorical',
        shuffle=True,
        classes=['Leishmaniasis','Non_Leishmaniasis']
    )

    #####this generator will be used to create confusion matrix, after training finished
    #confusion_mat_gen = train_datagen.flow_from_directory(
    #    train_data_source,
    #    target_size=(img_width,img_height),
    #    batch_size=batch_size_var,
    #    class_mode='categorical',
    #    shuffle=True,
    #    classes=['Leishmaniasis','Non_Leishmaniasis']
    #   )


    print("initializing fit...")

    accuracy_sets = [0.0,0.0,0.0,0.0,0.0]
    precision_sets = [0.0,0.0,0.0,0.0,0.0]
    sensitivity_sets = [0.0,0.0,0.0,0.0,0.0]
    specificity_sets = [0.0,0.0,0.0,0.0,0.0]
    f1Score_sets = [0.0,0.0,0.0,0.0,0.0]
    kappa_sets = [0.0,0.0,0.0,0.0,0.0]
    
    ########Fold 1
    temp_1 = loaded_model.fit_generator(
        train_generator_1,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        verbose=1
    )

    predicted = []
    real = []
    counter = 0

    for X, y in validation_generator_1:

        real.extend(y.ravel())
        y_hat_batch = loaded_model.predict(X)
        predicted.extend(y_hat_batch.ravel())
        counter = counter +1

        if counter == 1:
            break

    # Convert to np.array and round predictions
    real = np.array(real)
    predicted = np.round(predicted)

    TP = 0
    FP = 0
    TN = 0
    FN = 0

    for i in range(len(predicted)): #True positive
        if real[i]==predicted[i]==0:
            TP += 1

    for i in range(len(predicted)): #False positive
        if predicted[i]==1 and predicted[i]!=real[i]:
            FP += 1

    for i in range(len(predicted)): #True negative
        if real[i]==predicted[i]==1:
            TN += 1

    for i in range(len(predicted)): #False negative
        if predicted[i]==1 and real[i]!=predicted[i]:
            FN += 1

    sRPositivo = TP+FN
    sRNegativo = FP+TN
    sPPositivo = TP+FP
    sPNegativo = FN+TN
    sTotal = TP+TN+FP+FN

    sensitivity=(TP/(TP+FN))*100
    specificity=(TN/(TN+FP))*100
    accuracy=((TP+TN)/(TP+FP+FN+TN))*100
    f1=((2*TP)/((2*TP)+FP+FN))*100
    precision=(TP/(TP+FP))*100
    AggChance= (((sRPositivo/sTotal)*(sPPositivo/sTotal))+((sRNegativo/sTotal)*(sPNegativo/sTotal)))
    kappa=(accuracy-AggChance)/(1-AggChance)

    accuracy_sets[0]=accuracy
    precision_sets[0]=precision
    sensitivity_sets[0]=sensitivity
    specificity_sets[0]=specificity
    f1Score_sets[0]=f1
    kappa_sets[0]=kappa

    #evaluation = loaded_model.evaluate_generator(validation_generator_1, val_samples=nb_validation_samples,max_q_size=10, nb_worker=4, pickle_safe=True)
    #accuracy_sets[0]='{0:.3g}'.format(evaluation[1]*100)
    #print("Accuracy fold 1: %.2f%%" % (evaluation[1]*100))


    ########Fold 2
    temp_2 = loaded_model.fit_generator(
        train_generator_2,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        verbose=1
    )


    predicted = []
    real = []
    counter = 0

    for X, y in validation_generator_2:

        real.extend(y.ravel())
        y_hat_batch = loaded_model.predict(X)
        predicted.extend(y_hat_batch.ravel())
        counter = counter +1

        if counter == 1:
            break

    # Convert to np.array and round predictions
    real = np.array(real)
    predicted = np.round(predicted)

    TP = 0
    FP = 0
    TN = 0
    FN = 0

    for i in range(len(predicted)): #True positive
        if real[i]==predicted[i]==0:
            TP += 1

    for i in range(len(predicted)): #False positive
        if predicted[i]==1 and predicted[i]!=real[i]:
            FP += 1

    for i in range(len(predicted)): #True negative
        if real[i]==predicted[i]==1:
            TN += 1

    for i in range(len(predicted)): #False negative
        if predicted[i]==1 and real[i]!=predicted[i]:
            FN += 1

    sRPositivo = TP+FN
    sRNegativo = FP+TN
    sPPositivo = TP+FP
    sPNegativo = FN+TN
    sTotal = TP+TN+FP+FN

    sensitivity=(TP/(TP+FN))*100
    specificity=(TN/(TN+FP))*100
    accuracy=((TP+TN)/(TP+FP+FN+TN))*100
    f1=((2*TP)/((2*TP)+FP+FN))*100
    precision=(TP/(TP+FP))*100
    AggChance= (((sRPositivo/sTotal)*(sPPositivo/sTotal))+((sRNegativo/sTotal)*(sPNegativo/sTotal)))
    kappa=(accuracy-AggChance)/(1-AggChance)

    accuracy_sets[1]=accuracy
    precision_sets[1]=precision
    sensitivity_sets[1]=sensitivity
    specificity_sets[1]=specificity
    f1Score_sets[1]=f1
    kappa_sets[1]=kappa

    #evaluation = loaded_model.evaluate_generator(validation_generator_2, val_samples=nb_validation_samples,max_q_size=10, nb_worker=4, pickle_safe=True)
    #accuracy_sets[1]='{0:.3g}'.format(evaluation[1]*100)
    #print("Accuracy fold 2: %.2f%%" % (evaluation[1]*100))
    
    ########Fold 3
    temp_3 = loaded_model.fit_generator(
        train_generator_3,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        verbose=1
    )
    

    predicted = []
    real = []
    counter = 0

    for X, y in validation_generator_3:

        real.extend(y.ravel())
        y_hat_batch = loaded_model.predict(X)
        predicted.extend(y_hat_batch.ravel())
        counter = counter +1

        if counter == 1:
            break

    # Convert to np.array and round predictions
    real = np.array(real)
    predicted = np.round(predicted)

    TP = 0
    FP = 0
    TN = 0
    FN = 0

    for i in range(len(predicted)): #True positive
        if real[i]==predicted[i]==0:
            TP += 1

    for i in range(len(predicted)): #False positive
        if predicted[i]==1 and predicted[i]!=real[i]:
            FP += 1

    for i in range(len(predicted)): #True negative
        if real[i]==predicted[i]==1:
            TN += 1

    for i in range(len(predicted)): #False negative
        if predicted[i]==1 and real[i]!=predicted[i]:
            FN += 1

    sRPositivo = TP+FN
    sRNegativo = FP+TN
    sPPositivo = TP+FP
    sPNegativo = FN+TN
    sTotal = TP+TN+FP+FN

    sensitivity=(TP/(TP+FN))*100
    specificity=(TN/(TN+FP))*100
    accuracy=((TP+TN)/(TP+FP+FN+TN))*100
    f1=((2*TP)/((2*TP)+FP+FN))*100
    precision=(TP/(TP+FP))*100
    AggChance= (((sRPositivo/sTotal)*(sPPositivo/sTotal))+((sRNegativo/sTotal)*(sPNegativo/sTotal)))
    kappa=(accuracy-AggChance)/(1-AggChance)

    accuracy_sets[2]=accuracy
    precision_sets[2]=precision
    sensitivity_sets[2]=sensitivity
    specificity_sets[2]=specificity
    f1Score_sets[2]=f1
    kappa_sets[2]=kappa



    #evaluation = loaded_model.evaluate_generator(validation_generator_3, val_samples=nb_validation_samples,max_q_size=10, nb_worker=4, pickle_safe=True)
    #accuracy_sets[2]='{0:.3g}'.format(evaluation[1]*100)
    #print("Accuracy fold 3: %.2f%%" % (evaluation[1]*100))
    
    ########Fold 4
    temp_4 = loaded_model.fit_generator(
        train_generator_4,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        verbose=1
    )
    

    predicted = []
    real = []
    counter = 0

    for X, y in validation_generator_4:

        real.extend(y.ravel())
        y_hat_batch = loaded_model.predict(X)
        predicted.extend(y_hat_batch.ravel())
        counter = counter +1

        if counter == 1:
            break

    # Convert to np.array and round predictions
    real = np.array(real)
    predicted = np.round(predicted)

    TP = 0
    FP = 0
    TN = 0
    FN = 0

    for i in range(len(predicted)): #True positive
        if real[i]==predicted[i]==0:
            TP += 1

    for i in range(len(predicted)): #False positive
        if predicted[i]==1 and predicted[i]!=real[i]:
            FP += 1

    for i in range(len(predicted)): #True negative
        if real[i]==predicted[i]==1:
            TN += 1

    for i in range(len(predicted)): #False negative
        if predicted[i]==1 and real[i]!=predicted[i]:
            FN += 1

    sRPositivo = TP+FN
    sRNegativo = FP+TN
    sPPositivo = TP+FP
    sPNegativo = FN+TN
    sTotal = TP+TN+FP+FN

    sensitivity=(TP/(TP+FN))*100
    specificity=(TN/(TN+FP))*100
    accuracy=((TP+TN)/(TP+FP+FN+TN))*100
    f1=((2*TP)/((2*TP)+FP+FN))*100
    precision=(TP/(TP+FP))*100
    AggChance= (((sRPositivo/sTotal)*(sPPositivo/sTotal))+((sRNegativo/sTotal)*(sPNegativo/sTotal)))
    kappa=(accuracy-AggChance)/(1-AggChance)

    accuracy_sets[3]=accuracy
    precision_sets[3]=precision
    sensitivity_sets[3]=sensitivity
    specificity_sets[3]=specificity
    f1Score_sets[3]=f1
    kappa_sets[3]=kappa



    #evaluation = loaded_model.evaluate_generator(validation_generator_4, val_samples=nb_validation_samples,max_q_size=10, nb_worker=4, pickle_safe=True)
    #accuracy_sets[3]='{0:.3g}'.format(evaluation[1]*100)
    #print("Accuracy fold 4: %.2f%%" % (evaluation[1]*100))
    
    ########Fold 5
    temp_5 = loaded_model.fit_generator(
        train_generator_5,
        nb_epoch = nb_epoch_var,
        samples_per_epoch = samples_per_epoch_var,
        verbose=1
    )
    
    predicted = []
    real = []
    counter = 0

    for X, y in validation_generator_5:

        real.extend(y.ravel())
        y_hat_batch = loaded_model.predict(X)
        predicted.extend(y_hat_batch.ravel())
        counter = counter +1

        if counter == 1:
            break

    # Convert to np.array and round predictions
    real = np.array(real)
    predicted = np.round(predicted)

    TP = 0
    FP = 0
    TN = 0
    FN = 0

    for i in range(len(predicted)): #True positive
        if real[i]==predicted[i]==0:
            TP += 1

    for i in range(len(predicted)): #False positive
        if predicted[i]==1 and predicted[i]!=real[i]:
            FP += 1

    for i in range(len(predicted)): #True negative
        if real[i]==predicted[i]==1:
            TN += 1

    for i in range(len(predicted)): #False negative
        if predicted[i]==1 and real[i]!=predicted[i]:
            FN += 1

    sRPositivo = TP+FN
    sRNegativo = FP+TN
    sPPositivo = TP+FP
    sPNegativo = FN+TN
    sTotal = TP+TN+FP+FN

    sensitivity=(TP/(TP+FN))*100
    specificity=(TN/(TN+FP))*100
    accuracy=((TP+TN)/(TP+FP+FN+TN))*100
    f1=((2*TP)/((2*TP)+FP+FN))*100
    precision=(TP/(TP+FP))*100
    AggChance= (((sRPositivo/sTotal)*(sPPositivo/sTotal))+((sRNegativo/sTotal)*(sPNegativo/sTotal)))
    kappa=(accuracy-AggChance)/(1-AggChance)

    accuracy_sets[4]=accuracy
    precision_sets[4]=precision
    sensitivity_sets[4]=sensitivity
    specificity_sets[4]=specificity
    f1Score_sets[4]=f1
    kappa_sets[4]=kappa

    #evaluation = loaded_model.evaluate_generator(validation_generator_5, val_samples=nb_validation_samples,max_q_size=10, nb_worker=4, pickle_safe=True)
    #accuracy_sets[4]='{0:.3g}'.format(evaluation[1]*100)
    #print("Accuracy fold 5: %.2f%%" % (evaluation[1]*100))
    #var_total = 0.0
    #for element in accuracy_sets:
    #    var_total = float(var_total)+float(element)#

    #gblAcc = var_total/5.0
        #This output is read from Java program as line.split(" ")[1]   #####
    #print("Global Accuracy: "+str(gblAcc)+" %")

    #prediction = loaded_model.predict_generator(validation_generator_1, val_samples=100)

#https://groups.google.com/forum/#!searchin/keras-users/confusion$20matrix|sort:relevance/keras-users/W-AdTqL7oNE/wojuYRFFBQAJ
    

    acc_mean = sum(accuracy_sets)/len(accuracy_sets)
    prec_mean = sum(precision_sets)/len(precision_sets)
    sens_mean = sum(sensitivity_sets)/len(sensitivity_sets)
    spec_mean = sum(specificity_sets)/len(specificity_sets)
    f1_mean = sum(f1Score_sets)/len(f1Score_sets)



    #print(cf)

    #print(classification_report(real, predicted, target_names=['Leishmaniasis','Non-Leishmaniasis']))

    file = open("output.txt","w")
    file.write("Sensitivity "+str(sens_mean)+"\n")
    file.write("Accuracy "+str(acc_mean)+"\n")
    file.write("Specificity "+str(spec_mean)+"\n")
    file.write("Precision "+str(prec_mean)+"\n")
    file.write("F1 Score "+str(f1_mean)+"\n")
    file.write("Kappa 1"+str(kappa_sets[0])+"\n")
    file.write("Kappa 2"+str(kappa_sets[1])+"\n")
    file.write("Kappa 3"+str(kappa_sets[2])+"\n")
    file.write("Kappa 4"+str(kappa_sets[3])+"\n")
    file.write("Kappa 5"+str(kappa_sets[4])+"\n")
    file.write("Initial seed: "+str(init_seed)+"\n")
    file.write("generated weights by seed: \n")
    file.write("kernelInit_1:"+ str(trunc_rnd_1)+" \n")
    file.write("kernelInit_2:"+ str(trunc_rnd_2)+" \n")
    file.write("kernelInit_2:"+ str(trunc_rnd_3)+" \n")
    file.write("biasInit_1:"+ str(bias_rnd_1)+" \n")
    file.write("biasInit_2:"+ str(bias_rnd_2)+" \n")
    file.write("biasInit_2:"+ str(bias_rnd_3)+" \n")
    file.close()
    
    



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