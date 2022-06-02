#!/usr/bin/env python3
import sys

# read file
f = open(sys.argv[1], "r")
seq = f.read()


def count_nuc(seqs):
    a = seqs.count("A")
    c = seqs.count("C")
    g = seqs.count("G")
    t = seqs.count("T")
    if a + c + g + t == len(seqs):
        print("TRUE")
    else:
        print(f"FALSE {a+c+g+t}!={len(seqs)}")
    print("A\tC\tG\tT")
    print(f"{a}\t{c}\t{g}\t{t}")


count_nuc(seq)
