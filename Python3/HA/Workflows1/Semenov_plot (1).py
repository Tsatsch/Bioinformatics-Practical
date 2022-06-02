#!/usr/bin/env python3
#import matplotlib
#matplotlib.use('Agg')
import matplotlib.pyplot as plot #you need to add before 'import matplotlib' and do matplotlib.use('Agg') to use it on the server because of XServer backend
import numpy as np
import sys

f = open(sys.argv[1], "r")
table = f.readlines()


labels = []  # x-axis naming

# bar data
num_of_A = []
num_of_C = []
num_of_G = []
num_of_T = []

i = 0
for line in table:
    labels.append("O." + str(i))  # naming of organisms too long, so use O.<Number> instead
    i += 1
    line_arr = line.strip().split('\t')

    num_of_A.append(int(line_arr[1])) #append data about A,C,G,T of each organism  
    num_of_C.append(int(line_arr[2]))
    num_of_G.append(int(line_arr[3]))
    num_of_T.append(int(line_arr[4]))

# general info about bar
x = np.arange(len(labels))
width = 0.20

# plot construction
fig, ax = plot.subplots()

rec1 = ax.bar(x - 1.5 * width, num_of_A, width, label='A')
rec2 = ax.bar(x - 0.50 * width, num_of_C, width, label='C')
rec3 = ax.bar(x + 0.50 * width, num_of_G, width, label='G')
rec4 = ax.bar(x + 1.5 * width, num_of_T, width, label='T')

# labels, title etc
ax.set_ylabel('Num of nucleotides')
ax.set_title('Nucleotides frequency of organisms')
ax.set_xticks(x)
ax.set_xticklabels(labels)
ax.legend()

fig.tight_layout()
plot.show()
#plot.savefig(output+ "Nucleotides.png") #you can find the plot by the name Nucleotides.png


