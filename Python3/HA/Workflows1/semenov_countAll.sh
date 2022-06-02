#!/bin/bash

input=$1
fastafiles=$input/*.fasta
output=$2

>$output	#clear output file to avoid duplicates

#use python script for all fasta files
for file in $fastafiles
do
    python3 semenov_countNt.py $file ACGT >> $output
done

#make the plot from this data and save it in out directory
python3 semenov_plot.py $output




