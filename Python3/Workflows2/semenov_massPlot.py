#!/usr/bin/env python3
#import matplotlib
#matplotlib.use('Agg')
import matplotlib.pyplot as plot  # you need to add before 'import matplotlib' and do matplotlib.use('Agg') to use it on the server because of XServer backend
import numpy as np
import sys
import pathlib

allmasses = []
# bar data
mass = []
for path in pathlib.Path(sys.argv[1]).iterdir():
    with open(str(path), "r") as file:
        for line in file:
            line_arr = line.strip().split('\t')
            mass.append(float(line_arr[1]))  # y-axis nums
        allmasses.append(mass.copy())
        mass.clear()

fig = plot.figure()
fig.suptitle('Masses of organisms', fontsize=14, fontweight='bold')
ax = fig.add_subplot(111)
ax.set_title('all available masses shown')
ax.set_xlabel('Organisms')
ax.set_ylabel('Masses')
ax.set_axisbelow(True)

plot.boxplot(allmasses)

output = sys.argv[2]  # output directory
outputfile = output + "/#MASSPLOT.png"
plot.savefig(outputfile)  # you can find the plot by the name #MASSPLOT.png

#the plot contains always mass of original fasta and next random fasta weights

