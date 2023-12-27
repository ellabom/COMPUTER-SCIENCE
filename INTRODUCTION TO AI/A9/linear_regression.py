/*Emmanuella Eyo 11291003 eee917*/
def predict(point, weights):
    """ predicts the output value for the given point, using
    the given set of weights
    
    :params:
    point: record with keys "features" and "output"
    weights: list of weights for linear regression, one
    for each feature
    
    returns: integer. the predicted output value for the point """
    total = 0
    for i in range(len(weights)):
        total += weights[i] * point["features"][i]
        
    return total


def train(data, numsteps, alpha):
    """ trains a list of weights for linear regression
    
    :params:
    data: list of data points.  Each point is a record with
    fields "features" and "output"
    numsteps: integer. Number of training steps to use
    alpha: float. learning rate for the regression update rule
    
    returns: a list of weights, one for each feature
    """
    # initialize the weights to arbitrary values
    firstpoint = data[0]
    numfeatures = len(firstpoint["features"])
    w = [0] * numfeatures


    for point in data:
        yPrediction = predict(point, w)
        error = point['output'] - yPrediction

        for n in range(numfeatures):
            w[n] = w[n] + (alpha * point['features'][n] * error)

        
    #TODO: train the weights here
    
    
    return w
    
    
def total_error(data, weights):
    """ computes and returns the total mean squared error
    for all points in the given data set
    
    :params:
    data: list of data points, each point is a record with keys "features" and "output"
    weights: list of weights for linear regression, one weight
    per feature in the data
    
    return: float. the total mean squared error """
    total = 0
    for point in data:
        prediction = predict(point, weights)
        # a bit icky to use int, but otherwise the errors can
        # get too big for a Python float
        total += int((point["output"] - prediction)**2)
        
    total = total//len(data)
    return total
    
    
