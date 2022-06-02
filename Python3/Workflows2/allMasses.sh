#!/bin/bash

# when running on server, make sure you switch to matlib.use("Agg") in massPlot.py
inputdir=$1
fastafiles=$inputdir/*.fasta
outputdir=$2
out=$outputdir/mass		#create 2 dir, one for mass txt, one for random fastas
out2=$outputdir/random

mkdir -p $out	#if outputdir doesn't exist, create one
mkdir -p $out2

for file in $fastafiles
do
	outputfile="$out/${file##*/}_mass.txt"			#make new .txt file in output directory for calc mass
	randomout="$out2/${file##*/}_random.fasta"		#make new .txt for random fasta
	> $outputfile
	> $randomout
	touch $outputfile
	touch $randomout

# please change mass.tsv for full path if runing of server!!!
	python3 semenov_protMass.py mass.tsv $file >> $outputfile		#save mass in mass directory
	python3 semenov_randomSeq.py mass.tsv $file >> $randomout	#make random fastas with same length and save in random
done

#count mass for random fastas
files2=$out2/*.fasta

for fastas in $files2
do
	outfile="$out/${fastas##*/}_massRND.txt"
	>$outfile
	touch $outfile
# please change mass.tsv for full path if runing of server!!!
	python3 semenov_protMass.py mass.tsv $fastas >> $outfile	#count mass for random seq and save in random/mass directory
done

#make plot
# switch to matlib.use("Agg") in massPlot.py
python3 semenov_massPlot.py $out $out	#save in $out


#progress bar :D

echo -ne '#####                     (33%)\r'
sleep 1
echo -ne '#############             (66%)\r'
sleep 1
echo -ne '#######################   (100%)\r'
echo -ne '\n'
echo done! The results can be found in $outputdir
