# CMPT 317 Players.py
# Convenience class for pitting players against each other

# Copyright (c) 2016-2022 Michael C Horsch, Jeffrey R Long
# Department of Computer Science, University of Saskatchewan

# This file is provided solely for the use of CMPT 317 students.  Students are permitted
# to use this file for their own studies, and to make copies for their own personal use.

# This file should not be posted on any public server, or made available to any party not
# enrolled in CMPT 317.

# This implementation is provided on an as-is basis, suitable for educational purposes only.

##########################################################################################
# These classes help make it easier to interact with human and computer players
# by using a common interface.  
#     PlayerInterface
#     - a base class that remembers the game
#     HumanMenu
#     - uses the game to create a numbered list of moves
#     - asks the user to choose one of the moves
#     - interaction through the console
#     ComputerInterface
#     - needs a search class object (e.g., Minimax, AlphaBeta, etc)
#     VerboseComputer
#     - subclass of ComputerInterface to obtain moves from a search algorithm
#     - adds a bit of console IO for human v computer, or other debugging purposes
#     SilentComputer
#     - subclass of ComputerInterface to obtain moves from a search algorithm
#     - no console IO at all
#     - good for Computer vs Computer where you just care about the outcome

# The interface features the method
#   ask_move(state)
#   - returns an action legal in the given state
#     (the action must be an action as defined in game.actions())
#
# Here's an example of how to use these classes:
#   # setup
#   game = <create a game object from a Game Class>
#   searcher = Minimax(game)         # create a search class instance
#   player1 = Players.VerboseComputer(game, Searcher.Minimax(game))
#   player2 = Players.HumanMenu(game)
#
#   # player1's turn!
#   state = game.initial_state()
#   choice1 = player1.ask_move(state)
#   assert choice1 in game.actions(state), "The action <{}> is not legal in this state".format(choice)
# 
#   # player2's turn!
#     choice2 = player2.ask_move(state)
#     assert choice2 in game.actions(state), "The action <{}> is not legal in this state".format(choice)


##########################################################################################
class PlayerInterface(object):
    """ A base class for Player interfaces
        Just remember what game is being played.
    """
    def __init__(self, game):
        self.game = game


##########################################################################################
class HumanMenu(PlayerInterface):
    """ This interface puts the actions in a menu, asks for a choice
        from the menu, and waits for the user to type something.
    """
    def __init__(self, game):
        PlayerInterface.__init__(self, game)

    def ask_move(self, state):
        """ Present the human player with a menu of possible moves.
            The moves are obtained from the game, so they should be legal.
            :param state: a legal game state
            :return: an action in the game
        """

        # ask the user to make a move (and show an example format)
        # assumes that moves are tuples!
        state.display()
        actions = self.game.actions(state)
        example = actions[0]
        print("Human turn!  Type a move (example: " + example + ")")
        
        
        # wait for a valid choice from the menu
        valid = False
        while not valid:
            choice = input("Your move: ")
            
            if choice not in actions:
                print("Invalid move format")
            else:
                valid = True
        
        # return the choice
        return choice


##########################################################################################
class ComputerInterface(PlayerInterface):
    """ This subclass of PlayerInterface stores a search class object.
        The searcher has to have the following methods:
            minimax_decision_max(state)
            minimax_decision_min(state)
    """
    def __init__(self, game, searcher):
        PlayerInterface.__init__(self, game)
        self.searcher = searcher

    def _ask_move_searcher(self, state):
        """ This method interacts with the searcher object.
            Sub-classes can use this, and do other things as well.
            :param state: a legal game state
            :return: a SearchTerminationRecord
        """
        if self.game.is_maxs_turn(state):
            result = self.searcher.minimax_decision_max(state)
        else:
            result  = self.searcher.minimax_decision_min(state)
        return result
       
        
##########################################################################################
class SilentComputer(ComputerInterface):
    """ This class has no console IO.
        The searcher has to have the following methods:
            minimax_decision_max(state)
            minimax_decision_min(state)
    """
    def __init__(self, game, searcher):
        ComputerInterface.__init__(self, game, searcher)

    def ask_move(self, state):
        """ Get a move from the search algorithm.  No console IO.
            :param state: a legal game state
            :return: a legal move in the given state as defined by game.actions()
        """
        # need to call search and return the choice
        result = super()._ask_move_searcher(state)
        return result.move


##########################################################################################
class VerboseComputer(ComputerInterface):
    """ This interface uses the ComputerInterface to get a move 
        from the searcher.
        This version has some dialogue, and shows the details about the move.  
        A bit boring?
    """
    def __init__(self, game, searcher):
        ComputerInterface.__init__(self, game, searcher)

    def ask_move(self, state):
        """ Get a move from the search algorithm.  Some dialogue on console IO.
            :param state: a legal game state
            :return: a legal move in the given state as defined by game.actions()
        """
        print("Thinking...")

        # need to call search and return the choice
        result = super()._ask_move_searcher(state)

        print("...done")
        result.display()

        return result.move

