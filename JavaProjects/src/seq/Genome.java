package seq;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.FileUtils;

public class Genome extends Sequence {

    ArrayList<Transcript> transcripts = new ArrayList<>();
    ArrayList<String> proteins = new ArrayList<>();     //protein sequences from transcripts
    ArrayList<String> peptides = new ArrayList<>();     //protein sequences cuts with protease
    HashMap<Double, Integer> pepMass = new HashMap<>(); //mass and times occured of peptides
    int unique;                                         //num of unique masses
    HashSet<String> uniquePepSeq = new HashSet<>();     //num of unique peptide seqs (not nessesary needed)
    double percent;                                     //percentage of unique masses of peptides


    public Genome(File fasta, File gff) { //File triplets
        super(fasta);
        initTranscripts(gff);
        //initProteins(triplets);
    }

    /**
     * This method reads gff3 and initializes transcripts that have exons with their sequences and id
     * all transcripts are stored in arraylist transcripts
     */
    private void initTranscripts(File gff3) {
        //TODO read gff3 (FileUtils!!) and initialize all transcripts
        //columns needed to extract all relevant information
        int typeCol = 2;          //you only have to consider the entries with type "mRNA" and "exon"
        int startCol = 3;
        int endCol = 4;
        int strandCol = 6;
        int attributeCol = 8;    //use the getId(attributes) method on the content of this column to extract the ID
        //you can ignore exons that belong to transcripts that are not protein coding (not of type mRNA)

        try {
            Reader reader = new FileReader(gff3);
            ArrayList<String[]> allLines = (ArrayList<String[]>) FileUtils.readTsvToCollection(reader);      //read file and split line by \t and save in arraylist
            allLines.remove(0);
            String exonSeq = "";

            for (int i = 0; i < allLines.size(); i++) {
                if (allLines.get(i)[typeCol].equals("mRNA")) {
                    Transcript transcript = new Transcript(getId(allLines.get(i)[attributeCol]));      //create new mRNA/transcript
                    for (int j = 1; j < allLines.size(); j++) {
                        if (allLines.get(j)[typeCol].equals("exon") && getParentId(allLines.get(j)[attributeCol]).equals(transcript.getId())) {
                            if (allLines.get(j)[strandCol].equals("-")) {  //check for sameStrand
                                exonSeq = getSubsequence(Integer.parseInt(allLines.get(j)[startCol]), Integer.parseInt(allLines.get(j)[endCol]));
                            } else if (allLines.get(j)[strandCol].equals("+")) {
                                exonSeq = getSubsequence(Integer.parseInt(allLines.get(j)[startCol]), Integer.parseInt(allLines.get(j)[endCol]), true);
                            }
                            Sequence exon = new Sequence(getId(allLines.get(j)[attributeCol]), exonSeq);
                            transcript.addExon(exon);   //add to arraylist of transcript
                        }
                    }
                    transcripts.add(transcript);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public ArrayList<Double> getAllPeaks(File mass, File triplets){
//        ArrayList<Double>peaks = new ArrayList<>();
//        HashMap<Character, Double> aa2mass = FileUtils.readTsvToMap(mass,(s)
//    }

        Pattern id = Pattern.compile("(?:ID=.+?:|exon_id=)(.+?);");

        private String getId (String attributes){
            return match(id, attributes);
        }

        Pattern parent = Pattern.compile("Parent=.+?:(.+?);");

        private String getParentId (String attributes){
            return match(parent, attributes);
        }

        private String match (Pattern p, String s){
            Matcher m = p.matcher(s);
            if (m.find())
                return m.group(1);
            else
                throw new RuntimeException("unable to find " + p.pattern() + " in " + s);
        }


    public static void main(String[] args) throws FileNotFoundException {
//        File fasta = new File("/Users/alexander/Desktop/Escherichia_coli.fasta");
//        File gff3 = new File("/Users/alexander/Desktop/Escherichia_coli.gff3");
//        File triplets = new File("/Users/alexander/Desktop/triplets.tsv");
//        File mass = new File("/Users/alexander/Desktop/mass.tsv");

//        CmdParser parser = new CmdParser("-fasta","-gff","-triplets","-mass");
//        parser.setObligatoryOptions("-fasta","-gff","-triplets","-mass");
//        parser.setFile("-fasta","-gff","-triplets","-mass");
//        parser.parseArguments(args);

//        CmdParser parser = new CmdParser("-fasta", "-gff");
//        parser.setObligatoryOptions("-fasta","-gff");
//        parser.setFile("-fasta","-gff");
//        parser.parseArguments(args);

       // Genome gn = new Genome(parser.getFile("-fasta"),parser.getFile("-gff"),parser.getFile("-triplets"));

//        Pattern lysin = Pattern.compile("K");
//        Pattern arginin = Pattern.compile("R");
//        gn.proteaseAttack(lysin,arginin);
//        gn.calcMass(parser.getFile("-mass"));
//
//        System.out.println(gn.unique + "\t"+ gn.percent);


/*System.out.println("Num of transcripts: " +gn.transcripts.size());
        System.out.println("Num of proteins: " +gn.proteins.size());
        System.out.println("Num of peptides: " +gn.peptides.size());
        System.out.println("Unique peptides by sequence: "+ gn.uniquePepSeq.size());
        System.out.println("Unique peptides by mass: " + gn.unique);
        System.out.println("Percentage: "+ gn.percent); */


//        Hausaufgabe: 1)


//        CmdParser cmd = new CmdParser("-fasta","-gff");
//        cmd.parseArguments(args);
        Genome gen = new Genome(new File("/Users/alexander/Desktop/Escherichia_coli.fasta"), new File("/Users/alexander/Desktop/Escherichia_coli.gff3"));

        BufferedReader br = new BufferedReader(new FileReader("/Users/alexander/Desktop/Escherichia_coli.gff3")); //cmd.getFile("-gff3"   //read the gff3 and put all lines with mRNA in the list
            Collection<String[]> cl = FileUtils.readTsvToCollection(br);
            ArrayList<String[]> list = new ArrayList<>();
            for (String[] line : cl) {
                if (line[2].contains("mRNA")) {
                    list.add(line);
                }
            }
            if (list.size() == gen.transcripts.size()) {                        //check if the lenth of this list and transcripts is the same
                System.out.println("All transcripts werde added to genome!");
            }

        //Hausaufgabe: 2 und 3) pruefe ob alle transkripte durch 3 teilbar sind (Tripplets) und
         //ein korrektes Start und Stopp codon haben -> vollstanding und richtige Position

            for(Transcript transcript: gen.transcripts){
                if(transcript.getSeq().length() % 3 != 0){
                    System.out.println("Transcript " + transcript.getId() + " with seq" + transcript.getSeq() + "\nis not devided by 3 and has lenght: " +transcript.getSeq().length());
                }
            }
            System.out.println("Done! Alle Transkripte nach Laenge geprueft");

            //hier gebe ich die ersten und letzten Codons aus
            ArrayList<String> startTr = new ArrayList<>();   //list with all first triplets
            ArrayList<String> endTr = new ArrayList<>();     //list with all last triplets

            for (Transcript trans : gen.transcripts) {
                String triplett = trans.getSeq().substring(0,3);
                String tripl = trans.getSeq().substring(trans.getSeq().length()-3);
                startTr.add(triplett);
                endTr.add(tripl);
            }

            Map<String, Integer> countBeg = new HashMap<>();    //count same triplets
            Map<String, Integer> countEnds = new HashMap<>();

            for (String start : startTr) {
                if (countBeg.containsKey(start))
                    countBeg.put(start, countBeg.get(start) + 1);
                else
                    countBeg.put(start, 1);
            }
            int sum = 0;
            for (int count: countBeg.values()) {        //anzahl aller startcodons
                sum+=count;
            }

            for (String end : endTr) {              //das gleiche fur end codons
            if (countEnds.containsKey(end))
                countEnds.put(end, countEnds.get(end) + 1);
            else
                countEnds.put(end, 1);
            }
            int sum2 = 0;                           //anzahl aller endcodons
            for (int count: countEnds.values()) {
            sum2+=count;
            }

        //print first codons
        System.out.println("First tripplets of transcripts: \n");
        System.out.printf("%s %10s %15s\n","FirstTrippl", "Number", "Percentage");
            for (String key : countBeg.keySet()){
                float persent = (float)countBeg.get(key)/sum*100;
                System.out.printf("%s %14d %15.2f\n",key, countBeg.get(key),persent);
            }
            System.out.println("\ntotal transcripts: " + sum + "\n");

        //print last codons
        System.out.println("Last tripplets of transcripts: \n");
        System.out.printf("%s %10s %15s\n","LastTrippl", "Number", "Percentage");
        for (String key : countEnds.keySet()){
            float persent = (float)countEnds.get(key)/sum*100;
            System.out.printf("%s %14d %15.2f\n",key, countEnds.get(key),persent);
        }
        System.out.println("\ntotal transcripts: " + sum2);
    }

    /*
    Losung Main: CmdParser parser = new CmdParser("-fasta","-gff3");
        parser.setFile("-fasta","-gff3");
        parser.parseArguments(args);

        Genome gen = new Genome(parser.getFile("-fasta"),parser.getFile("-gff3"));

        System.out.println(gen.transcripts.size());

        for (Transcript t: transcripts){
        if (t.getSeq % 3 != 0) System.out.println("not multiple with 3");
        String startcodon = t.getSeq.substring(0,3);
        if (!startCodon.equals("ATG")){
        if (startCodon.equals("GTG") ||startCodon.equals("TTG") ){
        System.out.println("uses alternative codon" +t.getId);
         }
         else{
         System.out.println("does not start with a starcodon");
         }
        }

        int l = t.getSeq.length();
        String lastcodon = t.getSeq.substring(l-3,l);
        if (!(startCodon.equals("TAA") ||startCodon.equals("TAG")||startCodon.equals("TGA"))){
         System.out.println("t.getId doesnt end with a stopcodon");
     */
    }







