#!/usr/bin/env python3
import sys
import matplotlib.pyplot as plot

# read file
file = sys.argv[1]
f = open(file, "r")
output = sys.argv[2]
genomfile = f.readlines()
header = ""
genome = ""

for line in genomfile:
    if line.startswith(">"):  # header starts with '>'
        header += line.strip()
    else:
        genome += line.strip()

# initialize the plot
plot.title("Minimal Skew Problem")
plot.xlabel("position")
plot.ylabel("skew")


def min_skew_problem(gen):
    c = 0
    g = 0
    min_skew = 0
    skew_list = []
    index = 0
    y = []
    for i in gen:  # count Cytosine and Guanine
        index += 1
        if i == 'C':
            c += 1
        if i == 'G':
            g += 1
        skew = g - c  # per definition is skew the difference between G and C
        y.append(skew)
        if skew < min_skew:  # search for minima
            skew_list = [index]
            min_skew = skew
        if skew == min_skew and index not in skew_list:
            skew_list.append(index)  # add to list
    plot.plot(y)
    return skew_list


print(min_skew_problem(genome))
name = "/" + header[1:]+".png"
plot.savefig(output+name)
