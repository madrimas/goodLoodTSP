package org.uek.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uek.DataReader;
import org.uek.TravellingSalesmanProblem;
import org.uek.graph.GraphData;

import java.util.List;

@RestController
public class GraphController {

    @GetMapping("/graph")
    @CrossOrigin(origins = "*")
    public GraphData getGraphData() {
        TravellingSalesmanProblem travellingSalesmanProblem = new TravellingSalesmanProblem();
        List<List<Double>> distances = DataReader.readDistances();
        List<String> spoots = DataReader.readSpoots();
        return travellingSalesmanProblem.start(spoots, distances);

    }
}
