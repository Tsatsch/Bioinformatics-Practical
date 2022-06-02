# Nukleotidhäufigkeiten (Workflows1)
A program that gets a directory with fasta and other txt files with nucleotides as first argument creates a plot representing frequency of nucleotides of each organism. This plot and a output.txt file with raw data of counting nucleotides is saved in a directory that the program gets as second argument.

My program is saved on cip server in workflows1 directory, there you will also find the out directory. Before run the program please delete everything in the out directory first in case there is something (or it will generate the duplicated output).

# Usage

## using local:

bash semenov_countAll.sh <directory with fastas of organisms> <output directory>

## using on server:
Matplotlib uses per default the XServer Backend. We have to edit the plot.py script to be able to use it on server.

You need to make following editions: 
instead of this: 
```python
import matplotlib.pyplot as plot 
```
do this:
```python
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plot 
```

# Output 
You will have in your output directory a output.txt file with raw data of organisms with their num of A, C, G, T separated by tabs and with name if organisms at the beginning. The plot created of this data is saved in your output directory and has the name Nucleotides.png.
To open the Nucleotides.png you need to log into ssh via ssh -X. Then go to out directory and do feh Nucleotides.png.

## Attention
Be careful, if you have played the countAll.sh script and run it once again, the output.txt will be added the organisms once again, so the plot will be also have duplicated. Make sure you delete both files before playing the bash script again.

 
