package spreadsheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import spreadsheet.utils.Node;

public class SpreadsheetData {
	private int minRow = 0;
	private int minCol = 0;
	private int maxRow = 0;
	private int maxCol = 0;
	
	Map<Coordinate, SpreadsheetCell> dataMap; // hash table for threads;
	List<Coordinate> sortedData; // row, column

	public SpreadsheetData() {
		dataMap = new HashMap<>();
		sortedData = new ArrayList<>();
	}

	public SpreadsheetCell getCell(char column, int row) {
		Coordinate coordinate = new Coordinate(column, row);
		SpreadsheetCell cell = dataMap.getOrDefault(coordinate, null);
		cell.evaluate();
		return cell;
	}

	public void setCell(SpreadsheetCell cell) {
		dataMap.put(cell.getCoordinate(), cell);
		// TODO:
		minRow = Math.min(minRow, cell.getRow());
		minCol = Math.min(minCol, cell.getColumn());
		maxRow = Math.max(maxRow, cell.getRow());
		maxCol = Math.max(maxCol, cell.getColumn());
		cell.setSpreadsheetData(this);
		evaluate();
	}

	public void evaluate() {
		Map<Coordinate, List<Coordinate>> depMap = prepareDependencyList();
		int vertics = calculateVertics(depMap);
		sortedData = checkDependencies(vertics,depMap);
	}

	private int calculateVertics(Map<Coordinate, List<Coordinate>> depMap) {
		Set<String> entries = new HashSet<>();
		for (Entry<Coordinate, List<Coordinate>> entry : depMap.entrySet()) {
			for(Coordinate coordinate: entry.getValue()) {
				entries.add(coordinate.toString());
			}
			entries.add(entry.getKey().toString());
		}
		return entries.size();
	}

	private Map<Coordinate, List<Coordinate>> prepareDependencyList() {
		Map<Coordinate, List<Coordinate>> result = new HashMap<>();
		for (Entry<Coordinate, SpreadsheetCell> entry : dataMap.entrySet()) {
			SpreadsheetCell cell = entry.getValue();
			Coordinate coordinate = cell.getCoordinate();
			List<Coordinate> depList = cell.dependsOn;
			result.put(coordinate, depList);
		}
		Map<Coordinate, List<Coordinate>> test = new HashMap<>();
		for(List<Coordinate> coor: result.values()) {
			for(Coordinate temp: coor) {
				if(!result.containsKey(temp)) {
					test.put(temp, new ArrayList<>());
				}
			}
		}
		result.putAll(test);
		return result;
	}

	private List<Coordinate> checkDependencies(int vertices, Map<Coordinate, List<Coordinate>> depMap) {
		List<Coordinate> sortedOrder = new ArrayList<>();
		if (vertices <= 0)
			return sortedOrder;

		Map<Coordinate, Integer> inDegree = new HashMap<>(); // count of incoming edges for every vertex

		List<Coordinate> entries = new ArrayList<>();
		for (Entry<Coordinate, List<Coordinate>> entry : depMap.entrySet()) {
			entries.addAll(entry.getValue());
		}
		inDegree = calculateDuplicate(entries, depMap);
		
		// c. Find all sources i.e., all vertices with 0 in-degrees
		Queue<Coordinate> sources = new LinkedList<>();
		for (Map.Entry<Coordinate, Integer> entry : inDegree.entrySet()) {
			if (entry.getValue() == 0)
				sources.add(entry.getKey());
		}

		// d. For each source, add it to the sortedOrder and subtract one from all of
		// its children's in-degrees
		// if a child's in-degree becomes zero, add it to the sources queue
		while (!sources.isEmpty()) {
			Coordinate vertex = sources.poll();
			sortedOrder.add(vertex);
			List<Coordinate> children = depMap.get(vertex); // get the node's children to decrement their in-degrees
			if(children == null) continue;
			for (Coordinate child : children) {
				inDegree.put(child, inDegree.get(child) - 1);
				if (inDegree.get(child) == 0)
					sources.add(child);
			}
		}

		if (sortedOrder.size() != vertices) // topological sort is not possible as the graph has a cycle
			return new ArrayList<>();

		return sortedOrder;
	}

	private Map<Coordinate, Integer> calculateDuplicate(List<Coordinate> entries,
			Map<Coordinate, List<Coordinate>> graph) {
		// int occurrences = Collections.frequency(animals, "bat");
		Map<String, Node> temp_res = new HashMap<>();
		Map<Coordinate, Integer> result = new HashMap<>();
		List<Coordinate> vertices = new ArrayList(graph.keySet());
//		for (Coordinate coorEntry : entries) {
//			vertices.add(coorEntry);
//		}
		for (Coordinate entry : vertices) {
			int freq = Collections.frequency(entries, entry);
			Node node = temp_res.getOrDefault(entry.toString(),null);
			int indegree= node==null?0:node.getIndegree();
			temp_res.put(entry.toString(),new Node(entry,indegree+freq));
		}
		for(Node node: temp_res.values()) {
			result.put(node.getCoordinate(), node.getIndegree());
		}
		return result;
	}

	@Override
	public String toString() {
		return "SpreadsheetData [minRow=" + minRow + ", minCol=" + minCol + ", maxRow=" + maxRow + ", maxCol=" + maxCol
				+ "]";
	}

}
