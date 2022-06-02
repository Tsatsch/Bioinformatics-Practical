#!/bin/bash

inputpath=$1
fastafiles=$inputpath/*.fasta
output=$2

#clear output file to avoid dublicates
> output

for file in $fastafiles
do
    python3 Semenov_countNt.py $file ACGT >> $output 
done

#make the plot from this data and save it 
python3 Semenov_plot.py $output 



