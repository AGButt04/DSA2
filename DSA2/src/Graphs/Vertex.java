package Graphs;

public class Vertex<T> {
	
	private T data;
	
	public Vertex(T data) {
		if (data == null)
			throw new IllegalArgumentException("No Null Data");
		else
			this.data = data;
	}
	
	public int hashCode() {
		return data.hashCode();
	}
	
	public boolean equals() {
		return false;
	}
	
	public T getData() {
		return data;
	}
	
	public String toString() {
		return null;	
	}
}

