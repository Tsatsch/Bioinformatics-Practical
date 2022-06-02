package utils;

import seq.Sequence;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;

public class FileUtils {

    /**
     * Datei lesen und in eine Collection speichern
     * @param  reader: zB FileReader
     * @return eine Collection, die dei Zeilen der Datei enthalt
     */
    public static Collection<String> readFileToCollection (Reader reader){
        ArrayList<String> list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!=null){
                if(!line.startsWith("#")){
                    list.add(line);
                }
            }
            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return list;
    }
    /////////////Fasta lesen///////////////////
    /**
     * @param reader Reader as input
     * @return fasta header only
     */
    public static String readFasta_Header(Reader reader){
        StringBuilder builder = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!=null){
                if(line.startsWith(">")){
                    builder.append(line);
                }
            }
            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return builder.toString();
    }
    /**
     * @param reader Reader as input
     * @return fasta seq as one String
     */
    public static String readFasta_Seq(Reader reader){
        StringBuilder builder = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!=null){
                if(!line.startsWith(">")){
                    builder.append(line);
                }
            }
            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return builder.toString();
    }
    ////////////////TSV///////////////////////
    /**
     * tsv Datei einlesen und jede Zeile als String[] in einer Collection abspeichern
     * @return eine Collection, die jede Zeile als String[] abspeichert
     * Da kein Separator Zeichen als Parameter ubergeben wird, wird die Methode readTsvToCollection mit "\t" als Separator aufgerufen
     */
    public static Collection <String[]> readTsvToCollection (Reader reader){
        return readTsvToCollection (reader,"\t");
    }
    /**
     * tsv Datei einlesen und jede Zeile als String[] in einer Collection abspeichern
     * @param sep: Separator Zeichen nach dem in jeder Zeile gesplittet wird
     * @return eine Collection, die jede Zeile als String[] abspeichert
     */
    public static Collection <String[]> readTsvToCollection(Reader reader, String sep){
        ArrayList<String[]> list = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!=null){
                if(!line.startsWith("#")){
                    String[] parts = line.split(sep);
                    list.add(parts);
                }
            }
            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return list;
    }

    /////////////////////////////////////////////////

    /**
     * tsv Datei ablesen und eine HashMap<String, String> von zwei als Parameter gegebenen Spalten zuruckgeben
     * @return eine HashMap, in der fuer jede Zeile die Werte der Spalten als Paar (Key, Value) gespeichert werden
     * Da kein Separator Zeichen als Parameter, wird per Default "\t" benutzt
     */
    public static HashMap<String,String> readTsvToMap (Reader reader, int col1, int col2){
        return readTsvToMap(reader,col1,col2,"\t");
    }
    /**
     * tsv Datei ablesen und eine HashMap<String, String> speichern
     * es wird ein Default Separator benutzt und Default Werte fur col1 und col1 namloch 0 und 1 (die ersten beiden Spalten)
     */
    public static HashMap<String,String> readTsvToMap (Reader reader){
        return readTsvToMap(reader,0,1);
    }
    /**
     * tsv Datei ablesen und eine HashMap<String, String> speichern
     * es wird ein Parameter Separator benutzt und Default Werte fur col1 und col1 namloch 0 und 1 (die ersten beiden Spalten)
     */
    public static HashMap<String,String> readTsvToMap (Reader reader, String sep){
        return readTsvToMap(reader,0,1, sep);
    }
    /**
     * tsv Datei ablesen und eine HashMap<String, String> speichern
     * @param col1, col2: Spalten der tsv Datei die als Key bzw Value in der HashMap gespeichert werden sollen
     * @param sep: Separator Zeichen nach dem in jeder Zeile gesplittet wird
     * @return eine HashMap, in der fuer jede Zeile die Werte der Spalten col1 und col2 als Paar (Key, Value) gespeichert werden
     */
    public static HashMap<String,String> readTsvToMap (Reader reader, int col1, int col2, String sep){
        HashMap<String, String> map = new HashMap<>();
        try{
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine())!= null){
                if(!line.startsWith("#")){
                    String[] parts = line.split(sep);
                    if(col1 >= 0 && col2 >= 0 && col1 < parts.length && col2 <parts.length){
                        String key = parts[col1];
                        String value = parts[col2];
                        if(map.containsKey(key)){
                            System.out.println("Key "+key+" already in map.");
                        }else{
                            map.put(key,value);
                        }
                    }
                }
            }
            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return map;
    }
///////////ERWEITERUNG///////////////

    /**
     * tsv Datei einlesen und eine HashMap<A,B> mit belibeigen Typen zuruckgeben
     * @param functionA: gibt an wie der String in col1 in den entpsrechenden Typ A konvertiert werden soll
     * @param functionB: gibt an wie der String in col2 in den entpsrechenden Typ B konvertiert werden soll
     * @return: eine HashMap<A,B> mit beliebigen Typen, da kein Separator als Parameter wird die Methode readTsvToMap mit "\t" aufgerufen
     */
    public <A,B> HashMap<A,B> readTsvToMap(Reader reader, int col1, int col2, Function<String,A> functionA, Function<String,B> functionB){
        return readTsvToMap(reader,col1,col2,functionA,functionB,"\t");
    }

    /**
     * tsv Datei einlesen und eine HashMap<A,B> mit belibeigen Typen zuruckgeben
     * @param functionA: gibt an wie der String in col1 in den entpsrechenden Typ A konvertiert werden soll
     * @param functionB: gibt an wie der String in col2 in den entpsrechenden Typ B konvertiert werden soll
     * @param sep: beliebiges Separator Zeichen
     * @return: eine HashMap<A,B> mit beliebigen Typen
     */
    public static <A,B> HashMap<A,B> readTsvToMap(Reader reader, int col1, int col2, Function<String,A> functionA, Function<String,B> functionB, String sep) {
        HashMap<A, B> map = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!= null){
                if(!line.startsWith("#")){
                    String[] parts = line.split(sep);
                    if(col1 >= 0 && col2 >= 0 && col1 < parts.length && col2 <parts.length){
                        A key = functionA.apply(parts[col1]);
                        B value = functionB.apply(parts[col2]);
                        if(map.containsKey(key)){
                            System.out.println("Key "+key+" already in map.");
                        }else{
                            map.put(key,value);
                        }
                    }
                }
            }
            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return map;
    }
    /**
     *
     * @param f File from where to read fasta
     * @return id and seq of fasta
     */
    public static Sequence readFasta(File f){
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String id = null;
            StringBuffer seq = new StringBuffer();
            while ((line = br.readLine())!=null){
                if(line.startsWith(">"))
                    id = line.substring(1);
                else
                    seq.append(line);
            }
            return new Sequence(id, seq.toString());
        } catch (IOException ex){
            throw new RuntimeException("unable to read fasta file from "+ f.getAbsolutePath());
        }
    }

    /////////////main/////////////

    public static void main(String[] args) throws FileNotFoundException {
//        FileUtils rd = new FileUtils();
//        Reader reader = new FileReader("testCMD.txt");
//        HashMap<String,String> map = rd.readTsvToStringMap(reader,1,2);
//        System.out.println(map);
//        Collection<String[]>collection = rd.readTsvToCollection(reader);
//        for (String[] a: collection) {
//            System.out.println(Arrays.toString(a));
//        }

        Reader rd = new FileReader("/Users/alexander/Desktop/Escherichia_coli.fasta");
        String seq = readFasta_Seq(rd);
        Reader rd2 = new FileReader("/Users/alexander/Desktop/Escherichia_coli.fasta");
        String header = readFasta_Header(rd2);
        System.out.println("Header: "+header);
        System.out.println("Seq: "+seq.length());

    }
}

