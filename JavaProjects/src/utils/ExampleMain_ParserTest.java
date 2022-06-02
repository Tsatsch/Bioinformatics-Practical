package utils;

import java.io.File;

public class ExampleMain_ParserTest {
    public static void main(String[] args) {

                CmdParser cmd = new CmdParser("-mandatory","-file");
                cmd.setObligatoryOptions("-mandatory");
                cmd.parseArguments(args);
                String mandatory = cmd.getValue("-mandatory");
                File file = new File(cmd.getValue("-file"));
                System.out.println("mandatory -> "+mandatory);
                System.out.println("file -> "+file.getAbsolutePath());
            }
        }

        //input: java test.ExampleMain -mandatory nonoptional -file /Users/alexander/Desktop/bioinfo-tutorium/file1.txt

    //output: mandatory -> nonoptional
        //    file -> /Users/alexander/Desktop/bioinfo-tutorium/file1.txt




