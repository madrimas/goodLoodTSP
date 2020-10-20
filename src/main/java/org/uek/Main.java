package org.uek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		TravellingSalesmanProblem travellingSalesmanProblem = new TravellingSalesmanProblem();
		List<List<Double>> distances = DataReader.readDistances();
		List<String> spoots = DataReader.readSpoots();
		travellingSalesmanProblem.start(spoots, distances);

		SpringApplication.run(Main.class, args);
	}
}