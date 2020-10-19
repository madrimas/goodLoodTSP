package org.uek;

import com.google.gson.Gson;
import org.uek.graph.Edge;
import org.uek.graph.GraphData;
import org.uek.graph.Node;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Exhaustive search - checks all hamiltonian cycles and returns the cycle with the smallest sum of weights
 */
public class TravellingSalesmanProblem {

	int verticesAmount, startVertex, minimalEdgesSum, minimalEdgesSumTemp, hamiltonianCycleIndex, hamiltonianCycleTempIndex;
	boolean[][] adjacencyMatrix;
	double[][] edgeWeightMatrix;
	int[] hamiltonianCycle;
	int[] hamiltonianCycleTemp;
	boolean[] vertexVisited;
	List<Integer> fullPath;
	List<String> spoots;

	public void start(List<String> spoots, List<List<Double>> distances) {
		verticesAmount = spoots.size();
		this.spoots = spoots;

		initializeStructures();
		setInitialValues();
		createVerticesConnections(distances);

		TSP(startVertex, fullPath);

		showResult();
	}

	private void initializeStructures() {
		hamiltonianCycle = new int[verticesAmount];
		hamiltonianCycleTemp = new int[verticesAmount];
		vertexVisited = new boolean[verticesAmount];
		adjacencyMatrix = new boolean[verticesAmount][verticesAmount];
		edgeWeightMatrix = new double[verticesAmount][verticesAmount];
		for (int i = 0; i < verticesAmount; i++) {
			adjacencyMatrix[i] = new boolean[verticesAmount];
			edgeWeightMatrix[i] = new double[verticesAmount];
			for (int j = 0; j < verticesAmount; j++) {
				adjacencyMatrix[i][j] = false;
				edgeWeightMatrix[i][j] = 0;
			}
			vertexVisited[i] = false;
		}

		fullPath = new ArrayList<>();
	}

	private void setInitialValues() {
		minimalEdgesSum = Integer.MAX_VALUE;
		minimalEdgesSumTemp = startVertex = 0;
		hamiltonianCycleIndex = hamiltonianCycleTempIndex = 0;
	}

	private void createVerticesConnections(List<List<Double>> distances) {
		for (int i = 0; i < distances.size(); i++) {
			for (int j = 0; j < distances.get(i).size(); j++){
				if(Double.valueOf(distances.get(i).get(j)) > 0) {
					adjacencyMatrix[i][j] = true;
					edgeWeightMatrix[i][j] = distances.get(i).get(j);
				}
			}
		}
	}

	private void TSP(int vertexIndex, List<Integer> fullPath) {
		int neighborIndex;

		hamiltonianCycleTemp[hamiltonianCycleTempIndex++] = vertexIndex;                                                // add the current vertex to the cycle you are looking for

		if (hamiltonianCycleTempIndex < verticesAmount) {                                                               // search Hamiltonian path if not found
			vertexVisited[vertexIndex] = true;
			for (neighborIndex = 0; neighborIndex < verticesAmount; neighborIndex++) {                                  // check all vertex neighbors
				if (isUnvisitedNeighbor(adjacencyMatrix[vertexIndex][neighborIndex], neighborIndex)) {                  // find an unvisited neighbor
					minimalEdgesSumTemp += edgeWeightMatrix[vertexIndex][neighborIndex];                                // add to the sum the weight of the considered edge
					TSP(neighborIndex, fullPath);                                                                       // find Hamiltonian cycle for current vertex
					minimalEdgesSumTemp -= edgeWeightMatrix[vertexIndex][neighborIndex];                                // remove from the sum the weight of the considered edge
				}
			}
			vertexVisited[vertexIndex] = false;                                                                         // release the current vertex
		} else if (adjacencyMatrix[startVertex][vertexIndex]) {                                                         // if current path is Hamiltonian cycle
			minimalEdgesSumTemp += edgeWeightMatrix[vertexIndex][startVertex];                                          // check if it has the smallest sum of weights
			if (minimalEdgesSumTemp < minimalEdgesSum) {                                                                // if so (Hamiltonian cycle with the smallest sum of weights found)
				minimalEdgesSum = minimalEdgesSumTemp;                                                                  // set current sum as result
				for (neighborIndex = 0; neighborIndex < hamiltonianCycleTempIndex; neighborIndex++) {                   // set current cycle as result
					hamiltonianCycle[neighborIndex] = hamiltonianCycleTemp[neighborIndex];
				}
				hamiltonianCycleIndex = hamiltonianCycleTempIndex;
			}
			minimalEdgesSumTemp -= edgeWeightMatrix[vertexIndex][startVertex];                                          // remove vertexIndex-v0 edge weight from the sum
		}

		hamiltonianCycleTempIndex--;                                                                                    // remove current vertex from path
		fullPath.add(vertexIndex);
	}

	private boolean isUnvisitedNeighbor(boolean neighbor, int neighborIndex) {
		return neighbor && !vertexVisited[neighborIndex];
	}

	private void showResult() {
		if (hamiltonianCycleIndex > 0) {
			for (int i = 0; i < hamiltonianCycleIndex; i++) {
				System.out.print(hamiltonianCycle[i] + "-" + hamiltonianCycle[(i + 1) % verticesAmount] + " ");
				System.out.print("weight: " + edgeWeightMatrix[hamiltonianCycle[(i + 1) % verticesAmount]][hamiltonianCycle[i]] + " ");
				System.out.println();
			}

			for (int i = 0; i < hamiltonianCycleIndex; i++) {
				System.out.print(hamiltonianCycle[i] + " ");
			}
			System.out.println(startVertex);
			System.out.println("cycle weight = " + minimalEdgesSum);

			for (int i = 0; i < hamiltonianCycleIndex; i++) {
				System.out.print(printGoodLoodNames(hamiltonianCycle[i]) + " -> ");
			}
			System.out.println(printGoodLoodNames(startVertex));

			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("path_" + System.currentTimeMillis() + ".txt"));
				writer.write(fullPath.toString());

				createDisplayJson();

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("There is no Hamiltonian cycle");
		}
	}

	private String printGoodLoodNames(int i) {
		return spoots.get(i);
	}

	private void createDisplayJson() throws IOException {
		GraphData graphData = new GraphData();
		List<Edge> edges = new LinkedList<>();
		List<Node> nodes = new LinkedList<>();

		for(int i = 0; i < spoots.size(); i++) {
			nodes.add(new Node(i, spoots.get(i)));
		}

		for(int i = 1; i < edgeWeightMatrix.length; i++){
			for(int j = i - 1; j < edgeWeightMatrix[i].length; j++) {
				if(edgeWeightMatrix[i][j] > 0 ){
					edges.add(new Edge(i, j, null));

				}
			}
		}

		List<Edge> hamiltionianEdges = new LinkedList<>();
		for (int i = 0; i < hamiltonianCycleIndex - 1; i++) {
			hamiltionianEdges.add(new Edge(hamiltonianCycle[i], hamiltonianCycle[i + 1], "#ff0000"));
		}
		hamiltionianEdges.add(new Edge(hamiltonianCycle[hamiltonianCycle.length - 1], startVertex, "#ff0000"));

		edges.removeAll(hamiltionianEdges);
		edges.addAll(hamiltionianEdges);

		graphData.setEdges(edges);
		graphData.setNodes(nodes);

		Gson gson = new Gson();

		BufferedWriter writer = new BufferedWriter(new FileWriter("js_data_" + System.currentTimeMillis() + ".json"));
		writer.write(gson.toJson(graphData));
		writer.close();


	}
}