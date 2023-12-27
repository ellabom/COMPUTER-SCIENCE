# CMPT 317 compute_firstmove.py
# collect info on timing for differnet depth-limits for game search

# Copyright (c) 2016-2022 Michael C Horsch, Jeffrey R Long
# Department of Computer Science, University of Saskatchewan

# This file is provided solely for the use of CMPT 317 students.  Students are permitted
# to use this file for their own studies, and to make copies for their own personal use.

# This file should not be posted on any public server, or made available to any party not
# enrolled in CMPT 317.

# This implementation is provided on an as-is basis, suitable for educational purposes only.

import Players
#import Minimax as Searcher
import AlphaBeta_Full as Searcher
import Megaman_Arena as Game

# to generate the table, we'll create a list for the input depths
list_of_depths = [1, 2, 3, 4, 5, 6, 7, 8, 9]

for depth in list_of_depths:

    print('Running depth', depth)
    # create the game, and the initial state
    game = Game.Game(depthlimit=depth)
    state = game.initial_state()

    # set up the player
    current_player = Players.VerboseComputer(game, Searcher.Minimax(game))
    current_player.ask_move(state)

    
