/*EMMANUELLA EYO 11291003 EEE917*/

import random as rand

def read_data(filename):
    """ returns a list of data points read from a file name.
    Each data point is stored in a dictionary with fields
    "features" and "output". For mathematical convenience,
    a dummy feature with value 1 is added to all data points 
    to be the 'feature' for w0 (the bias).  Data is assumed to be integers
    
    :params:
    filename: name of the data file to read
    
    returns: a list of records """
    f = open(filename, "r")
    data = []
    for line in f:
        line = line.rstrip().split()
        line = [int(x) for x in line]
        point = {}
        point["features"] = tuple([1] + line[0:-1])
        point["output"] = line[-1]
        data.append(point)    
    
    f.close()
    return data
    
    
def partition_data(data, k):
    """
    Partitions a set of data into training and test sets.

    Returns a dictionary-of-records. The keys of the dictionary are the index of the test set.
    Each record has two fields: "train" and "test".

    :param data: A list of tuples, where each tuple is a data point.
    :param k: An integer representing the number of folds for cross-validation.

    :return: A dictionary of train/test set pairs.
    """

    n = len(data)
    rand.shuffle(data)

    test_size = n // k
    remainder = n % k
    start = 0

    result = {}

    for i in range(k):
        end = start + test_size + (1 if i < remainder else 0)
        test = data[start:end]
        train = data[:start] + data[end:]
        result[i] = {"train": train, "test": test}
        start = end

    return result
