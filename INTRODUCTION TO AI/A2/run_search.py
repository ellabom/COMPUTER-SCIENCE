# CMPT 317: Solver script for solving Colored Tile problems

# Copyright (c) 2016-2022 Michael C Horsch, Jeffrey R Long
# Department of Computer Science, University of Saskatchewan

# This file is provided solely for the use of CMPT 317 students.  Students are permitted
# to use this file for their own studies, and to make copies for their own personal use.

# This file should not be posted on any public server, or made available to any party not
# enrolled in CMPT 317.

# This implementation is provided on an as-is basis, suitable for educational purposes only.
#


import UninformedSearch as BlindSearch
import InformedSearch as Search
import simpleTetris as P

import gc as gc
import sys as sys
import time as time

# process the command line arguments
print(sys.argv)

if len(sys.argv) < 3:
    print('usage: python', sys.argv[0], 'problemfile timelimit')
    sys.exit()

file = open(sys.argv[1], 'r')
timelimit = int(sys.argv[2])



strategies = ['AStar0', 'AStarH1', 'AStarH2']

# read the problem first
examples = []
file = open(sys.argv[1], 'r')
gridsize = int(file.readline())

line = file.readline()
blocks = {}
while line:
    parts = line.strip().split(" ")
    count = int(parts[0])
    block = parts[1].split("x")
    block = (int(block[0]), int(block[1]))
    blocks[block] = count
    line = file.readline()

file.close()

global_start = time.time()

print()
print("Finding solutions for grid of size", gridsize, "with blocks: ", blocks)
print("***")
print()

# try all the solvers
for solver in strategies:

    gc.collect()  # clean up any allocated memory now, before we start timing stuff

    # determine which of the implemented heuristics to use
    if solver == 'AStar0':
        problem = P.InformedProblem(gridsize, blocks)
        
    elif solver == 'AStarH1':
        problem = P.InformedProblemV1(gridsize, blocks)

    elif solver == 'AStarH2':
        problem = P.InformedProblemV2(gridsize, blocks)

    else:
        print('Unknown solver:', solver, '-- terminating!')
        sys.exit(1)
    
    # run the search
    s = problem.create_initial_state()
    searcher = Search.InformedSearch(problem, timelimit=timelimit)
    answer = searcher.AStarSearch(s)

    # process the result of search
    if answer.success:
        checked = (problem.is_goal(answer.result.state) )

        # print the solution
        
        print("Solution found by",solver)
        answer.result.display_steps()
        print()
        print("Solution depth:", answer.result.depth)

    else:
        print("Could not find solution with",solver)
        print("Solution depth: N/A")
        
    print("Time used (secs):", answer.time)
    print("Space used (nodes):", answer.space)

    print("\n")
    
global_finish = time.time()
print('Took', global_finish - global_start, 'seconds')