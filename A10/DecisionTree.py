#EMMANUELLA EYO 11291003 EEE917
#   DecisionTree.py
#
#   Basic implementation of a decision tree for binary
#   classification problems
#   Written by Jeff Long for CMPT 317, University of Saskatchewan


import math as math

class Decision_Treenode(object):

    def __init__(self):
        return
        
    def classify(self, sample):
        """ returns the label for the given sample """

        
class Label_Node(Decision_Treenode):

    def __init__(self, label):
        self.label = label
        
    def classify(self, sample):
        return self.label
        
    def __str__(self):
        if self.label != None:
            return "Label " + self.label
        else:
            return "Label None"
        
        
class Feature_Node(Decision_Treenode):

    def __init__(self, feature, values):
        """ returns a new feature node that splits on the given feature
        
        :params:
        feature: string. name of the categorical feature this node will split on
        values: list of all possible values for that feature
        
        returns: a new feature node with no children yet
        """
        self.feature = feature
        self.children = {}
        for v in values:
            self.children[v] = None
            
            
    def classify(self, sample):
        sample_value = sample[self.feature]
        child_node = self.children[sample_value]
        return child_node.classify(sample)
            

    def __str__(self):
        return "Feature " + self.feature
               
        
class Decision_Tree(object):

    def __init__(self):
        self.root = None
        self.outputname = ""
        
        
    def build(self, filename):
        """ builds a binary decision tree from data in the given file name
        file must be plain text with one column for each feature
        It is assumed the first column is just the sample ID and the
        last column is the label. columns are white-space separated, 
        and sample labels must all values 'yes' or 'no'
        
        :params:
        filename: string. name of the file to open
        """
        f = open(filename, 'r')
        features = f.readline()
        features = features.strip().split()
        
        # get rid of the sample_id
        features = features[1:]
        
        # get rid of the label name in the last column
        self.outputname = features[-1]
        features = features[:-1]
        
        # construct a list of all possible values for each feature,
        # so that we're not limited to binary features
        feature_vals = {}
        for feat in features:
            feature_vals[feat] = []
        
        data = []
        for line in f:
            line = line.strip().split()
            line = line[1:] # get rid of the sample_id
            s = {}
            s["label"] = line[-1]
            for i in range(len(features)):
                s[features[i]] = line[i]
                if line[i] not in feature_vals[features[i]]:
                    feature_vals[features[i]].append(line[i])
                
            data.append(s)
            
        
        f.close()
        
        # store the possible values for features for later queries if needed
        self.features = feature_vals
        majority = self.get_majority_label(data)
        
        if len(data) > 0:
            self.root = self.__build_rec(data, feature_vals, majority)
            
##############################################################################

    def get_best_feature(self, data, features):
        """ returns the name of the feature with the highest information gain
        to use for a split, given the current data points

        :params:
        data: a list of records representing the data.  Each record MUST have a field called "label", and other fields must be the name of a feature
        features: dictionary mapping a feature name to a list of all possible values for that feature

        returns: the name (string) of the most informative feature on which to split the given data
        """

        # Calculate the entropy of the current data set
        entropy = self.__entropy(data)

        # Initialize variables to keep track of the feature with the highest information gain and its gain
        best_feature = None
        best_gain = 0

        # Iterate over all features to calculate the information gain of each feature
        for feature in features.keys():
            # Calculate the information gain of this feature
            gain = self._info_gained(data, feature, entropy)

            # Print the feature name and its information gain
            # print(f"Feature {feature}: information gain = {gain}")

            # If this feature has a higher information gain than the current best feature, update the best feature
            if gain > best_gain:
                best_feature = feature
                best_gain = gain

        # If no feature has positive information gain, return the first feature
        if best_feature is None:
            best_feature = list(features.keys())[0]

        # Return the feature with the highest information gain
        return best_feature

    def __entropy(self, data):
        """Calculate the entropy of the given data set."""
        num_samples = len(data)
        if num_samples == 0:
            return 0

        label_counts = {}
        for sample in data:
            label = sample["label"]
            label_counts[label] = label_counts.get(label, 0) + 1

        entropy = 0
        for count in label_counts.values():
            proportion = count / num_samples
            entropy -= proportion * math.log2(proportion)

        return entropy

    def _info_gained(self, data, feature, parent_entropy):
        """Calculate the information gain of the given feature."""
        num_samples = len(data)

        # Get the unique values for the feature
        feature_values = set(sample[feature] for sample in data)

        entropy_sum = 0
        for value in feature_values:
            value_data = [sample for sample in data if sample[feature] == value]
            value_entropy = self.__entropy(value_data)
            value_proportion = len(value_data) / num_samples
            entropy_sum += value_proportion * value_entropy

        information_gain = parent_entropy - entropy_sum

        return information_gain



    def get_some_feature(self, data, features):
        """ placeholder to compare with getting the best feature
        """
        return list(features.keys())[0]


###############################################################################
     
    def get_majority_label(self, data):
        """ returns the majority label from the data set.  if there is a tie,
        then an arbitrary label is returned """
        
        histo = {}
        result = ""
        for d in data:
            label = d["label"]
            result = label
            if label not in histo:
                histo[label] = 1
            else:
                histo[label] += 1
        
        for label in histo:
            if histo[label] > histo[result]:
                result = label
        return result
       
     
    def __build_rec(self, data, features, mostcommon):
        """ recursively builds a decision tree with the given data and features,
        returning the root of that tree
        
        :params:
        data: a list of records representing the data.  Each record MUST have a field called "label", and other fields must be the name of a feature
        features: dictionary mapping a feature name to a list of all possible values for that feature
        mostcommon: string. most common label of the parent node.  This is needed in the event
        we find a feature combination of which we have seen no examples
        """
        # base case 1: data is empty, meaning we don't have any point with the current feature values
        # use the most common label from the parent's data set
        if len(data) == 0:
            return Label_Node(mostcommon)
        # base case 2: features are empty, so return label node with majority class
        elif len(features.keys()) == 0:
            majority = self.get_majority_label(data)
            return Label_Node(majority)
        else:
        
            # base case 3: all the remaining data has the same label, so features don't matter
            all_same = True
            first = data[0]["label"]
            for d in data:
                if d["label"] != first:
                    all_same = False
                    break
                    
            if all_same:
                return Label_Node(first)
            else:
                # recursive case: split on some feature
            
                # get the majority label to pass down in case there are child nodes with no data
                majority = self.get_majority_label(data)
                
                # get the feature on which to split
                feat = self.get_best_feature(data, features)
                # feat = self.get_some_feature(data, features)
                
                # temporarily remove this feature for the recursive calls
                feat_vals = features.pop(feat)
                node = Feature_Node(feat, feat_vals)
                
                # for each possible value of the chosen feature, partition the data based on that value
                for v in feat_vals:
                    partition = []
                    for d in data:
                        if d[feat] == v:
                            partition.append(d)
                            
                    child = self.__build_rec(partition, features, majority)
                    node.children[v] = child
                            
                # make sure to add the feature back!
                features[feat] = feat_vals
                return node
    

    def classify(self, sample):
        """ returns the classification label of the given sample
        
        :params:
        sample: a record represeting a data point.  keys of the record must be
        feature names that are in the tree
        
        returns: the label (string) for the given sample """
        return self.root.classify(sample)
        
    def size(self):
        """ returns the total number of nodes of any kind
        in this decision tree """
        return self.__size_rec(self.root)
        
    def __size_rec(self, node):
        if node == None:
            return 0
        elif isinstance(node, Label_Node):
            return 1
        else:
            numchildren = 0
            for child in node.children.values():
                numchildren += self.__size_rec(child)
                
            return 1 + numchildren
    
    
    def __str__(self):
        result = "Decision tree to determine the " + self.outputname + " of a data point.\n******\n"
        # do a breadth-first traversal of the tree to print. not a perfect visualization, but okayish
        queue = []
        # keep track of the depth of each node as we put it into the queue
        t = ("Root", self.root, 0)
        queue.append(t)
        cur_depth = 0
        layer = []
        
        while len(queue) > 0:
            cur = queue.pop(0)
            parent_value = cur[0]
            node = cur[1]
            depth = cur[2]
            if depth != cur_depth:
                cur_depth += 1
                result += " |--| ".join(layer)
                result += "\n\nNodes at layer " + str(cur_depth) + ": "
                layer = []
            layer.append(parent_value + " " + str(node))
            if isinstance(node, Feature_Node):
                for v, c in node.children.items():
                    next_node = (node.feature + "=" + v, c, cur_depth+1)
                    queue.append(next_node)
            
        result += " |--| ".join(layer)    
        return result
        
        