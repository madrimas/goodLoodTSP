package org.uek;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataReader {

    private static String distancesPath = "src/main/resources/data/distances.json";
    private static String spootsPath = "src/main/resources/data/spoots.json";

    public static List<List<Double>> readDistances() {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(DataReader.distancesPath))
        {
            List<List<Double>> object = gson.fromJson(reader, List.class);

            System.out.println(object);

            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> readSpoots() {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(DataReader.spootsPath))
        {
            List<String> spoots = gson.fromJson(reader, List.class);

            System.out.println(spoots);
            return spoots;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
