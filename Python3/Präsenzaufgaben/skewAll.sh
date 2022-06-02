
#!/bin/bash

inputpath=$1
fastafiles=$inputpath/*.fasta
outputdir=$2

textfile="output.txt"  
txtoutput="${outputdir}/${textfile}"


for file in $fastafiles
do
    python3 minSkewWF1.py $file ACGT >> $txtoutput
done



