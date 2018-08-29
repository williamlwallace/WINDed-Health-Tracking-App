package seng202.group8.Parser;

import com.opencsv.CSVReader;

import java.io.Reader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Parser {

    private ArrayList<ArrayList<String>> acceptedValues = new ArrayList<ArrayList<String>>();
    private ArrayList<String> walk = new ArrayList<String>(Arrays.asList("walk"));
    private ArrayList<String> hike = new ArrayList<String>(Arrays.asList("hike", "hiking"));
    private ArrayList<String> run = new ArrayList<String>(Arrays.asList("run"));
    private ArrayList<String> climb = new ArrayList<String>(Arrays.asList("climb"));
    private ArrayList<String> bike = new ArrayList<String>(Arrays.asList("bike", "biking"));
    private ArrayList<String> swim = new ArrayList<String>(Arrays.asList("swim", "scuba", "diving"));
    private ArrayList<String> waterSports = new ArrayList<String>(Arrays.asList("kayak", "canoe", "raft", "surf"));

    public void Parser(String filename) throws Exception {
        // hello
        CSVReader csvReader = new CSVReader(new FileReader(filename));
        String[] line = readLine(csvReader);
        while (line != null) {
            parseActivity(line);
            line = readLine(csvReader);
        }
        csvReader.close();
    }
    private void parseActivity(String[] line) {
        System.out.print(line);
    }

    private String[] readLine(CSVReader csvReader) throws Exception {
        //ArrayList<String> list = new ArrayList<String>();
        String[] line;
        line = csvReader.readNext();
        return line;
    }


}
