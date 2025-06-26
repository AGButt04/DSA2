package Graphs;
public class Edge<T> implements Comparable<Edge<? super T>> {
	
	private Vertex u;
	private Vertex v;
	private int weight;
	
	public Edge(Vertex u, Vertex v, int weight) {
		if (u ==  null || v == null)
			throw new IllegalArgumentException("Vertex cannot be null");
		this.u = u;
		this.v = v;
		this.weight = weight;
	}
	
	public int hashCode() {
		return 0;
	}
	
	public boolean equals() {
		return false;
	}
	
	public Vertex getVertexU() {
		return u;
	}
	
	public Vertex getVertexV() {
		return v;
	}
	
	public int getWeight() {
		return weight;
	}

	@Override
	public int compareTo(Edge<? super T> o) {
		return 0;
	}
	
	public String toString() {
		return null;
		
	}

}

