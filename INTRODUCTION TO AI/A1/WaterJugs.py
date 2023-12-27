# EMMANUELLA EY0
# EEE917
# 11291003


# CMPT 317: Problem Class Template

# Copyright (c) 2016-2019 Michael C Horsch and Jeff Long,
# Department of Computer Science, University of Saskatchewan

# This file is provided solely for the use of CMPT 317 students.  Students are permitted
# to use this file for their own studies, and to make copies for their own personal use.

# This file should not be posted on any public server, or made available to any party not
# enrolled in CMPT 317.

# This implementation is provided on an as-is basis, suitable for educational purposes only.
#

import random as rand
import math as maths

class State(object):

    def __init__(self, preceding_action, states=[0, 0, 0]):
        """
        Initialize the State object.
        """
        # each state MUST store the action that created it
        # if you want to be able to actually see your solutions
        self.action = preceding_action
        self.states = states


    def __str__(self):
        """ A string representation of the State """
        return str(self.states)

    def __eq__(self, other):
        """ Allows states to be compared"""
        return self.states == other.states

    


class Problem(object):
    """The Problem class defines aspects of the problem.
       One of the important definitions is the transition model for states.
       To interact with search classes, the transition model is defined by:
            is_goal(s): returns true if the state is a goal state.
            actions(s): returns a list of all legal actions in state s
            result(s,a): returns a new state, the result of doing action a in state s

    """

    def __init__(self, goal):
        """ The problem is defined by a target value.  We want to measure that many
        liters of water.

            :param goal: the number of liters of water we want to measure

        """
        self.goal = goal
        self.jugs = [15,7,4]
        # INSERT WHATEVER ELSE YOU NEED HERE


    def create_initial_state(self):
        """ returns an initial state
        """

        return State(None)

    def is_goal(self, a_state: State):
        """Determine whether a_state is a goal state"""

        return self.goal in a_state.states

    def actions(self, a_state:State):
        """ Returns all the actions that are legal in the given state.

        """
        l_actions = []

        for i, capacity in enumerate(self.jugs):
            jug = a_state.states[i]

            if jug < capacity:
                l_actions.append('Fill jug ' + str(capacity))

            if jug > 0:
                l_actions.append('Empty jug ' + str(capacity))

            if jug > 0:
                for j, j_cap in enumerate(self.jugs):
                    if i == j and a_state.states[j] == j_cap:
                        continue

                    l_actions.append('Pour jug ' + str(capacity) + ' to jug ' + str(j_cap))

        return l_actions

    def result(self, a_state: State, an_action):
        """Implements the transition function.
        Given a state and an action, return the resulting state.
        """

        capacities = self.jugs
        transfers = [0, 0, 0]
        split = an_action.split()

        if an_action.find('Fill jug') == 0:
            i = capacities.index(int(split[2]))
            transfers[i] = capacities[i] - a_state.states[i]

        elif an_action.find('Empty jug') == 0:
            i = capacities.index(int(split[2]))
            transfers[i] = 0
        else:
            i = capacities.index(int(split[2]))
            j = capacities.index(int(split[5]))
            transfer = min(a_state.states[i], capacities[j] - a_state.states[j])
            transfers[i] = -transfer
            transfers[j] = transfer

        new_state = [sum(x) for x in zip(a_state.states, transfers)]
        return State(an_action, new_state)