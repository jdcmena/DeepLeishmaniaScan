# Step by step process manual of training and classification process

[![Tensorflow](http://blog.christianperone.com/wp-content/uploads/2016/08/tensorlogo.png)](https://www.tensorflow.org/)

This manual will cover the steps to execute the commands to perform the training and classification process.

# Preconditions:
- **Linux**, **Ubuntu 16.04 LTS.** There is no guarantee that this software, after having followed all the steps, can work correctly in a different version of the previous OS.
- **Python 2.7.2 nativo**.
- It is recommended that the computer has an Nvidia GPU for faster processing. If available, install  **[Tensorflow for GPU](https://www.tensorflow.org/install/install_linux#InstallingNativePip)**, **[CUDA ToolKit 7.0 o greater](https://developer.nvidia.com/cuda-downloads)** and **[CuDNN](https://developer.nvidia.com/cudnn)**, en ese orden.

# Folder Structure
The project folder called DeepLeishmaniaScan contains several files and folders that are important:

- models: contains initial configurations and model files
- datasource: where the input images are found
- fold [#]: subsets of datasource (from the K-Fold algorithm)
- fold-test [#] subset of test images.
- train.py: training script.
- classify.py: classification script.
- jsonreader.py: subroutine to read json files.
- InceptionV3_1.json: InceptionV3 base architecture.
- inceptionV3_1.h5: base weights of InceptionV3.

Within **/ models**, other folders named with numbers serve as identification for all trained models. These folders contain the following files:
- [model-id] .json: file used to be read by Java application
- [model-id] -arch.json: architecture of modified model (different from InceptionV3_1.json)
- [model-id] .h5: weights of modified model
- runconfig.json: file that has the values of the hyperparameters used for a specific model.

The configurable hyperparameters in the runconfig.json file are:

- Number of times
- Learning rate
- Momentum
- Nesterov technique (True or False)
- Number of images per batch

To apply changes of any of these hyperparameterers you need to **start another training session**.

# Execution of training algorithm

- To start training you must first open the terminal and go to the folder containing all folders previously mentioned (using **cd**).

```sh
$ cd /../DeepLeishmaniaScan/
```
- Once inside the folder, execute the following command
```sh
$ python train.py /models/[id de modelo disponible]/runconfig.json
```
The process will take some time depending on the number of epochs defined in the previous configuration file. At the end of the process, 3 files will be generated (or overwritten if they already exist):
- **[modelID] -arch.json** new model architecture.
- **[modelID] .h5** contains new values of each of the weights in the trained network.
- **output.txt** containing several metrics, such as sensitivity, specificity, accuracy, precision, execution time, and others.

# Execution of classification script
In the same folder, execute:
```sh
python classify.py /models/[id de modelo disponible]/runconfig.json [../../imagen.jpg|png|jpeg]
```
This command requires an additional parameter, which is the image's location.
Output value will be the likelihood (%) of being infected with cutaneous leishmaniasis.
