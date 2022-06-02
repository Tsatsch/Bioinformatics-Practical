#!/usr/bin/env python3
import sys

pattern = sys.argv[1].upper()   #make uppercase
rev_pattern = pattern[::-1]    #reverse pattern
rev_compl = ''

for nuc in rev_pattern:
    if nuc == 'A':
        rev_compl+='T'
    if nuc == 'T' or nuc == 'U':
        rev_compl+='A'
    if nuc == 'G':
        rev_compl+='C'
    if nuc == 'C':
        rev_compl+='G'
    else: raise Exception (f"{nuc} is not a nuc")

print(rev_compl)
