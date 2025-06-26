package Graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Graph<T> implements Comparable<Edge<? super T>> {
	
	private Set<Edge> edges;
	private Set<Vertex> vertices;
	private HashMap<Vertex, List<Edge>> adjList;
	
	public Graph(Set<Edge> edges, Set<Vertex> vertices) {
		if (edges == null || vertices == null)
			throw new IllegalArgumentException("No null arguments");
		this.edges = edges;
		this.vertices = vertices;
		adjList = new HashMap<>();
		// add vertices to adjList
		// add edges to adjList 
	}

	@Override
	public int compareTo(Edge<? super T> o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
