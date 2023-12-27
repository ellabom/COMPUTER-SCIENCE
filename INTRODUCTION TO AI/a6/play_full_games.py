# CMPT 317 play_full_games.py
# Collecting match-up data for computer players with different depth limits

# Copyright (c) 2022 Jeffrey R. Long
# Department of Computer Science, University of Saskatchewan

# This file is provided solely for the use of CMPT 317 students.  Students are permitted
# to use this file for their own studies, and to make copies for their own personal use.

# This file should not be posted on any public server, or made available to any party not
# enrolled in CMPT 317.

# This implementation is provided on an as-is basis, suitable for educational purposes only.

import Players
import time as time

# there's no reason not to use full-featured AlphaBeta as the player here; given the same depth limit
# Minimax would play just as well, but would take WAY longer
import AlphaBeta_Full as Searcher1 # this player will take the first move in whatever the game is
import AlphaBeta_Full as Searcher2

import Megaman_Arena as Game

# create a list of all the tuples of depth-pairs that we want to try for player 1 and player 2

light = []
wily = []
even = []

# advantage Light
#light = [ (1, 0), (2, 1), (3, 2), (4, 3), (2, 0), (3, 1), (4, 2), (3, 0), (4, 1), (4, 0) ]
light = [ (7, 0), (7, 3) ]

# advantage Wily
#wily = [ (0, 1), (1, 2), (2, 3), (3, 4), (0, 2), (1, 3), (2, 4), (0, 3), (1, 4), (0, 4) ]
wily = [ (0, 7), (3, 7) ]

# even match
#even = [(0, 0), (1, 1), (2, 2), (3, 3), (4, 4)]
even = [(0, 0), (8, 8)]

list_of_depths = light + wily + even

for depth1, depth2 in list_of_depths:
    # create the game, and the initial state

    game1 = Game.Game(depthlimit=depth1)
    game2 = Game.Game(depthlimit=depth2)
    state = game1.initial_state()
    state2 = game2.initial_state()

    # set up the players
    current_player = Players.SilentComputer(game1, Searcher1.Minimax(game1))
    other_player = Players.SilentComputer(game2, Searcher2.Minimax(game2))

    # I don't think it matters which game is used
    # but this ensures no cross talk in case I have a bug somewhere
    current_game, other_game = game1, game2
    
    start = time.perf_counter()

    # play the game
    while not current_game.is_terminal(state):

        # state.display()

        # ask the current player for a move
        choice = current_player.ask_move(state)
    
        # check the move
        assert choice in current_game.actions(state), "The action <{}> is not legal in this state".format(choice)

        # apply the move
        state = current_game.result(state, choice)
    
        # swap the players
        current_player, current_game, other_player, other_game = other_player, other_game, current_player, current_game

    # game's over
    end = time.perf_counter()
    print("-------------")
    print("Game took", (end-start), "seconds")
    print('For trial', depth1, depth2, end=': ')
    game1.congratulate(state)

# eof

