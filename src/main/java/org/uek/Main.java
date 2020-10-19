package org.uek;

import io.javalin.Javalin;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		TravellingSalesmanProblem travellingSalesmanProblem = new TravellingSalesmanProblem();
		List<List<Double>> distances = DataReader.readDistances();
		List<String> spoots = DataReader.readSpoots();
		travellingSalesmanProblem.start(spoots, distances);
	}
}