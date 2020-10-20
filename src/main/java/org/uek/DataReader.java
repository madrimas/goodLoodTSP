package org.uek;

import com.google.gson.Gson;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {

    private static String distancesPath = "data/distances.json";
    private static String spootsPath = "data/spoots.json";

    public static List<List<Double>> readDistances() {
        Gson gson = new Gson();

        try {
            InputStream resource = new ClassPathResource(DataReader.distancesPath).getInputStream();
            String text = new BufferedReader(
                    new InputStreamReader(resource, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            List<List<Double>> object = gson.fromJson(text, List.class);

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

        try {
            InputStream resource = new ClassPathResource(DataReader.spootsPath).getInputStream();
            String fileAsString = new BufferedReader(
                    new InputStreamReader(resource, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            List<String> spoots = gson.fromJson(fileAsString, List.class);

            return spoots;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
