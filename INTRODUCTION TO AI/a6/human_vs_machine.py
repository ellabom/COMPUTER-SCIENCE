# CMPT 317 human_vs_machine.py
# script to make it easier to play vs a computer player

# Copyright (c) 2022 Jeffrey R. Long
# Department of Computer Science, University of Saskatchewan

# This file is provided solely for the use of CMPT 317 students.  Students are permitted
# to use this file for their own studies, and to make copies for their own personal use.

# This file should not be posted on any public server, or made available to any party not
# enrolled in CMPT 317.

# This implementation is provided on an as-is basis, suitable for educational purposes only.

import Players
import sys
import AlphaBeta_Full as Computer 
import Megaman_Arena as Game

if len(sys.argv) < 3:
    print("useage: python human_vs_machine.py <side> <depth_limit>")
    print("Side is L for human to play Mega Man, W for human to play Dr. Wily")
    print("Choosing a higher depth limit should make the computer player stronger")
    exit()
    
side = sys.argv[1]
depth_limit = int(sys.argv[2])



# create the game, and the initial state

game1 = Game.Game(depthlimit=depth_limit)
game2 = Game.Game(depthlimit=depth_limit)
state = game1.initial_state()
state2 = game2.initial_state()

# set up the players
if side == "L":
    current_player = Players.HumanMenu(game1)
    other_player = Players.VerboseComputer(game2, Computer.Minimax(game2))
else:
    current_player = Players.VerboseComputer(game1, Computer.Minimax(game1))
    other_player = Players.HumanMenu(game2)



# I don't think it matters which game is used
# but this ensures no cross talk in case I have a bug somewhere
current_game, other_game = game1, game2

# play the game
while not current_game.is_terminal(state):

    # ask the current player for a move
    choice = current_player.ask_move(state)

    # check the move
    assert choice in current_game.actions(state), "The action <{}> is not legal in this state".format(choice)

    # apply the move
    state = current_game.result(state, choice)

    # swap the players
    current_player, current_game, other_player, other_game = other_player, other_game, current_player, current_game

# game's over
game1.congratulate(state)

