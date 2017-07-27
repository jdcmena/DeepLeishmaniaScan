# Manual de proceso paso a paso del proceso de entrenamiento y clasificación

[![Tensorflow](http://blog.christianperone.com/wp-content/uploads/2016/08/tensorlogo.png)](https://www.tensorflow.org/)

Este manual cubrirá los pasos para ejecutar los comandos para realizar el proceso de entrenamiento y clasificación de modelos.

# Precondiciones:
- Sistema operativo **Linux**, **Ubuntu 16.04 LTS**. No se garantiza que este software, después de haber seguido todos los pasos, pueda funcionar correctamente en una versión diferente del SO anterior.
- El computador debe tener instalado **Python 2.7.2 nativo** (no con anaconda).
- Es recomendable que el computador tenga una GPU de Nvidia para un procesamiento más rapido de los datos. Si está disponible, se debe instalar  **[Tensorflow para GPU](https://www.tensorflow.org/install/install_linux#InstallingNativePip)** (seleccionar de ***Installing with native pip*** la opción que tenga ***GPU Support***) y las librerias de **[CUDA ToolKit 7.0 o mayor](https://developer.nvidia.com/cuda-downloads)** y **[CuDNN](https://developer.nvidia.com/cudnn)**, en ese orden.

# Estructura de carpetas
La carpeta del proyecto llamado DeepLeishmaniaScan contiene diversos archivos y carpetas que son importantes para este manual:
- /models: contiene las configuraciones de inicio y archivos de modelos
- /datasource: donde se encuentran las imagenes de entrada
- /fold[#]: subconjuntos de datasource (a partir del algoritmo de K-Fold)
- /fold-test[#] subconjunto de imagenes de prueba.
- train.py: script de entrenamiento.
- classify.py: script de clasificación.
- jsonreader.py: subrutina para leer archivos json.
- InceptionV3_1.json: arquitectura InceptionV3 base.
- inceptionV3_1.h5: pesos base de InceptionV3.

Dentro de **/models** se almacenarán otras carpetas nombradas con números que sirven de identificación de todos los modelos entrenados en tensorflow. Dichas carpetas contienen los siguientes archivos:
- [id-de-modelo].json: archivo usado para ser detectado por la aplicación de Java
- [id-de-modelo]-arch.json: arquitectura del modelo modificado (diferente a InceptionV3_1.json)
- [id-de-modelo].h5: pesos del modelo modificado
- runconfig.json: archivo que tiene los valores de los hiperparámetros usados para el modelo en específico.

Los hiperparámetros configurables en el archivo runconfig.json son:
- Número de épocas
- Tasa de aprendizaje
- Momentum
- Técnica de nesterov (***True o False***)
- Número de imágenes por batch

Para **aplicar** el cambio de alguno de los anteriores hiperparámetros es necesario **iniciar otra sesión de entrenamiento**.

# Ejecución del algoritmo de entrenamiento

- Para iniciar el entrenamiento primero se debe abrir la terminal y dirigirse a la carpeta que contiene a todas las carpetas anteriomente mencionadas con el comando **cd**.
```sh
$ cd /../DeepLeishmaniaScan/
```
- Una vez estando en la carpeta, se debe ejecutar la siguiente linea:
```sh
$ python train.py /models/[id de modelo disponible]/runconfig.json
```
El proceso se demorará dependiendo del número de épocas definido en dicho archivo. Al final del proceso se generarán ( o se sobre-esciribirán si ya existen) 3 archivos:
- **[id_de_modelo]-arch.json** tiene la arquitectura del modelo creado.
- **[id_de_modelo].h5** contiene los valores de cada uno de los pesos de la red.
- **output.txt** que contendrá varios indicadores, tales como: sensibilidad, especificidad, accuracy, precision, tiempo de ejecución, entre otros.

# Ejecución del algoritmo de clasificación
Sobre la misma carpeta, ejecutar la siguiente línea:
```sh
python classify.py /models/[id de modelo disponible]/runconfig.json [../../imagen.jpg|png|jpeg]
```
A diferencia del comando de entrenamiento, este requiere un parámetro adicional que es la imágen en cuestión que se quiere clasificar. Si no están los dos parámetros, el comando de clasificación no funcionará.
