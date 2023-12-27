# EMMANUELLA EYO 11291003 EEE917
import sys

def parse_input_file(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()
        num_days = int(lines[0])
        class_rosters = [line.strip().split() for line in lines[1:]]
        return num_days, class_rosters

def get_variables(class_rosters):
    variables = {roster[0] for roster in class_rosters}
    return variables

def get_domains(num_days, variables):
    domains = {variable: set(range(1, num_days+1)) for variable in variables}
    return domains


def generate_constraints(class_rosters, num_days):
    constraints = {}
    for i, roster_i in enumerate(class_rosters):
        for j in range(i+1, len(class_rosters)):
            roster_j = class_rosters[j]
            common_students = set(roster_i[1:]) & set(roster_j[1:])
            if len(common_students) > 0:
                instructor_i = roster_i[0]
                instructor_j = roster_j[0]
                days = {(day1, day2) for day1 in range(1, num_days+1) for day2 in range(1, num_days+1) if day1 != day2}
                constraint_pair = f"{instructor_i}_{instructor_j}"
                constraints[constraint_pair] = days
    return constraints



if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python a4q2.py <input_file>")
        sys.exit(1)
    input_file = sys.argv[1]

    num_days, class_rosters = parse_input_file(input_file)
    variables = get_variables(class_rosters)
    domains = get_domains(num_days, variables)
    constraints = generate_constraints(class_rosters,num_days)

    print(f"Variables: {variables}")
    print(f"Domains: {domains}")
    print("Contraints:")
    for instructor_pair, disallowed_days in constraints.items():
        print(f"{instructor_pair}: {disallowed_days}")

    print("\n\n")


