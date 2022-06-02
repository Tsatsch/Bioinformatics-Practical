#!/bin/bash
while read line
do echo $line
done < $1 | cut -d "," -f 1 | sort |uniq -c>&1

