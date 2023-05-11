package spreadsheet.utils;

import spreadsheet.Coordinate;

public class Node {
Coordinate coordinate;
int indegree;

public Node(Coordinate coordinate, int indegree) {
	super();
	this.coordinate = coordinate;
	this.indegree = indegree;
}
public Coordinate getCoordinate() {
	return coordinate;
}
public void setCoordinate(Coordinate coordinate) {
	this.coordinate = coordinate;
}
public int getIndegree() {
	return indegree;
}
public void setIndegree(int indegree) {
	this.indegree = indegree;
}

}
