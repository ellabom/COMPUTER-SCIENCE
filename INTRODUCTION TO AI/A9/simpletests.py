import data_manager as DM

infile = "../Data/gamesite.txt"

data = DM.read_data(infile)

print(len(data))

parts = DM.partition_data(data, 10)

for s in parts:
    print("----")
    print(s)
    print("-----")
    print("Test set", len(parts[s]["test"]), "elements")
    print(parts[s]["test"])
    print("Train set")
    print(len(parts[s]["train"]))