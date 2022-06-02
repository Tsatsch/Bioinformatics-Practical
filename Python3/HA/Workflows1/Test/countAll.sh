#!/bin/bash

inputpath=$1
fastafiles=$inputpath/*.fasta
outputdir=$2

#create a new txt file and path for nucleotide frequency data of each organism
#textfile="output.txt"  
#txtoutput="${outputdir}/${textfile}"


for file in $fastafiles
do
    python3 semenov_countNt.py $file ACGT >> $txtoutput 
done

#make the plot from this data and save it in out directory
python3 semenov_plot.py $txtoutput $outputdir



