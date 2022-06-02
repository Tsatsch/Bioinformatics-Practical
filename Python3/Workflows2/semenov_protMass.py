#!/usr/bin/env python3
import sys

f = open(sys.argv[1], "r")  # mass.tsv
fp = open(sys.argv[2], "r")  # fasta


# saves mass.tsv and fasta in two dictionaries and calculates for each seq its mass
# output: {Header : mass of seq}
def calc_mass():
    mass = {}
    for line in f.readlines():
        arr = line.split("\t")
        mass[arr[0]] = float(arr[1].strip())  # save mass = {Met : 3.9101}

    fasta = {}
    header = ""
    seq = ""
    for i in fp.readlines():
        if i.startswith(">"):
            array = i.split(" ")
            header = array[0].strip()
            seq = ""
        else:
            seq += i.strip()
        fasta[header] = seq  # save fasta = {Header : proteine sequence}

    result = {}
    for key, value in fasta.items():
        res = 0
        for a in value:
            if mass.keys().__contains__(a):
                res += mass.get(a)
        result[key] = res.__round__(3)  # {Header : mass of proteine sequence}
    return result


output = calc_mass()
# print output in right format
for key, value in output.items():
    print(f"{key}\t{value}")


