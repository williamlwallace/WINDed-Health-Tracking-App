package seng202.group8.gui.GoogleMapsTools;

import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GoogleMapsTools {


    /**
     *
     * @param directory
     * @return
     * @throws IOException
     * Takes a directory path, loads its content as a string and copies its content in the strGooglMaps String object
     */
    public static String returnHTMLFileToString(String directory) throws IOException {
        URL urlGoogleMaps = GoogleMapsTools.class.getResource(directory);

        // Reading of the URL and create related String object
        String strGoogleMaps = "";
        BufferedReader in = new BufferedReader(
                new InputStreamReader(urlGoogleMaps.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            strGoogleMaps += (inputLine + "\n");
        in.close();
        return strGoogleMaps;
    }


    /**
     *
     * @param htmlFile
     * @param data
     * @return the parsed html file injected with the Data coordinates
     * Parses the googleMapsView.html file and injects specific variables to allow the plotting of coordinates in the
     * WebView that will render the html file.
     */
    public static String jsInjection(String htmlFile, Data data) {
        htmlFile = htmlFile.replace("&CENTERID", "lat: "
                + data.getCoordinatesArrayList().get(0).getLatitude()
                + ", lng: "
                + data.getCoordinatesArrayList().get(0).getLongitude());
        StringBuilder stringBuilder = new StringBuilder("var dataCoordVar = [ ");
        for (int i = 0; i < data.getCoordinatesArrayList().size(); i++) {
            CoordinateData coordinateData = data.getCoordinatesArrayList().get(i);
            if (i != (data.getCoordinatesArrayList().size() -1)) {
                stringBuilder.append("{lat: " + coordinateData.getLatitude()
                        + ", lng: " + coordinateData.getLongitude() + "},");
                } else {
                    stringBuilder.append("{lat: " + coordinateData.getLatitude()
                            + ", lng: " + coordinateData.getLongitude() + "}];");
                }
        }
        htmlFile = htmlFile.replace("&PREVFLIGHTCOORDID", stringBuilder.toString());

        htmlFile = htmlFile.replace("&FLIGHTPATHVARID", "dataCoordPath");
        htmlFile = htmlFile.replace("&PREVFLIGHTVAR", "dataCoordVar");
        return htmlFile;
    }



}
