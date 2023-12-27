import sys
import math
import data_manager as DM
import linear_regression as LR

if len(sys.argv) < 4:
    print("Usage: python run_regression.py <datafile> <numsteps> <alpha>")
    exit()
    
filename = sys.argv[1]
data = DM.read_data(filename)
numsteps = int(sys.argv[2])
alpha = float(sys.argv[3])

num_partitions = 2
partitions = DM.partition_data(data, 10)

total_error = 0

for s,dataset in partitions.items():
    train = dataset["train"]
    test = dataset["test"]
    weights = LR.train(train, numsteps, alpha)
    training_error = LR.total_error(train, weights)
    test_error = LR.total_error(test, weights)
    total_error += test_error
    print("------")
    print("Results for data set", s)
    print("Learned weights were:", weights)
    print("Error on TRAINING set:", training_error)
    print("Error on TEST set:", test_error)
    print("------")
    
print("******")
total_error = total_error/num_partitions
print("Total average test error: ", total_error)

