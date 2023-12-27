import simpleTetris as P

# some ideas for doing simple testing to ensure correctness
N = 4
grid = [0] * N
blocks = {(3, 2): 1,
            (2, 3): 1,
            (2, 2): 1,
            (1, 2): 2,
            (2, 1): 1,
            (1, 1): 4}

problem = P.InformedProblem(N, blocks) 
# see if the initial state looks right
print("Initial state:")
s = problem.create_initial_state()
print(s)
print()

# print("\nActions:")
actions = problem.actions(s)
# print(actions)


# check if applying an action leads to the correct next state
action = actions[0]
print("Applying action:", action)
new_state = problem.result(s, action)
print(new_state)

# make sure applying an action didn't change the original state!
print(s)
print()

# Testing Heuristic 1
print("\nTesting heuristic 1")
problem = P.InformedProblemV1(N, blocks) 
s = problem.create_initial_state()
print(s)
print()

actions = problem.actions(s)
a = actions[5]
new_state = problem.result(s, a)
print(new_state)



# Testing Heuristic 2
print("\nTesting heuristic 2")
problem = P.InformedProblemV2(N, blocks)
s = problem.create_initial_state()
print(s)
print()

actions = problem.actions(s)
a = actions[0]
new_state = problem.result(s, a)
print(new_state)




