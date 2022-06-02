package seq;

import java.util.ArrayList;
import java.util.HashMap;

public class Transcript extends Sequence {
    private ArrayList<Sequence> exons = new ArrayList<>();

    /**
     * Constructor to create a new Transcript with its id  and
     * Arraylist in case there are more exons in one transcript
     */
    public Transcript(String id) {
        super(id);
    }

    public void addExon(Sequence exon) {    //add Sequence/exon  to arraylist
        //TODO complete code
        exons.add(exon);
    }

    //this function should overwrite the getter of seq in Sequence
    public String getSeq() {
        //TODO complete code: paste together the sequences of the exons (you can assume that they are already in the right order)
        StringBuilder builder = new StringBuilder();

        for (Sequence s : exons) {
            builder.append(s.getSeq());
        }
        return builder.toString();
    }
    public Protein translate(HashMap<String, Character> triplet2aa){
        StringBuffer aaseq = new StringBuffer();
        for (int i = 0; i <getSeq().length()-2 ; i+=3) {
            Character aa = triplet2aa.get(getSeq().substring(i,i+3).replace("T","U"));
            if(aa==null || aa== '$') break;
            aaseq.append(aa);
        }
        return new Protein(getId(),aaseq.toString());
    }
}


