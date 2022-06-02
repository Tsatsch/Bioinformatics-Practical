package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.function.Function;

public class TesterKlasse {
    public static void main(String[] args) throws FileNotFoundException {
        /*  wenn man in der methode fileToArrayList File statt String als Parameter benutzt (analog fuer alle anderen Methoden)
    File file = new File("/Users/alexander/Desktop/bioinfo-tutorium/file1.txt");
    ArrayList<String>arrayList1 = ReadFile.fileToArrayList(file)

            using a String pathname as a parameter (analog fur alle anderen Methoden)
    ArrayList<String> arrayList2 = ReadFile.fileToArrayList("/Users/alexander/Desktop/bioinfo-tutorium/file1.txt");
    System.out.println(arrayList2); */
        /**
         * Man kann alle subclasses von Reader benutzen (habe BufferReader genommen, weil ich den personlich am meisten benutze)
         */
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/alexander/Desktop/bioinfo-tutorium/file1.txt"));
//        ArrayList<String> arrayList = ReadFile.fileToArrayList(reader);
//        System.out.println(arrayList);
//
//        BufferedReader reader1 = new BufferedReader(new FileReader("/Users/alexander/Desktop/bioinfo-tutorium/file2.tsv"));
//        ArrayList<String[]> arrayListTSV = ReadFile.readTSV(reader1);
//        for (String[] strings : arrayListTSV) {
//            System.out.println(Arrays.toString(strings));
//        }
//
//        BufferedReader reader2 = new BufferedReader(new FileReader("/Users/alexander/Desktop/bioinfo-tutorium/file2.tsv"));
//        HashMap<String,String> map = ReadFile.readColumnsTSV(reader2,1,3,"\t");
//        System.out.println(map);

        Function<String,Integer> func1 = Integer::parseInt;
        Function<String,Double> func2 = Double::parseDouble;
        BufferedReader reader = new BufferedReader(new FileReader("/Users/alexander/Desktop/file34.tsv"));
        //HashMap<Integer, Double> map = ReadFile.hashMap("/Users/alexander/Desktop/file34.tsv",1,2,func1,func2);
       // System.out.println(map);
    }
}

