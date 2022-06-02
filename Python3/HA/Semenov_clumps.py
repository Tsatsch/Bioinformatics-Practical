#!/usr/bin/env python3
import argparse as ap

# create parser
parser = ap.ArgumentParser()
parser.add_argument("-g", type=str, required=True)
parser.add_argument("-k", type=int, required=True)
parser.add_argument("-L", type=int, required=True)
parser.add_argument("-t", type=int, required=True)

# parse arguments and open genome file
args = parser.parse_args()

genome_file = args.g
f = open(genome_file, 'r')
genome = f.read()

k_mer_len = args.k
clump_len = args.L
times = args.t


# save and count k-mers from genome
def save_kmers(genome, k_mer_len):
    counts = {}

    for i in range(len(genome) - k_mer_len):
        kmer = genome[i:i + k_mer_len]
        if kmer in counts:
            counts[kmer] += 1
        else:
            counts[kmer] = 1
    return counts


# search for k-mers that appear more than t-times in clumps
def search_clumps(genome, k_mer_len, L, t):
    res = []
    frequency = {}
    for i in range(len(genome) - L):
        clump = genome[i:i + L]
        counts = save_kmers(clump, k_mer_len)
        for k in counts:
            if counts[k] >= t:
                frequency[k] = counts[k]

    for mers in frequency.keys():
        res.append(mers)
    return res


print(search_clumps(genome, k_mer_len, clump_len, times))
