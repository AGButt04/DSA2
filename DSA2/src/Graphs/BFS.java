package Graphs;
import Trees.TreeProblems.TreeNode;
import java.util.*;
import Graphs.DFS.Tuple;

public class BFS {
    public static void main(String[] args) {
        Integer[] arr = {3,9,20,null,null,15,7};
        TreeNode root = buildTree(arr);
        List<List<Integer>> res = levelOrder(root);
        for  (List<Integer> list : res) {
            for (Integer integer : list) {
                System.out.print(integer+" ");
            }
            System.out.println();
        }
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        /*
        Leet-code 210
         */
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        Deque<Integer> queue = new ArrayDeque<>();
        int[] indegree = new int[numCourses];
        int[] ordering = new int[numCourses];
        int index = 0;

        for (int i  = 0; i < numCourses; i++) {
            map.put(i, new ArrayList<>());
            indegree[i] = 0;
        }
        for (int[] prereq : prerequisites) {
            int course  = prereq[0];
            int pre = prereq[1];
            map.get(course).add(pre);
            indegree[pre]++;
        }
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int course = queue.poll();
            ordering[index++] = course;
            for (int prereq : map.get(course)) {
                indegree[prereq]--;
                if (indegree[prereq] == 0) {
                    queue.offer(prereq);
                }
            }
        }
        return index == numCourses ? ordering : new int[0];
    }

    public static List<List<Integer>> levelOrder(Node root) {
        /*
        Leet-code 429 (Medium)
         */
        List<List<Integer>> res = new ArrayList<>();
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                Node curr = queue.poll();
                list.add(curr.val);
                List<Node> children = curr.children;

                for (Node child : children) {
                    queue.offer(child);
                }
            }
            res.add(list);
        }
        return res;
    }

    public int[][] updateMatrix(int[][] mat) {
        /*
        Leet-code 542
        In this problem, we are creating a matrix where each cell has
        the distance of that cell in original matrix from nearest 0, and
        we can use BFS for that, where we will keep on going until we find zero.
        We need deque for that, where we will store indexes, and create new matrix
        which will have distances and we need directions array as well to check.
         */
        Deque<int[]> queue = new ArrayDeque<>();
        int m = mat.length, n = mat[0].length;
        int[][] distances = new int[m][n];
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        /*
        This loop will go through the original grid, and if it sees a 0, it will
        just put 0 there in the distances matrix, and will push it into the queue,
        as for this problem we are doing reverse approach, where we are checking for
        1s from zeros, and increment their distances accordingly, and if we see 1, we just
        put -1 in the corresponding index in the distance matrix, indicating unprocessed.
         */
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int curr = mat[i][j];
                if (curr == 0) {
                    distances[i][j] = 0;
                    queue.offer(new int[]{i, j});
                } else {
                    distances[i][j] = -1;
                }
            }
        }

        while (!queue.isEmpty()) {
            /*
            Here, we will perform BFS, where we will pop the pushed coordinates,
            which are zeros, and check its appropriate surroundings for potential -1s.
             */
            int[] curr = queue.poll();

            for (int[] dir : directions) {
                int dx = dir[0] + curr[0];
                int dy = dir[1] + curr[1];

                if ((dx >= 0 && dx < m) && (dy >= 0 && dy < n)) {
                    /*
                    If it is in bounds and the current elements is -1, that means
                    this is unprocessed, so we update its distance, which is the
                    distance from the current index popped + 1, as this is a level traversal.
                    Then we push that onto the queue as well to be processed later for its potential neighbors.
                     */
                    if (distances[dx][dy] == -1) {
                        distances[dx][dy] = distances[curr[0]][curr[1]] + 1;
                        queue.offer(new int[] {dx, dy});
                    }
                }
            }
        }
        return distances;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        /*
        Leet-code 103
         */
        if (root == null) return new ArrayList<>();
        /*
        This problem returns the zigzag traversal of the tree which means
        that first level will be from left to right, then right to left and so on.
        For this we can perform the typical BFS, with a reverse check variable, which
        will indicate whether this particular level will reversed or not.
         */
        Deque<TreeNode> queue = new ArrayDeque<>();
        List<List<Integer>> traversal = new ArrayList<>();
        boolean reverse = false; // Starting from false, as first level is right-to-left.
        queue.offer(root);

        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<>();
            int level_size = queue.size();
            for (int i = 0; i < level_size; i++) {
                TreeNode curr = queue.pollFirst();
                level.add(curr.val);
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            /*
            After we have performed simple BFS, we can check what does our check variable says
            if it is toggled true, that means current level processed should be reversed, so we do that.
             */
            if (reverse) {
                Collections.reverse(level);
            }
            traversal.add(level); // Add the processed level to the traversal array.
            reverse = !reverse; // Toggle the reverse check for the next iteration.
        }
        return traversal;
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        /*
        Leet-code 787
         */
        HashMap<Integer, List<Tuple>> cities = new HashMap<>();
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        for (int i = 0; i < n; i++) {
            List<Tuple> info = new ArrayList<>();
            cities.put(i, info);
        }
        for (int i = 0; i < flights.length; i++) {
            int[] flight = flights[i];
            Tuple t = new Tuple(flight[1], flight[2]);
            cities.get(flight[0]).add(t);
        }
        // Add this after creating cities map
        int[][] best = new int[n][k + 2];  // best[city][stops] = min cost
        for (int[] row : best) Arrays.fill(row, Integer.MAX_VALUE);

        // Queue structure: {city, cost, stops}
        queue.offer(new int[]{src, 0, 0});
        int minCost = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int curr_city = current[0];
            int curr_cost = current[1];
            int curr_stops = current[2];
            // Add this right after polling from queue
            if (curr_cost >= best[curr_city][curr_stops]) continue;
            best[curr_city][curr_stops] = curr_cost;

            if (curr_city == dst) {
                return curr_cost;
            }
            if (curr_stops > k) continue;
            if (cities.containsKey(curr_city)) {
                for (Tuple t : cities.get(curr_city)) {
                    int next_city = t.x;
                    int nextCost = t.y + curr_cost;
                    if (nextCost < minCost) {
                        queue.offer(new int[]{next_city, nextCost, curr_stops + 1});                    }
                }
            }
        }
        return minCost == Integer.MAX_VALUE? -1 : minCost;
    }

    public static int orangesRotting(int[][] grid) {
        /*
        Leet-code 994
        For this problem, we are making all the fresh(1) oranges rotten(2) which are
        4-directionally adjacent to the rotten orange and need minimim time for that
        We will use multi-source BFS for that, Which means we will all the starting
        points which in this case all rotten oranges, into queue to process,
        and we will pop them one-by-one and start performing BFS on them.
         */
        Deque<Tuple> queue = new ArrayDeque<>();
        int min_min = 0, fresh = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                /*
                First, we will count all the fresh oranges, because we have to check at the end that
                all are rotten (fresh=0), and if we see a rotten orange, we push them into the queue.
                 */
                int curr = grid[i][j];
                if (curr == 1)
                    fresh += 1;
                if (curr == 2) {
                    Tuple t = new Tuple(i, j);
                    queue.offer(t);
                }
            }
        }
        // We check if there is any fresh orange at all or no, just return 0 if not.
        if (fresh == 0) return 0;

        while (!queue.isEmpty()) {
            /*
            Here, we will pop the coordinates of the rotten oranges pushed and we will start
            performing BFS on them, according to their levels, because all the adjacent oranges
            simultaneously become rotten and after that level is processed, we increment minutes.
             */
            int level_size = queue.size();
            min_min++;
            for (int i = 0; i < level_size; i++) {
                /*
                We pop the coordinates and get its neighbors, which will return all the adjancent
                neighbors that have a fresh orange, it will make it rotten in the grid, decrement
                the count of the fresh oranges, and will push that orange to the queue to be processed later.
                 */
                Tuple popped = queue.pollFirst();
                List<Tuple> neighbors = getNeighbors(grid, popped);
                for (Tuple n : neighbors) {
                    grid[n.x][n.y] = 2;
                    fresh--;
                    queue.offer(n);
                }
            }
        }
        // At the end we check if all the fresh oranges are zero, if yes
        // return the minimum minutes, else return -1 i.e not possible.
        return fresh == 0? min_min - 1 : -1;
    }
    public static List<Tuple> getNeighbors(int[][] grid, Tuple pos) {
        int[][] directions = {{1,0}, {0,1}, {-1,0}, {0,-1}};
        List<Tuple> neighbors = new ArrayList<>();

        for (int[] dir : directions) {
            int dx = dir[0] + pos.x;
            int dy = dir[1] + pos.y;

            if ((dx >= 0 && dx < grid.length) && (dy >= 0 && dy < grid[0].length)) {
                if (grid[dx][dy] == 1) {
                    Tuple t = new Tuple(dx, dy);
                    neighbors.add(t);
                }
            }
        }
        return neighbors;
    }

    public int minDepth(TreeNode root) {
        /*
        Leet-code 111
         */
        if (root == null) return 0;

        Deque<TreeNode> queue = new ArrayDeque<>();
        int mindepth = 0;
        queue.offer(root);

        while (!queue.isEmpty()) {
            int level_size = queue.size();
            mindepth += 1;
            for (int i = 0; i < level_size; i++) {
                TreeNode curr = queue.pollFirst();
                if (curr.left == null && curr.right == null)
                    return mindepth;
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
        }
        return mindepth;
    }

    public static List<Integer> rightSideView(TreeNode root) {
        /*
        Leet-code 199 (Medium)
         */
        if (root == null) return new ArrayList<>();

        List<Integer> rightnodes = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int level_size = queue.size();
            for (int i = 0; i < level_size; i++) {
                TreeNode curr = queue.pollFirst();
                if (i == level_size - 1)
                    rightnodes.add(curr.val);
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
        }

        return rightnodes;
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        /*
        Leet-code 102 (Medium)
         */
        List<List<Integer>> levels = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<>();
            int level_size = queue.size();
            for (int i = 0; i < level_size; i++) {
                TreeNode curr = queue.pollFirst();
                level.add(curr.val);
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            levels.add(level);
        }
        return levels;
    }

    public static TreeNode buildTree(Integer[] val) {
        Integer[] values = val;

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

    static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }
}
