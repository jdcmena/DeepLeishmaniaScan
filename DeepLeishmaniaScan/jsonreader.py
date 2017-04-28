import json as simplejson

#with open('test.json') as data_file:
#    data = simplejson.load(data_file)
#    print(data["name"])
#    print(data["place"])
#    print(data["number"])



def readFileHip(file):
    array =[]
    with open(file) as data:
        dfile = simplejson.load(data)
        array.append(dfile["id"]) #0
        array.append(dfile["generaciones"]) #1
        array.append(dfile["imagenesPorGeneracion"]) #2
        array.append(dfile["tasaAprendizaje"]) #3
        array.append(dfile["momentum"]) #4
        array.append(dfile["nesterov"]) #5
        
    return array