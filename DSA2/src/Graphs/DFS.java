package Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class DFS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static Node cloneGraph(Node node) {
		/*
		 * Leet-code 133 (Medium)
		 */
		/*
		 * In this problem we are provided with a graph, we are supposed
		 * to return the deep-copy of the graph with new nodes and same structure.
		 * The node has its value, and a list of its adjacent nodes.
		 */
		if (node == null) return null;
        /*
         * The idea here is that we will use a HashMap, which will have the original
         * node as a key and its copy node as the value, and we will the stack to perform
         * a DFS on the nodes, and map their neighbors to their copy nodes. We create a 
         * new Node for the root, and add it in the HashMap and the stack to process later.
         */
        HashMap<Node, Node> cloneMap = new HashMap<>();
        Stack<Node> stack = new Stack<>();
        Node newNode = new Node(node.val);
        cloneMap.put(node, newNode);
        stack.push(node);

        while (!stack.isEmpty()) {
        	// Pop the current Node from the stack.
            Node current = stack.pop();
            /*
             * Go into the neighbors list of the current node, and stack will have
             * original nodes in it, not copies. Copies are just in HashMap to get.
             */
            for (Node neighbor : current.neighbors) {
            	/*
            	 * We see if this neighbor is not in the HashMap, that means we
            	 * have not created its copy yet, so we create its copy and add
            	 * it in the map with the original node as its key and push the
            	 * new neighbor onto the stack as we will have to process its neighbors too.
            	 */
                if (!cloneMap.containsKey(neighbor)) {
                    Node newNeighbor = new Node(neighbor.val);
                    cloneMap.put(neighbor, newNeighbor);
                    stack.push(neighbor);
                }
                /*
                 * If the neighbor is already in the map or if we just added it, now we
                 * can get the COPY'S neighbors list and add the COPY neighbor node in it.
                 * This is a crucial step for this problem as it maps copies to copies.
                 */
                Node copyParent = cloneMap.get(current);
                Node copyParentneighbor = cloneMap.get(neighbor);
                copyParent.neighbors.add(copyParentneighbor);
            }
        }
        // return the copy node of the root from the map.
        return cloneMap.get(node);
	}
	
	public static List<Tuple> getNeighbors(char[][] grid, Tuple pos) {
		/*
		 * Helper for numIslands
		 */
		/*
		 * This method is a helper for numIslands, which checks the neighbors
		 * of the provided position for potential 1s. If they are not out of
		 * bounds and are 1s, we can add them in neighbors and return the list of tuples.
		 */
		List<Tuple> neighbors = new ArrayList<>();
		int[][] directions = {{-1,0}, {1,0}, {0,1}, {0,-1}};
		int i = 0;
		for (int[] dir : directions) {
			int dx = dir[0] + pos.x;
			int dy = dir[1] + pos.y;
			
			if ((dx >= 0 && dx < grid.length) && (dy >= 0 && dy < grid[0].length)) {
				if (grid[dx][dy] == '1')
					neighbors.add(new Tuple(dx, dy));
			}
		}
		return neighbors;
	}
	
	public static int numIslands(char[][] grid) {
		/*
		 * Leet-code 200
		 */
		/*
		 * In this problem, we are using DFS to find the number of islands
		 * in a grid which are adjacent ones. We could use DP too for this problem.
		 * We will use a stack as this is DFS and HashSet, where we will add the 
		 * indexes which have been visited. The idea is to do DFS when we come across
		 * a one, and add all adjacent co-ordinates in the visited and regard it as one ISLAND.
		 */
		Stack<Tuple> stack = new Stack<>();
		HashSet<Tuple> visited = new HashSet<>();
		int islands = 0;
		/*
		 * We will scan the grid for potential ones which are the 1s.
		 */
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				// If the element is zero, we just continue, no need to continue.
				if (grid[i][j] == '0')
					continue;
				
				/*
				 * We will store tuples in the visited set and in stack that we 
				 * can pop at each iteration to find the neighbors of, only 1s
				 */
				Tuple current = new Tuple(i, j);
				if (visited.contains(current))
					continue;
				
				stack.push(current);
				visited.add(current);
				/*
				 * This loop will keep on going until we have elements in the stack,
				 * that means that we still have adjacent 1s to process for potential neighbors.
				 */
				while (!stack.isEmpty()) {
					/*
					 * We pop the current tuple and get all its neighbors that are not in
					 * the visited set, and are 1s to push in the stack and in the visited set.
					 */
					Tuple popped = stack.pop();
					List<Tuple> neigbors = getNeighbors(grid, popped);
					for (Tuple neighbor : neigbors) {
						if (!visited.contains(neighbor)) {
							stack.push(neighbor);
							visited.add(neighbor);
						}
					}
				}
				/*
				 * After we are done with the DFS step, we can increment the islands.
				 */
				islands++;
			}
		}
		return islands;
	}
	static class Node {
	    public int val;
	    public List<Node> neighbors;
	    public Node() {
	        val = 0;
	        neighbors = new ArrayList<Node>();
	    }
	    public Node(int _val) {
	        val = _val;
	        neighbors = new ArrayList<Node>();
	    }
	    public Node(int _val, ArrayList<Node> _neighbors) {
	        val = _val;
	        neighbors = _neighbors;
	    }
	}
	
	public static class Tuple {
		private int x;
		private int y;
		public Tuple(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public boolean equals(Object obj) {
		    if (this == obj) return true;
		    if (obj == null || getClass() != obj.getClass()) return false;
		    Tuple tuple = (Tuple) obj;
		    return x == tuple.x && y == tuple.y;
		}

		@Override
		public int hashCode() {
		    return Objects.hash(x, y);
		}
	}
	
}
