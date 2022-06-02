#!/usr/bin/env python3
import sys

seq = sys.argv[1]
k = int(sys.argv[2])
counter = 0
k_mers = []

for i in range(len(seq)-k):
        k_mers.append(seq[i:i+k])

for i in k_mers:
    curr_frequency = k_mers.count(i)
    if curr_frequency > counter:
            counter = curr_frequency

    print(k_mers[counter])



