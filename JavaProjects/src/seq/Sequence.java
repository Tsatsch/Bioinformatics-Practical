package seq;

import utils.FileUtils;
import java.io.*;
import java.util.Collection;

public class Sequence {
    private String id;
    private String seq;

    //TODO add constructors, getter and setters
    /**
    First constructor reads fasta and initializes the seq of genome and its id
     */
    public Sequence(File fasta) {
        StringBuilder builder = new StringBuilder();
        try {
            Reader reader = new FileReader(fasta);
            Collection<String> fastaIn = FileUtils.readFileToCollection(reader); //using method from utils.Readfile
            for (String line : fastaIn) {
                if (line.startsWith(">")) {
                    id = line;
                } else {
                    builder.append(line);
                }
            }
            seq = builder.toString();   //concatenating all lines from the collection into a string
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    Second constructor makes a new sequence (not of whole genome) with its id and seq
     - used for exons
     */
    public Sequence(String id, String seq) {
        this.seq = seq;
        this.id = id;
    }
    /**
     Third constructor makes a new sequence with id only
     - used for transcripts
     */
    public Sequence(String id){
        this.id = id;
    }

    public String getId() {                         //getters
        return id;
    }

    public String getSeq() {
        return seq;
    }

    public void setId(String id) {                 //setters
        this.id = id;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    /**
     * @param start      start position of subsequence, with sequence numbering starting at 1
     * @param end        end position of subsequence, with sequence numbering starting at 1
     * @param sameStrand should the subsequence be extracted from the same strand as this sequence
     * @return subsequence from start to end
     */
    public String getSubsequence(int start, int end, boolean sameStrand) {
        String stringsub = getSeq().substring(start - 1, end).toUpperCase();    //uppercase to avoid big small chars errors
        StringBuilder builder = new StringBuilder();
        if (start >= 1 && end >= 1 && end > start) {
            if (sameStrand) {
                for (int i = 0; i < stringsub.length(); i++) {
                    if (stringsub.charAt(i) == 'A') builder.append("A");
                    if (stringsub.charAt(i) == 'T') builder.append("U");
                    if (stringsub.charAt(i) == 'G') builder.append("G");
                    if (stringsub.charAt(i) == 'C') builder.append("C");
                }
                return builder.toString();
            } else {
                return getSubsequence(start, end);
            }
        }
        return "Non valid parameters";
    }

    public String getSubsequence(int start, int end) {
        String substring = getSeq().substring(start - 1, end).toUpperCase();
        StringBuilder builder = new StringBuilder();
        if (start >= 1 && end >= 1 && end>start ) {
            for (int i = 0; i < substring.length(); i++) {
                if (substring.charAt(i) == 'A') builder.append("U");
                if (substring.charAt(i) == 'T') builder.append("A");
                if (substring.charAt(i) == 'G') builder.append("C");
                if (substring.charAt(i) == 'C') builder.append("G");
            }
        }
        return builder.reverse().toString();        //make complementary and reverse
    }
}

