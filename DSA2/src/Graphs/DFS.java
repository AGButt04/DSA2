package Graphs;

import Trees.TreeProblems.TreeNode;

import java.util.*;

public class DFS {

	public static void main(String[] args) {
        TreeNode root = buildTree();
        int targetSum = 8;
        int[][] graph = {{1},{2},{3},{}};
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for  (int j = 0; j < graph[i].length; j++) {
                temp.add(graph[i][j]);
            }
            res.add(temp);
        }
        System.out.println(canVisitAllRooms(res));
    }

    public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        /*
        Leet-code 841
        This is a classic DFS problem where are trying to see if we can visit
        all the rooms with the key we have in the rooms. Here we will use simple
        DFS with stack and visited set, and start with the 0, as that room is open.
         */
        Stack<Integer> stack = new Stack<>();
        HashSet<Integer> visited = new HashSet<>();
        stack.push(0);
        visited.add(0);

        while (!stack.isEmpty()) {
            /*
            We'll keep on looping until stack is empty as always, and we will
            get the current room which is pushed onto the stack and get the keys
            in that room which are located in the room 2D list given at the index
            room, simply we will loop over the keys and push into the visited set.
             */
            int curr_room = stack.pop();
            List<Integer> curr_keys = rooms.get(curr_room);
            for (int key : curr_keys) {
                if (!visited.contains(key)) {
                    stack.push(key);
                    visited.add(key);
                }
            }
        }
        /*
        If we found all the keys, then length of rooms list should be
        equal to the visited set meaning visited all rooms, if not then
        that means we are missing a key, and hence we will return false.
         */
        return rooms.size() == visited.size();
    }
    public static class Employee {
        int id;
        int importance;
        List<Integer> subordinates;
        public Employee(int id, int importance, String name) {
            this.id = id;
            this.importance = importance;
            this.subordinates = new ArrayList<>();
        }
    }
    public static int getImportance(List<Employee> employees, int id) {
        /*
        Leet-code 690
        In this problem, we are getting the importance of the given employee, by adding
        all its direct and indirect subordinates' importances, and we can use DFS for that
        with HashMap, which will store the id's of employee's mapped to the employee object.
         */
        Stack<Integer> st = new Stack<>();
        HashMap<Integer, Employee> subs = new HashMap<>();
        int importance = 0;
        // push the id of the main person
        st.push(id);
        /*
        First we will populate the hashmap, by adding all the employee ID's
        as keys and their values will be their employee objects for O(1) retrieval.
         */
        for (Employee emp : employees) {
            subs.put(emp.id, emp);
        }

        while (!st.isEmpty()) {
            /*
            Then we will do typical DFS by popping the stack which contains the ID's of
            employees and to get the list of their subordinates, we will need the actual
            employee object which is stored in the map, we'll get it and push their ID's to stack
             */
            int curr_id = st.pop();
            Employee curr_emp = subs.get(curr_id);
            importance += curr_emp.importance; // add each popped employee's importance.
            List<Integer> subord = curr_emp.subordinates;
            for (int s : subord) {
                st.push(s);
            }
        }
        return importance;
    }

    public static List<String> binaryTreePaths(TreeNode root) {
        /*
        Leet-code 257
        To get all the paths in string format, we will use recursive DFS.
        Create a list of string paths, and if root != null, call the function.
         */
        List<String> paths = new ArrayList<>();
        if (root != null) {
            DFS_tree(paths, "", root);
        }
        return paths;
    }
    public static void DFS_tree(List<String> paths, String path, TreeNode root) {
        /*
        In this function we see if the node given is a leaf or not, if yes then
        we will add its value to the current path of the recursive call, and add
        the path without "->" to the paths list and return the call back.
         */
        if  (root.left == null && root.right == null) {
            path += root.val;
            paths.add(path);
            return;
        }
        /*
        Else we create a new current path by copying the older path, adding the
        value of the current node and "->" to create the String, and then we
        will perform two recursive calls on the left subtree and rightsubtree.
         */
        String currentPath = path + root.val + "->";
        if (root.left != null) DFS_tree(paths, currentPath, root.left);
        if (root.right != null) DFS_tree(paths, currentPath, root.right);
    }

    public static List<List<Integer>> allPaths(int[][] graph) {
        /*
        lEET-CODE 797 (Recursive Solution)
         */
        /*
        This is a recursive solution for the allpaths problem, where idea is the same,
        but this one is more cleaner and alittle faster. Here, we also create a new initial
        path, and add 0 to it, and we give the paths, and path to the call with the target = graph.length-1.
         */
        List<List<Integer>> paths = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        int target = graph.length-1;
        path.add(0);
        DFS_paths(graph, paths, path, 0, target);
        return paths;
    }
    public static void DFS_paths(int[][] graph, List<List<Integer>> paths, ArrayList<Integer> path, int curr, int target) {
        /*
        Helper for recursive allPaths.
        This method check if the current node/element is the same as the target meaning last value,
        if yes, then it creates a new ArrayList, and copies this path and add it to paths and return the call.
         */
        if (curr==target){
            paths.add(new ArrayList<>(path));
            return;
        }
        /*
        Else, we get the neighbors for the current element, which is the last element in the path,
        and go into the neighbors, we add the neighbor to our current path and calls the function
        again to explore this path now, which will explore this path to the end, and when the
        call returns, we can just remove this neighbor from the path (backtrack), and keep on doing
        the same steps for the other neighbors, and this process will eventually find all paths.
         */
        int[] neighbors = graph[curr];
        for (int n : neighbors) {
            path.add(n);
            DFS_paths(graph, paths, path, n, target);
            path.remove(path.size()-1);
        }
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        /*
        Leet-code 733
        This problem also uses DFS, as the approach here is that we start the DFS from the
        sr, sc coordinates given, change its value to color value given, and start exploring
        its neighbors, and each adjacent neighbor with the same value as starting point also gets changed.
         */
        // If the starting position element is already color, then no need to progress, just return.
        if (image[sr][sc] == color) return image;
        /*
        Here, we will use a stack and visited set, we will create tuples of indexes, and push them onto the
        stack and visited set, and push the (sr,sc) tuple and start the process and save its original value
        as that will be used to compare with its neighbors to add.
         */
        Stack<Tuple> st = new Stack<>();
        HashSet<Tuple> visited = new HashSet<>();
        Tuple start = new Tuple(sr, sc);
        int c = image[sr][sc];
        st.push(start);
        visited.add(start);

        while (!st.isEmpty()) {
            /*
            Pop the current coordinate, update its value with the color value given, and get its neighbors from the
            getneighbors method written, which will return the list of tuple coordinate, which are valid, meaning
            in range and with same values as origin value, we'll add them to visited set and stack if not pushed before.
             */
            Tuple current = st.pop();
            image[current.x][current.y] = color;
            List<Tuple> neighbors = getNeighbors(image, current, c);
            for (Tuple t : neighbors) {
                if (!visited.contains(t)) {
                    st.push(t);
                    visited.add(t);
                }
            }
        }
        // return the updated image.
        return image;
    }
    public static List<Tuple> getNeighbors(int[][] image, Tuple curr, int c) {
        /*
        This is a typical getneighbors method, but here we also check if the
        value of the neighbor is equal to the original value (c) given.
         */
        int[][] directions = {{1,0}, {0,1}, {-1,0}, {0,-1}};
        List<Tuple> neighbors = new ArrayList<>();
        for (int[] dir : directions) {
            int dx = dir[0] + curr.x;
            int dy = dir[1] + curr.y;

            if ((dx >= 0 && dx < image.length) && (dy >= 0 && dy < image[0].length)) {
                if (image[dx][dy] == c) {
                    Tuple n = new Tuple(dx, dy);
                    neighbors.add(n);
                }
            }
        }
        return neighbors;
    }

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        /*
        Leet-code 797
        In this problem, we are finding all the possible paths from the node 0 to the
        last node which is the length of the graph - 1. For this, we will use a simple
        DFS approach, in which we will use a Stack, and because this graph is a Directed
        Acyclic Graph (DAG), we don't need a visited set, as there are no cycles.
         */
        /*
        For this problem, stack will not store the nodes, but the whole paths, which will
        be explored all the way throughout the loop, and when it ends, we explore another.
         */
        int target = graph.length-1;
        List<List<Integer>> paths = new ArrayList<>();
        Stack<ArrayList<Integer>> st = new Stack<>();
        /*
        Creating a new List, and adding 0 to it as all the paths will start from 0. The idea
        is that this path will be explored by its neighbors, and added again as a new one, so we push it.
         */
        ArrayList<Integer> currPath = new ArrayList<>();
        currPath.add(0);
        st.push(currPath);

        while (!st.isEmpty()) {
            /*
            This main loop pops the current path from the stack and retrieve its last element as that element's
            neighbors will be explored next, and we check if that element is the target, if not, we get its neighbors
            from the graph. Then we go into its neighbors, and copy the current path and create neighbor num of new
            paths and push onto the stack as there will be neighbor number of possibilities for exploring at each step.
             */
            ArrayList<Integer> curr = st.pop();
            int lastelem = curr.get(curr.size()-1);
            if (lastelem == target) {
                // If we found the target then we create the new Arraylist, copy the current path and add to paths.
                paths.add(curr);
            }
            int[] neighbors = graph[lastelem];
            for (int n : neighbors) {
                ArrayList<Integer> newPath = new ArrayList<>(curr);
                // We create the new path, add current neighbor to it and push this new path to the stack to be explored next.
                newPath.add(n);
                st.push(newPath);
            }
        }
        return paths;
    }

    public boolean isSymmetric(TreeNode root) {
        /*
        Leet-code 101
         */
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    public boolean isMirror(TreeNode left, TreeNode right) {
        /*
        Helper for leet-code 101
         */
        if (left == null && right == null) return true;

        if (left == null || right == null) return false;

        if (left.val != right.val) return false;

        return isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }

    public static boolean validPath(int n, int[][] edges, int source, int destination) {
        /*
        Leet-code 1971.
         */
        Stack<Integer> st = new Stack<>();
        HashSet<Integer> visited = new HashSet<>();
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int vertex = edge[0];
            int neighbor = edge[1];
            graph.get(vertex).add(neighbor);
            graph.get(neighbor).add(vertex);
        }

        st.push(source);
        visited.add(source);
        while (!st.isEmpty()) {
            int curr = st.pop();
            if (curr == destination) return true;
            List<Integer> neighbors = graph.get(curr);
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    st.push(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return false;
    }

    public boolean exist(char[][] board, String word) {
        /*
        Leet-code 79
         */
        /*
        In this problem, we are supposed to check if the word given exists in the mXn grid.
        This could be done iteratively but that would be too complex to implement as it
        requires backtracking and recursion does the backtracking automatically for us.
         */
        int m = board.length;
        int n = board[0].length;
        /*
        The idea here is that we will scan through the grif and will call the DFS method which is
        a recursive method on each position, and if this method returns true, we will return true,
        as that means that word can be found from this position on. The main logic is the DFS.
         */
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (DFS(board, i , j, 0, word)) {
                    return true;
                }
            }
        }
        // If no call returned true, that means it cannot be formed, so return false.
        return false;
    }

    public static boolean DFS(char[][] board, int x, int y, int index, String word) {
        /*
        Helper method for leet-code 79
         */
        /*
        This method performs DFS recursively, from the current position for all 4 adjacent directions.
        The base case would be if our index which keeps track of which character we are on of the word,
        if that reaches end of the word, that means we have found the word, so return true.
         */
        if (index == word.length()) return true;
        /*
        This condition has all scenarios where word cannot be formed, that if the coordinates aere out
        of bounds of if the current character is not equal to the character if the word we are looking for.
         */
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || word.charAt(index) != board[x][y])
            return false;
        /*
        This is the main logic, where we temporarily store the current position's value and put #
        there just to indicate that its visited, and we will recurse in each direction from this
        point on with the index incremented by one, to look for thr remaining characters. And if
        any of these 4 calls return true that means that word can be formed in that direction.
         */
        char temp = board[x][y];
        board[x][y] = '#';
        boolean found = DFS(board, x, y+1, index+1, word) ||
                DFS(board, x+1, y, index+1, word) ||
                DFS(board, x, y-1, index+1, word) ||
                DFS(board, x-1, y, index+1, word);

        // For backtracking, we put the character back when we return from all the calls
        board[x][y] = temp;
        // And, just return found variable which would be false only if all calls returned false.
        return found;
    }

    public static int maxAreaofIsland(char[][] grid) {
        /*
         * Leet-code 695
         */
        /*
        This problem is the same as numIslands, but instead here we are counting
        how many 1s are in an adjacent islands, which will be counted as its area,
        at the end we are just returning the biggest island with largest area.
         */
        Stack<Tuple> stack = new Stack<>();
        HashSet<Tuple> visited = new HashSet<>();
        int maxislands = 0;
        /*
         * We will scan the grid for potential ones which are the 1s.
         */
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == '0')
                    continue;

                Tuple current = new Tuple(i, j);
                if (visited.contains(current))
                    continue;
                int currArea = 1;
                stack.push(current);
                visited.add(current);
                while (!stack.isEmpty()) {
                    Tuple popped = stack.pop();
                    List<Tuple> neigbors = getNeighbors(grid, popped);
                    for (Tuple neighbor : neigbors) {
                        if (!visited.contains(neighbor)) {
                            stack.push(neighbor);
                            visited.add(neighbor);
                            currArea += 1;
                        }
                    }
                }
                maxislands = Math.max(maxislands, currArea);
            }
        }
        return maxislands;
    }

    public static int pathSum(TreeNode root, int targetsum) {
        /*
        Leet-code 467 (Medium)
         */
        HashMap<Integer, Integer> prefix_sums = new HashMap<>();
        prefix_sums.put(0, 1);
        int count = DFS(root, targetsum, prefix_sums, 0);
        return count;
    }

    public static int DFS(TreeNode root, int targetsum, HashMap<Integer, Integer> prefix_sums, int runningSum) {
        if (root == null) return 0;

        runningSum += root.val;
        int pathCount = prefix_sums.getOrDefault(runningSum-targetsum, 0);

        prefix_sums.put(runningSum, prefix_sums.getOrDefault(runningSum, 0) + 1);

        pathCount += DFS(root.left, targetsum, prefix_sums, runningSum);
        pathCount += DFS(root.right, targetsum, prefix_sums, runningSum);

        prefix_sums.put(runningSum, prefix_sums.get(runningSum)-1);

        return pathCount;
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

    public static TreeNode buildTree() {
        Integer[] values = {10,5,-3,3,2,null,11,3,-2,null,1};

        if (values.length == 0 || values[0] == null) return null;

        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;

        while (i < values.length) {
            TreeNode current = queue.poll();

            // Left child
            if (i < values.length && values[i] != null) {
                current.left = new TreeNode(values[i]);
                queue.offer(current.left);
            }
            i++;

            // Right child
            if (i < values.length && values[i] != null) {
                current.right = new TreeNode(values[i]);
                queue.offer(current.right);
            }
            i++;
        }

        return root;
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
