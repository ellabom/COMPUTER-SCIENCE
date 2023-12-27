# EMMANUELLA EYO 11291003 EEE917


# CMPT 317 A Python Class for Megaman Arena Battles

# Copyright (c) 2022, Jeffrey R. Long
# Department of Computer Science, University of Saskatchewan

# This file is provided solely for the use of CMPT 317 students.  Students are permitted
# to use this file for their own studies, and to make copies for their own personal use.

# This file should not be posted on any public server, or made available to any party not
# enrolled in CMPT 317.

# This implementation is provided on an as-is basis, suitable for educational purposes only.

# The Game Class encodes the rules of a game.  
# Game Class Interface:
#    initial_state(self)
#       - returns an initial game state
#       - the state can be any object that stores the details 
#         needed to keep track of the game, including any information
#         convenient to store
#           
#    is_mins_turn(self, state)
#    is_maxs_turn(self, state)
#       - return a boolean that indicates if it's Min/Max's turn
#       - max is Dr. Light, min is Dr. Wily
#           
#    is_terminal(self, state)
#       - return a boolean that indicates if the state represents
#         a true end of the game situation, i.e, a win for either side
#           
#    utility(self, state)
#       - return the utility value of the given terminal state
#       - must return one of two values: k_min, k_max
#           k_min: the value returned if Min is the winner
#           k_max: the value returned if Max is the winner
#           - any range is allowed. 
#           - must be the case that k_min < k_max
#       - will only be called if the state is determined to be
#         a terminal state by is_terminal()
#       - only terminal states have utility; other states get 
#         their value from searching.
#           
#    actions(self, state)
#       - returns a list of actions legal in the given state
#           
#    result(self, state, action)
#       - returns the state resulting from the action in the given state
#           
#    cutoff_test(self, state, depth)
#       - returns a bolean that indicates if this state and depth is suitable 
#         to limit depth of search.  A simple implementation might just look 
#         at the depth; a more sophisticated implementation might look at 
#         the state as well as the depth.
#           
#    eval(self, state)
#       - returns a numeric value that estimates the minimax value of the
#         given state.  This gets called if cutoff_test() returns true.
#         Instead of searching to the bottom of the tree, this function
#         tries to guess who might win.  The function should return a value 
#         that is in the range defined by utility().  Because this is an
#         estimate, values close to k_min (see utility()) indicate that 
#         a win for Min is likely, and values close to k_max should indicate 
#         a win for Max is likely.  Should not return values outside the range
#         (k_min, k_max).  k_min means "Min wins"; a value smaller than k_min
#         makes no sense.  An estimate from eval() cannot be more extreme than a 
#         fact known from utility().
#           
#    transposition_string(self)
#       - return a string representation of the state
#       - for use in a transposition table
#       - this string should represent the state exactly, but also without
#         too much waste.  In a normal game, lots of these get stored!
#           
#    congratulate(self)
#       - could be called at the end of the game to indicate who wins
#       - this is not absolutely necessary, but could be informative

import random as rand
import math as math

class GameState(object):
    """ The GameState class stores the information about the state of the game.
    """

    def __init__(self, robot_healths={}, light_roster=[], wily_roster=[],light_champ="", wily_champ="", mega_weapons=[], maxs_turn=True):
        # store the list of robots for both sides
        self.healths = robot_healths

        self.light_roster = light_roster
        self.wily_roster = wily_roster

        # store any champions currently in the arena
        self.light_champ = light_champ
        self.wily_champ = wily_champ

        # store the weapons Mega Man has acquired on Dr. Light's side
        self.mega_weapons = mega_weapons

        # a boolean to store if it's Max's turn
        self.maxs_turn = maxs_turn

        # if this state is a terminal state, store that information
        # because it is cheaper to check once, than a bunch of times
        self.cachedTerminal = False

        # if cachedTerminal is True:
        #       cachedOutcome == True means Max won;
        #       cachedOutcome == False means Min won
        #       cachedOutcome == None means no outcome yet
        self.cachedOutcome = None

        # at the very end, cache the string that represents this state
        self.stringified = str(self)


    def myclone(self):
        """ Make and return an exact copy of the state.
        """
        new_state = GameState()

        new_state.healths = {} # jeffnote: apparently the {} in the default parameters does NOT create a new dictionary and so THIS IS NECESSARY for a correct copy

        #copy mutable portions of state
        for robot, life in self.healths.items():
            new_state.healths[robot] = life

        # the rosters just state which side each robot is on, even if loser,
        # so shouldn't NEED to copy it, but just in case
        new_state.light_roster = [r for r in self.light_roster]
        new_state.wily_roster = [r for r in self.wily_roster]

        new_state.mega_weapons = [w for w in self.mega_weapons]

        # the rest are immutable anyway
        new_state.light_champ = self.light_champ
        new_state.wily_champ = self.wily_champ
        new_state.maxs_turn = self.maxs_turn
        new_state.cachedTerminal = self.cachedTerminal
        new_state.cachedOutcome = self.cachedOutcome
        new_state.stringified = self.stringified

        return new_state

    def display(self):
        """
        Present the game state to the console.
        """
        print()
        if (self.maxs_turn):
            print("Dr. Light's Turn: Send a champion!")
        else:
            print("Dr. Wily's Turn: Send a champion!")
        print("****")
        print("Light Robot Roster:")
        roster = []
        for robot in self.light_roster:
            if self.healths[robot] > 0:
                s = robot + ": " + str(self.healths[robot])
                roster.append(s)
        print(", ".join(roster))
        print("Mega Man's weapons: ", ", ".join(self.mega_weapons))
        print("*"*10)
        if self.light_champ == "":
            print("Awaiting champion...")
        else:
            # print(self.light_champ, "|"*self.healths[self.light_champ]) # more thematic, but less useful
            print(self.light_champ, self.healths[self.light_champ])
        print()
        print("VS!!!!!")
        print()
        if self.wily_champ == "":
            print("Awaiting champion...")
        else:
            # print(self.wily_champ, "|"*self.healths[self.wily_champ]) # more thematic, but less useful
            print(self.wily_champ, self.healths[self.wily_champ])
        print("*"*10)
        print("Wily Robot Roster:")
        roster = []
        for robot in self.wily_roster:
            if self.healths[robot] > 0:
                s = robot + ": " + str(self.healths[robot])
                roster.append(s)
        print(", ".join(roster))

        print()


    def __str__(self):
        """ Translate the board description into a string.
            Could be used as a key for a hash table.
            :return: A string that describes the board in the current state.
        """
        s = ""
        s += str(self.healths)
        s += self.light_champ
        s += self.wily_champ
        s += str(self.mega_weapons)
        s += str(self.maxs_turn)
        return s



class Game(object):
    """ The Game object defines the interface that is used by Game Tree Search
        implementation.
    """

    def __init__(self, depthlimit=0):
        """ Initialization.
        """
        self.depth_limit = depthlimit
        # dictionary that applies to the whole game that determines
        # how much damage each robot takes from each weapon
        # keys are the attacker's name, mapped to a dictionary stating
        # how much damage a defender would take from that attack
        # The 8 Wily robots need more entries, since they fight the Light
        # robots BUT Mega Man can gain their weapons to use against them!
        self.weapon_damage = {
        "Mega Man": {"Metal Man":1, "Quick Man":2, "Air Man":2, "Crash Man":1, "Flash Man":2, "Bubble Man":1, "Wood Man":1, "Heat Man":2, "Gamma":1},

        "Spark Man": {"Metal Man":2, "Quick Man":1, "Air Man":4, "Crash Man":1,
        "Flash Man":1, "Bubble Man":4, "Wood Man":1, "Heat Man":1, "Gamma":1},

        "Snake Man": {"Metal Man":1, "Quick Man":4, "Air Man":2, "Crash Man":1,
        "Flash Man":1, "Bubble Man":1, "Wood Man":4, "Heat Man":1, "Gamma":2},

        "Needle Man": {"Metal Man":1, "Quick Man":1, "Air Man":1, "Crash Man":2,
        "Flash Man":4, "Bubble Man":2, "Wood Man":4, "Heat Man":2, "Gamma":1},

        "Hard Man": {"Metal Man":4, "Quick Man":1, "Air Man":2, "Crash Man":7,
        "Flash Man":1, "Bubble Man":2, "Wood Man":2, "Heat Man":2, "Gamma":4},

        "Top Man": {"Metal Man":1, "Quick Man":1, "Air Man":1, "Crash Man":4,
        "Flash Man":1, "Bubble Man":1, "Wood Man":2, "Heat Man":7, "Gamma":12},

        "Gemini Man": {"Metal Man":1, "Quick Man":4, "Air Man":2, "Crash Man":1,
        "Flash Man":4, "Bubble Man":1, "Wood Man":1, "Heat Man":1, "Gamma":1},

        "Magnet Man": {"Metal Man":4, "Quick Man":2, "Air Man":4, "Crash Man":1,
        "Flash Man":1, "Bubble Man":1, "Wood Man":1, "Heat Man":1, "Gamma":1},

        "Shadow Man": {"Metal Man":2, "Quick Man":2, "Air Man":1, "Crash Man":1,
        "Flash Man":2, "Bubble Man":4, "Wood Man":2, "Heat Man":4, "Gamma":2},

        "Metal Man": {"Mega Man":1, "Metal Man":14, "Air Man":0, "Bubble Man":4,
        "Quick Man":0, "Crash Man":0, "Flash Man":4, "Heat Man":1, "Wood Man":1,
        "Needle Man":2, "Magnet Man":2, "Gemini Man":2, "Hard Man":2, "Top Man":2,
        "Snake Man":2, "Spark Man":2, "Shadow Man":2, "Gamma":1},

        "Air Man": {"Mega Man":1, "Metal Man":0, "Air Man":0, "Bubble Man":0,
        "Quick Man":2, "Crash Man":10, "Flash Man":0, "Heat Man":2, "Wood Man":4,
        "Needle Man":2, "Magnet Man":2, "Gemini Man":2, "Hard Man":2, "Top Man":2,
        "Snake Man":2, "Spark Man":2, "Shadow Man":2, "Gamma":1},

        "Bubble Man": {"Mega Man":1, "Metal Man":0, "Air Man":0, "Bubble Man":0,
        "Quick Man":0, "Crash Man":1, "Flash Man":2, "Heat Man":6, "Wood Man":0,
        "Needle Man":2, "Magnet Man":2, "Gemini Man":2, "Hard Man":2, "Top Man":2,
        "Snake Man":2, "Spark Man":2, "Shadow Man":2, "Gamma":10},

        "Quick Man": {"Mega Man":1, "Metal Man":4, "Air Man":2, "Bubble Man":2,
        "Quick Man":0, "Crash Man":1, "Flash Man":0, "Heat Man":2, "Wood Man":0,
        "Needle Man":2, "Magnet Man":2, "Gemini Man":2, "Hard Man":2, "Top Man":2,
        "Snake Man":2, "Spark Man":2, "Shadow Man":2, "Gamma":2},

        "Crash Man": {"Mega Man":1, "Metal Man":0, "Air Man":0, "Bubble Man":2,
        "Quick Man":4, "Crash Man":0, "Flash Man":3, "Heat Man":0, "Wood Man":2,
        "Needle Man":2, "Magnet Man":2, "Gemini Man":2, "Hard Man":2, "Top Man":2,
        "Snake Man":2, "Spark Man":2, "Shadow Man":2, "Gamma":6},

        "Flash Man": {"Mega Man":1, "Metal Man":0, "Air Man":0, "Bubble Man":0,
        "Quick Man":14, "Crash Man":0, "Flash Man":0, "Heat Man":0, "Wood Man":0,
        "Needle Man":2, "Magnet Man":2, "Gemini Man":2, "Hard Man":2, "Top Man":2,
        "Snake Man":2, "Spark Man":2, "Shadow Man":2, "Gamma":0},

        "Heat Man": {"Mega Man":1, "Metal Man":2, "Air Man":4, "Bubble Man":0,
        "Quick Man":6, "Crash Man":2, "Flash Man":3, "Heat Man":0, "Wood Man":30,
        "Needle Man":2, "Magnet Man":2, "Gemini Man":2, "Hard Man":2, "Top Man":2,
        "Snake Man":2, "Spark Man":2, "Shadow Man":2, "Gamma":0},

        "Wood Man": {"Mega Man":1, "Metal Man":0, "Air Man":8, "Bubble Man":0,
        "Quick Man":0, "Crash Man":0, "Flash Man":0, "Heat Man":0, "Wood Man":0,
        "Needle Man":2, "Magnet Man":2, "Gemini Man":2, "Hard Man":2, "Top Man":2,
        "Snake Man":2, "Spark Man":2, "Shadow Man":2, "Gamma":0},

        "Gamma": {"Mega Man":2, "Needle Man":2, "Magnet Man":2, "Gemini Man":2, "Hard Man":2, "Top Man":2, "Snake Man":2, "Spark Man":2, "Shadow Man":2}
        }



    def initial_state(self):
        """ Return an initial state for the game.
        """
        # the default GameState constructor creates the initial starting position
        healths = {"Mega Man": 30, "Spark Man": 30, "Snake Man": 30, "Needle Man": 30, "Hard Man": 30, "Top Man": 30, "Gemini Man": 30, "Magnet Man": 30, "Shadow Man": 30, "Bubble Man": 30, "Air Man": 30, "Quick Man": 30, "Heat Man": 30, "Wood Man": 30, "Metal Man": 30, "Flash Man": 30, "Crash Man": 30, "Gamma":99}

        light = ["Mega Man", "Spark Man", "Snake Man", "Needle Man", "Hard Man", "Top Man", "Gemini Man", "Magnet Man", "Shadow Man"]

        wily = ["Bubble Man", "Air Man", "Quick Man", "Heat Man", "Wood Man", "Metal Man", "Flash Man", "Crash Man", "Gamma"]

        state = GameState(robot_healths=healths, light_roster=light, wily_roster=wily)


        return state

    def is_mins_turn(self, state):
        """ Indicate if it's Min's turn
            :return: True if it's Min's turn to play
        """
        return not state.maxs_turn

    def is_maxs_turn(self, state):
        """ Indicate if it's Min's turn
            :return: True if it's Max's turn to play
        """
        return state.maxs_turn

    def is_terminal(self, state):
        """ Indicate if the game is over.
            :param node: a game state with stored game state
            :return: a boolean indicating if node is terminal
        """
        return state.cachedTerminal

    def cutoff_test(self, state, depth):
        """
            Check if the search should be cut-off early.
            In a more interesting game, you might look at the state
            and allow a deeper search in important branches, and a shallower
            search in boring branches.

            :param state: a game state
            :param depth: the depth of the state,
                          in terms of levels below the start of search.
            :return: True if search should be cut off here.
        """
        return depth > self.depth_limit

    def transposition_string(self, state):
        """ Returns a unique string for the given state.  For use in
            any Game Tree Search that employs a transposition table.
            :param state: a legal game state
            :return: a unique string representing the state
        """
        return state.stringified

    def actions(self, state):
        """ Returns all the legal actions in the given state.

            :param state: a state object
            :return: a list of actions legal in the given state
        """
        # TODO: return the list of actions available in this state
        # You PROBABLY want each action to be just the name of the champion tht is being sent to the field.  If you do anything else, you might have to edit the 'HumanMenu' interface in Players.py
        actions = []
        if state.maxs_turn:
            for robot in state.light_roster:
                if state.healths[robot] > 0:
                    actions.append(robot)
        else:
            for robot in state.wily_roster:
                if state.healths[robot] > 0:
                    actions.append(robot)

        return actions
    def result(self, state, action):
        """Return the state that results from the application of the
        given action in the given state.

        :pre-conditions: We assume the action is legal
        :param state: a legal game state
        :param action: a legal action in the game state
        :return: a new game state
        """
        new_state = state.myclone()

        if state.maxs_turn:
            new_state.light_champ = action
        else:
            # Set new action for Wily champion
            new_state.wily_champ = action

            # Calculate damage and health for Light and Wily champions
            Light_damage = self.weapon_damage[new_state.light_champ][new_state.wily_champ]
            Wily_damage = self.weapon_damage[new_state.wily_champ][new_state.light_champ]
            Light_health = new_state.healths[new_state.light_champ]
            Wily_health = new_state.healths[new_state.wily_champ]

            # Check if Light champion is Mega Man and update damage
            if new_state.light_champ == "Mega Man":
                Light_damage = max(
                    [self.weapon_damage[a][new_state.wily_champ] for a in new_state.mega_weapons] + [Light_damage])

            # Calculate survival rounds for both champions
            lrounds_left = math.ceil(Light_health / Light_damage)
            wrounds_left = math.ceil(Wily_health / Light_damage)

            # Update health and champion for Light and Wily champions respectively
            new_state.healths[
                new_state.wily_champ] = 0 if wrounds_left > lrounds_left else Wily_health - lrounds_left * Light_damage
            new_state.healths[
                new_state.light_champ] = 0 if lrounds_left > wrounds_left else Light_health - wrounds_left * Wily_damage
            new_state.wily_champ, new_state.light_champ = ("", "") if wrounds_left > lrounds_left else (
            new_state.wily_champ, new_state.light_champ)
            defeat = new_state.wily_champ if wrounds_left <= lrounds_left else ""

            # Check if Light champion is Mega Man and update health and mega weapons
            if new_state.light_champ == "Mega Man":
                new_state.healths[new_state.light_champ] += 5
                new_state.mega_weapons.append(defeat)

        new_state.maxs_turn = not new_state.maxs_turn

        # Update the hash string for this state
        new_state.stringified = str(new_state)

        return new_state

    def utility(self, state):
        """ Calculate the utility of the given state.
        This method is only called in TERMINAL states

            :param state: a legal game state
            :return: utility of the terminal state
        """
        if state.cachedOutcome is not None:
            return 100 if state.cachedOutcome else -100

        total_health = sum(state.healths[x] for x in state.light_roster)
        if total_health <= 0:
            state.cachedOutcome = False
            return -100
        else:
            state.cachedOutcome = True
            return 100

    def eval(self, state):
        if state.cachedTerminal:
            return state.cachedOutcome

        total_score = 0

        # Calculate score based on remaining health of robots
        # Calculate score for defeated robots
        for robot in state.healths:
            if state.healths[robot] == 0:
                score = 15 if robot not in state.light_roster else -15
                total_score += score

        # Add score for number of defeated robots
        total_score += len(state.mega_weapons) * 5

        # Add bonus score for Mega Man's health
        light_champ_health = state.healths.get(state.light_champ, 0)
        if state.light_champ in state.light_roster and light_champ_health >= 5:
            total_score += 10
        elif state.light_champ in state.light_roster and light_champ_health < 5:
            total_score -= 10

        return total_score


    def congratulate(self, state):
        """ Called at the end of a game, display some appropriate
            sentiments to the console. Could be used to display
            game statistics as well.
            :param state: a legal game state
        """
        print("The game has ended!")
        if state.cachedOutcome:
            print("Congratulations, " + state.light_champ + ", you won!")
        elif state.cachedOutcome is False:
            print("Congratulations, " + state.wily_champ + ", you won!")
        else:
            print("It's a tie!")



# eof