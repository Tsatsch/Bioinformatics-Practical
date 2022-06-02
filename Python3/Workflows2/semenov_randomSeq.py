#!/usr/bin/env python3
import sys
import random

f = open(sys.argv[1], "r")  # mass.tsv
fp = open(sys.argv[2], "r")  # fasta


# saves mass.tsv and fasta in two dictionaries and calculates for each seq its mass
# output: {Header : mass of seq}
def seq_length():
    mass = {}
    aa = []
    for line in f.readlines():
        arr = line.split("\t")
        mass[arr[0]] = float(arr[1].strip())  # save mass = {Met : 3.9101}
        aa.append(arr[0])

    fasta = {}
    header = ""
    seq = ""
    for i in fp.readlines():
        if i.startswith(">"):
            array = i.split(" ")
            header = array[0]
            seq = ""
        else:
            seq += i.strip()
        fasta[header] = len(seq)  # save fasta = {Header : len of seq}

    return fasta, aa


output, amino = seq_length()
# generete random seq with len of seq
res = {}
random_seq = ""
for key in output.keys():
    for i in range(output.get(key)):        #length should be same as in original fasta
        random_seq += random.choice(amino)  #chose random amino and add to seq
    res[key] = random_seq
    random_seq = ""

for a, b in res.items():
    print(a, end="\n")
    print(b)

