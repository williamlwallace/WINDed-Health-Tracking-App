package seng202.group8.parser;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Parser {

    private ArrayList<ArrayList<String>> acceptedValues = new ArrayList<ArrayList<String>>();
    private ArrayList<String> walk = new ArrayList<String>(Arrays.asList("walk"));
    private ArrayList<String> hike = new ArrayList<String>(Arrays.asList("hike", "hiking"));
    private ArrayList<String> run = new ArrayList<String>(Arrays.asList("run"));
    private ArrayList<String> climb = new ArrayList<String>(Arrays.asList("climb"));
    private ArrayList<String> bike = new ArrayList<String>(Arrays.asList("bike", "biking"));
    private ArrayList<String> swim = new ArrayList<String>(Arrays.asList("swim", "scuba", "diving"));
    private ArrayList<String> waterSports = new ArrayList<String>(Arrays.asList("water sport","kayak", "canoe", "raft", "surf"));

    /**
     * Receives a filename and starts reading the data activity by activity
     * @param filename
     * @throws Exception
     */
    public Parser(String filename) throws Exception {
        acceptedValues.add(walk);
        acceptedValues.add(hike);
        acceptedValues.add(run);
        acceptedValues.add(climb);
        acceptedValues.add(bike);
        acceptedValues.add(swim);
        acceptedValues.add(waterSports);
        CSVReader csvReader = new CSVReader(new FileReader(filename));
        String[] line = readLine(csvReader);
        //String[] myEntries = csvReader.readAll();
        while (line != null) {
            line = parseActivity(line, csvReader);
            //line = readLine(csvReader);
        }
        csvReader.close();
    }
    /**
     * Receives a activity and collates the data from it
     * @param line
     * @throws Exception
     */
    private String[] parseActivity(String[] line, CSVReader csvReader) throws Exception {
        //System.out.print(line[0]);
        String activityName = line[1];
        System.out.println(activityName);
        String activityType = "";
        activityName = activityName.toLowerCase();
        // Check if it is a walk
        for (int place = 0; place < acceptedValues.size(); place++) {
            for (int i = 0; i < acceptedValues.get(place).size(); i++) {
                if (activityName.contains(acceptedValues.get(place).get(i))) {
                    activityType = acceptedValues.get(place).get(0);
                }
            }
        }
        if (activityType.equals("")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("This activity, '"+line[1]+"', doesn't match any of our catagorys, please select the appropriate one:\n1: Walk\n2: Hike\n3: Run\n4: Climb\n5: Bike\n6: Swim\n7: Water Sports\n");
            String selection = scanner.next();
            while (!selection.equals("1") && !selection.equals("2") && !selection.equals("3") && !selection.equals("4") && !selection.equals("5") && !selection.equals("6") && !selection.equals("7")) {
                System.out.print("You must enter a number between 1 and 7\n");
                selection = scanner.next();
            }
            int activityNum = Integer.parseInt(selection) - 1;
            System.out.print("Please enter a phrase from '"+line[1]+"' so we can recognise this activities category next time\n");
            String phrase = scanner.next();
            while (!activityName.contains(phrase.toLowerCase())) {
                System.out.print("You must enter a phrase from '"+line[1]+"'\n");
                phrase = scanner.next();
            }
            acceptedValues.get(activityNum).add(phrase.toLowerCase());
            activityType = acceptedValues.get(activityNum).get(0);
        }
        System.out.println(activityType);
        line = readLine(csvReader);
        while (!(line[0].equals("#start"))) {
            System.out.println(line[1]);
            line = readLine(csvReader);
        }
        return line;
    }

    /**
     * Receives a single line and returns it.
     * @param csvReader
     * @throws Exception
     */
    private String[] readLine(CSVReader csvReader) throws Exception {
        //ArrayList<String> list = new ArrayList<String>();
        String[] line;
        line = csvReader.readNext();
        return line;
    }

    public static void main(String[] args) throws Exception {
        Parser parserTest =  new Parser("seng202_2018_example_data.csv");
    }

}
