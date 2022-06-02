import sys

pat = sys.argv[1].lower()

f = open(sys.argv[2], "r")
gen = f.read()

fail = int(sys.argv[3])  # num of missmatches allowed


# count num of difference between 2 strings
def difference(kmer, pattern):
    count = 0
    for i in range(len(kmer)):
        if kmer[i] != pattern[i]:
            count += 1
    return count


def matching(pattern, genome, fails):  # fails - max num of mismatches
   
    for i in range(len(genome) - len(pattern)):
        kmer = genome[i:i + len(pattern)]  # create kmers
        mismatch = difference(pattern, kmer)  # count difference
        if mismatch <= fails:
            print(i)



matching(pat, gen, fail)
