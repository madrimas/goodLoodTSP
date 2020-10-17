import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exhaustive search - checks all hamiltonian cycles and returns the cycle with the smallest sum of weights
 */
public class TravellingSalesmanProblem {

	int verticesAmount, edgesAmount, startVertex, minimalEdgesSum, minimalEdgesSumTemp, hamiltonianCycleIndex, hamiltonianCycleTempIndex;
	boolean[][] adjacencyMatrix;
	int[][] edgeWeightMatrix;
	int[] hamiltonianCycle;
	int[] hamiltonianCycleTemp;
	boolean[] vertexVisited;
	List<Integer> fullPath;

	public void start() {
		verticesAmount = 24;
		edgesAmount = 48;

		initializeStructures();
		setInitialValues();
		createVerticesConnections();

		TSP(startVertex, fullPath);

		showResult();
	}

	private void initializeStructures() {
		hamiltonianCycle = new int[verticesAmount];
		hamiltonianCycleTemp = new int[verticesAmount];
		vertexVisited = new boolean[verticesAmount];
		adjacencyMatrix = new boolean[verticesAmount][verticesAmount];
		edgeWeightMatrix = new int[verticesAmount][verticesAmount];
		for (int i = 0; i < verticesAmount; i++) {
			adjacencyMatrix[i] = new boolean[verticesAmount];
			edgeWeightMatrix[i] = new int[verticesAmount];
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

	private void createVerticesConnections() {
		for (int i = 0; i < verticesAmount; i++) {
			adjacencyMatrix[i][(i + 1) % verticesAmount] = adjacencyMatrix[(i + 1) % verticesAmount][i] = true;
			edgeWeightMatrix[i][(i + 1) % verticesAmount] = edgeWeightMatrix[(i + 1) % verticesAmount][i] = (int) (Math.random() * 10 + 1);

			adjacencyMatrix[i][(i + 2) % verticesAmount] = adjacencyMatrix[(i + 2) % verticesAmount][i] = true;
			edgeWeightMatrix[i][(i + 2) % verticesAmount] = edgeWeightMatrix[(i + 2) % verticesAmount][i] = (int) (Math.random() * 10 + 1);
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

			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("path_" + System.currentTimeMillis() + ".txt"));
				writer.write(fullPath.toString());

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("There is no Hamiltonian cycle");
		}
	}
}