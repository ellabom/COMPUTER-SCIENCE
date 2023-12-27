# CMPT 317 A Python implementation of Game Tree Search - Minimax with some data collection

# Copyright (c) 2016-2019 Michael C Horsch,
# with tiny modification by Jeff Long
# Department of Computer Science, University of Saskatchewan

# This file is provided solely for the use of CMPT 317 students.  Students are permitted
# to use this file for their own studies, and to make copies for their own personal use.

# This file should not be posted on any public server, or made available to any party not
# enrolled in CMPT 317.

# This implementation is provided on an as-is basis, suitable for educational purposes only.

##########################################################################################
# This module implements 
#     Minimax search with data collection 
#
# The two methods that can be called directly are:
#    minimax_decision_max(state)  # find the best move for Max in the give state
#    minimax_decision_min(state)  # find the best move for Min in the give state
#
# This Search be used with any Game Class that has the following interface:
#    initial_state(self)
#       -returns an initial game state
#    is_mins_turn(self, state)
#    is_maxs_turn(self, state)
#       - indicates if it's Min/Max's turn
#    is_terminal(self, state)
#       - indicates if the game is over
#    utility(self, state)
#       - the utility value of the given state
#    actions(self, state)
#       - list actions legal in the given state
#    result(self, state, action)
#       - give the rstate resulting from the action in the given state


# To use the MinimaxData class, you need the following steps
#    game = <create a game object from a Game Class>
#    searcher = MinimaxData(game)         # create an instance of Minimax for the game
#    state = game.initial_state()     # any valid state will do
#    if game.is_maxs_turn(state):
#        result = searcher.minimax_decision_max(state)
#    else:
#        result  = searcher.minimax_decision_min(state)
#    # result is a SearchTerminationRecord
#    result.display()

import time as time

##########################################################################################
class SearchTerminationRecord(object):
    """A record to return information about how the search turned out.
       All the details are provided in a record, to avoid needing to print out the details
       at different parts of the code.
    """

    def __init__(self, value, move, time=0, nodes=0, cutoff=False):
        self.value = value      # numeric: the minimax value
        self.move = move        # a move with the stated minimax value
        self.time = time        # float: how much time was spent searching?  Not really accurate, but good enough for fun
        self.nodes = nodes      # integer: How many nodes were expanded during the search?
        self.cutoff = cutoff    # boolean: was the search cut-off?

    def __str__(self):
        """Create a string representation of the Result data
           This string doesn't show everything it could.
        """
        text = 'Chose move <{}> with {} Minimax value {} after {:.4f} seconds, expanding {} nodes'
        # if self.cutoff:
            # textsuccess = 'estimated'
        # else:
            # textsuccess = 'true'
        # cutoff reporting not implemented
        textsuccess = ""
        return text.format(self.move, textsuccess, self.value, self.time, self.nodes)

    def display(self):
        """Display the record to the console
        """
        print(str(self))


##########################################################################################
class Minimax(object):
    """ An implementation of MiniMax Search
        - no pruning
        - no transposition table
    """

    # a clumsy way to represent a large value
    ifny = 2**20

    def __init__(self, game):
        """ Remember the game object.
            :param: game: an object from the Game Class, with methods as described
                    at the top of this document.
        """
        self.game = game
        self.nodes_expanded = 0

    def minimax_decision_max(self, state):
        """ Return the move that Max should take in the given state
            :param state: a legal game state
            :return: a SearchTerminationRecord
        """
        start = time.perf_counter()
        self.nodes_expanded = 0

        # look for the best among Max's options
        best = -self.ifny
        best_action = None

        self.nodes_expanded += 1
        for act in self.game.actions(state):
            val = self.__min_value(self.game.result(state, act), 1)
            if val > best:
                # remember something better
                best = val
                best_action = act

        end = time.perf_counter()

        return SearchTerminationRecord(best, best_action, end - start, self.nodes_expanded)

    def minimax_decision_min(self, state):
        """ Return the move that Min should take in the given state
            :param state: a legal game state
            :return: a SearchTerminationRecord
        """
        start = time.perf_counter()
        self.nodes_expanded = 0

        # look for the best among Min's options
        best = self.ifny
        best_action = None

        self.nodes_expanded += 1
        for act in self.game.actions(state):
            val = self.__max_value(self.game.result(state, act), 1)
            if val < best:
                # remember something better
                best = val
                best_action = act

        end = time.perf_counter()

        return SearchTerminationRecord(best, best_action, end - start, self.nodes_expanded)

    def __max_value(self, state, depth):
        """ Return the minimax value of the given state.
            :param state: a legal game state
            :return: the value that Max can obtain here
        """
        if self.game.is_terminal(state):
            # the game is over, return the utility
            best = self.game.utility(state)
        elif self.game.cutoff_test(state, depth):
            best = self.game.eval(state)
        else:
            # look for the best among Max's options
            best = -self.ifny
            self.nodes_expanded += 1
            for act in self.game.actions(state):
                val = self.__min_value(self.game.result(state, act), depth+1)
                if val > best:
                    # remember something better
                    best = val
        return best

    def __min_value(self, state, depth):
        """ Return the minimax value of the given state.
            :param state: a legal game state
            :return: the value that Min can obtain here
        """
        if self.game.is_terminal(state):
            # the game is over, return the utility
            best = self.game.utility(state)
        elif self.game.cutoff_test(state, depth):
            best = self.game.eval(state)
        else:
            # look for the best among Max's options
            best = self.ifny
            self.nodes_expanded += 1
            for act in self.game.actions(state):
                val = self.__max_value(self.game.result(state, act), depth+1)
                if val < best:
                    # remember something better
                    best = val
        return best


