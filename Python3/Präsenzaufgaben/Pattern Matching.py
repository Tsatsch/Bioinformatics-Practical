#!/usr/bin/env python3
import sys

pattern = sys.argv[1]
f = open(sys.argv[2],'r')
genome = f.read()
start_pos = []
for i in range(0,len(genome)-len(pattern)):
    if genome[i:i + len(pattern)+1] == pattern:
        start_pos.append(i)
print(start_pos)


