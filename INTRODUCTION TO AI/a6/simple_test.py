import Players
import AlphaBeta_Full as Searcher
import Megaman_Arena as G
import random as rand

g = G.Game()
state = g.initial_state()
state.display()
#print(state)
for i in range(5):
    actions = g.actions(state)
    a = rand.choice(actions)
    if state.maxs_turn:
        player = "Light"
    else:
        player = "Wily"
    print("\n--------------")
    print(player, "took action", a)
    print("--------------\n")
    #old_state = state
    state = g.result(state, a)
    #print("old")
    #print(old_state)
    print("new")
    print(state)
    print("Eval: ", g.eval(state))


