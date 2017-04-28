from __future__ import division # /: float div, //: integer div
from PIL import Image
import h5py
import numpy as np
import glob
import time
import pydot
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

batch_size_var = 1
img_width = 100
img_height = 100
nb_epoch_var = 5 #10
samples_per_epoch_var = 200
nb_validation_samples = 5
lrate = 0.0001 #0.001

train_data_dir='data/train'
validation_data_dir='data/validation'
prediction_data_dir='data/prediction'

print('loading model architecture...')
json_file = open('model.json', 'r')
loaded_model_json = json_file.read()
json_file.close()
loaded_model = model_from_json(loaded_model_json)
print('loading model architecture... Done')

print('loading model weights...')
# load weights into new model
loaded_model.load_weights("modelWeight.h5")
print('loading model weights... Done')
decayR = lrate/nb_epoch_var
sgd = SGD(lr=lrate, momentum=0.4, decay=decayR, nesterov=False)
loaded_model.compile(loss='binary_crossentropy',
              optimizer=sgd,
              metrics=['accuracy','mean_absolute_error'])
              
print('compilation successful')
train_datagen = ImageDataGenerator(
        #rescale=1./255,
        #shear_range=0.1, #0.3
        #zoom_range=0.1  #0.3

)
    
test_datagen = ImageDataGenerator()

eval_generator = train_datagen.flow_from_directory(
    train_data_dir,
    target_size=(img_width, img_height),
    batch_size=batch_size_var,
    class_mode='binary',
    shuffle=True
)
    
#validation_generator = test_datagen.flow_from_directory(
#    validation_data_dir,
#    target_size=(img_width, img_height),
#    batch_size=batch_size_var,
#    class_mode='binary',
#    shuffle=True
#)

prediction_generator = test_datagen.flow_from_directory(
    prediction_data_dir,
    target_size=(img_width, img_height),
    batch_size=1,
    class_mode='binary'
    )



evaluation = loaded_model.evaluate_generator(eval_generator, val_samples=50,max_q_size=10, nb_worker=1, pickle_safe=False)
print("Accuracy: %.2f%%" % (evaluation[1]*100))

pred = loaded_model.predict_generator(eval_generator, val_samples=50,max_q_size=10, nb_worker=1, pickle_safe=False)
for element in pred:
    print(element)
    
    
    
    
    print('evaluation')
    evaluation = newModel.evaluate_generator(eval_generator, val_samples=50,max_q_size=5, nb_worker=1, pickle_safe=True)
    for item in evaluation:
        print(item)
        
    print("Accuracy: %.2f%%" % (evaluation[1]*100))
    
    pred = newModel.predict_generator(eval_generator, val_samples=batch_size_var,max_q_size=5, nb_worker=1, pickle_safe=True)
    for element in pred:
        print(element)
        
    
    print('prediction')
    img = load_img('data/prediction/1.jpg',False, (50, 50))
    x = img_to_array(img).reshape((1,50,50,3))
    img = load_img('data/prediction/2.jpg',False, (50, 50))
    y = img_to_array(img).reshape((1,50,50,3))
    #img = load_img('data/prediction/images(20).jpg',False, (100, 100))
    #z = img_to_array(img).reshape((1,100,100,3))
    #img = load_img('data/prediction/images(27).jpg',False, (100, 100))
    #w = img_to_array(img).reshape((1,100,100,3))
    prediction = newModel.predict(x,batch_size=1, verbose=1)
    print(prediction)
    prediction = newModel.predict(y,batch_size=1, verbose=1)
    print(prediction)
    #prediction = newModel.predict(z,batch_size=1, verbose=1)
    #print(prediction)
    #prediction = newModel.predict(w,batch_size=1, verbose=1)
    #print(prediction)
    
    

#rint('predicting...')
#
#predictionR = loaded_model.predict_generator(prediction_generator, 1)
#print(predictionR)
#evR = loaded_model.evaluate_generator(prediction_generator, 1)
#print(evR)

print('finished')