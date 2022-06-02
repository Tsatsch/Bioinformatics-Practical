package utils;


import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class CmdParser {

    /**
     * Geparste Parameter werden in einer HashMap als key value Paare gespeichert (zB key = "-int" , value = "10")
     */

    private HashMap<String, String> parameterMap;
    private HashSet<String> allowedOptions;
    private HashSet<String> obligatoryOptions;  //non-optional options

    //Erweiterung

    private HashSet<String> intOptions;
    private HashSet<String> doubleOptions;
    private HashSet<String> fileOptions;

    ///////initializierung von validen Optionen////////////
    public CmdParser(String... options) {
        this.allowedOptions = new HashSet<>();
        this.allowedOptions.addAll(Arrays.asList(options));
        this.obligatoryOptions = new HashSet<>();
        this.parameterMap = new HashMap<>();

        this.intOptions = new HashSet<>();
        this.doubleOptions = new HashSet<>();
        this.fileOptions = new HashSet<>();

    }

    /**
     * @param options ist ein String[] wird als Menge gespeichert
     */
    public void setObligatoryOptions(String... options) {
        this.obligatoryOptions.addAll(Arrays.asList(options));
    }

    /**
     * Methode parst die Kommandozeilenparameter aus dem String Array
     *
     * @param args und speichert sie in der HashMap parameterMap
     */
    public void parseArguments(String[] args) {
        for (int i = 0; i < args.length; i+=2) {
            if (args[i].startsWith("-")) {
                if (!allowedOptions.contains(args[i])) {
                    throw new RuntimeException("Parameter not allowed: " + args[i]);
                } else if (i + 1 == args.length) {
                    throw new RuntimeException("Last parameter has not value: " + args[i]);
                } else if (args[i + 1].startsWith("-")) {
                    throw new RuntimeException("Parameter without value: " + args[i]);
                } else if (parameterMap.containsKey(args[i])) {
                    throw new RuntimeException("Parameter already in map: " + args[i]);
                } else {
                    parameterMap.put(args[i], args[i + 1]);
                }
            } else {
                throw new RuntimeException("Arguments have to start with '-'");
            }
        }
        // testen ob die obligatory options enthalten sind
        for (String option : obligatoryOptions) {
            if (!parameterMap.containsKey(option)) {
                throw new RuntimeException("Obligaotry option missing: " + option);
            }
        }
    }

    /**
     * Festlegen welche Optionen vom Typ int/double oder File sein duerfen
     */

    public void setInt(String... intoptions) {
        this.intOptions.addAll(Arrays.asList(intoptions));
    }

    public void setDouble(String... doubleoptions) {
        this.doubleOptions.addAll(Arrays.asList(doubleoptions));
    }

    public void setFile(String... fileoptions) {
        this.fileOptions.addAll(Arrays.asList(fileoptions));
    }

    /**
     * @param option - key in der HashMap
     * @return String value aus der HashMap
     */
    public String getValue(String option) {
        return parameterMap.getOrDefault(option, null);
    }

    /**
     * Gibt den Wert einer intOption aus der HashMap zuruck
     */
    public Integer getInt (String option){
        if (!parameterMap.containsKey(option))return null;
        else if (intOptions.contains(option)) return Integer.parseInt(getValue(option));
        else throw new RuntimeException("Option value is not an Integer: " +  option);
    }
    public Double getDouble (String option){
        if (!parameterMap.containsKey(option))return null;
        else if (doubleOptions.contains(option)) return Double.parseDouble(getValue(option));
        else throw new RuntimeException("Option value is not an Double: " +  option);
    }
    public File getFile (String option){
        if (!parameterMap.containsKey(option))return null;
        else if (fileOptions.contains(option)) return new File (getValue(option));
        else throw new RuntimeException("Option value is not an Integer: " +  option);
    }



}







