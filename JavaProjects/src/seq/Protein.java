package seq;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Protein extends Sequence {
    static String aas = "GAVLIMFWPSTCYNQDEKRHOU";


    public Protein(String id, String seq) {
        super(id, seq);
        checkProtSequence(seq);
    }

    //check if seq consists of amino acids only
    private void checkProtSequence(String seq){
        for (int i = 0; i <seq.length() ; i++)
            if(!isAminoAcid(seq.charAt(i))) throw new RuntimeException("non amino acid character: "+seq.charAt(i));
    }
    public static boolean isAminoAcid(char a){ return aas.indexOf(a)>=0;}

    /**
     * Cut the proteins with a nuclease
     * @param cut Pattern of nuclease where to cut proteines
     * @return set of cutted protein sequences
     */
    public ArrayList<Protein>cut (Pattern cut) {
        ArrayList<Protein> parts = new ArrayList<>();
        Matcher m = cut.matcher(getSeq());
        int lastCut = 0;
        int partId = 0;
        while(m.find()){
            parts.add(new Protein(getId()+"_"+partId,getSubsequence(lastCut+1,m.end(),true)));
            partId++;
            lastCut = m.end();
        }
        //add tail peptide after last cut position if cut was not at the end
        if(lastCut!=getSeq().length()){
            parts.add(new Protein(getId()+"_"+partId,getSubsequence(lastCut+1, getSeq().length(), true)));
        }
        return parts;
    }
    /**
     * Calculate mass of protein sequence
     * @param aa2mass protein seq
     * @return Double mass (sum)
     */
    public double calcMass(HashMap<Character,Double> aa2mass){
        double mass = 0.0;
        for (int i = 0; i <getSeq().length() ; i++) {
            Double aamass = aa2mass.get(getSeq().charAt(i));
            if(aamass == null) throw new RuntimeException("no mass given for "+getSeq().charAt(i));
            mass+=aamass;
        }
        return mass;
    }
    public  ArrayList<Double> getSpectrum (HashMap<Character, Double> aa2mass){
        ArrayList<Double> peaks = new ArrayList<>();
        for(Protein peptide : cut(Pattern.compile("K|R"))){
            peaks.add(Math.round(100_000*peptide.calcMass(aa2mass))/100_000.0);
        }
        return peaks;
    }
}




