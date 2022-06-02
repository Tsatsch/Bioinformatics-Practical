#!/usr/bin/env python3
import sys

# read file
f = open(sys.argv[1], "r")
options = sys.argv[2]
inputs = f.readlines()
header = ""
seq = ""

for line in inputs:
    if line.startswith(">"):  # header starts with '>', the rest is sequence
        header += line.strip()
    else:
        seq += line.strip()


def count_nuc(seqs):
    nucs = {}

    for a in range(0, len(options)):
        nucs[a] = options[a]  # make dictionary with nucleotide and his index
    a = str(seqs.count(nucs.get(0)))
    b = str(seqs.count(nucs.get(1)))
    c = str(seqs.count(nucs.get(2)))
    d = str(seqs.count(nucs.get(3)))  # count first nucs with with index 0 then 1,2,3
    res = [a, b, c, d]
    return res


res = count_nuc(seq)
result = ""
for num in res:
    result += "\t" + num
print(header[1:], end=" ")  # do not want '>' symbol before name and strip is needed because of the newline
print(result)

