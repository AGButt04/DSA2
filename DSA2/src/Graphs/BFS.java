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

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        /*
        Leet-code 787
         */
        HashMap<Integer, List<Tuple>> cities = new HashMap<>();
        Deque<int[]> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            List<Tuple> info = new ArrayList<>();
            cities.put(i, info);
        }

        for (int i = 0; i < flights.length; i++) {
            int[] flight = flights[i];
            Tuple t = new Tuple(flight[1], flight[2]);
            cities.get(flight[0]).add(t);
        }

        // Queue structure: {city, cost, stops}
        queue.offer(new int[]{src, 0, 0});
        int minCost = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            int[] current = queue.pollFirst();
            int curr_city = current[0];
            int curr_cost = current[1];
            int curr_stops = current[2];

            if (curr_city == dst) {
                minCost = Math.min(curr_cost, minCost);
                continue;
            }

            if (curr_stops > k) continue;

            if (cities.containsKey(curr_city)) {
                for (Tuple t : cities.get(curr_city)) {
                    int next_city = t.x;
                    int nextCost = t.y + curr_cost;
                    if (nextCost < minCost) {
                        queue.offer(new int[]{next_city, nextCost, curr_stops+1});
                    }
                }
            }
        }
        return minCost == Integer.MAX_VALUE? -1 : minCost;
    }

    public static int orangesRotting(int[][] grid) {
        Deque<Tuple> queue = new ArrayDeque<>();
        int min_min = 0, fresh = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int curr = grid[i][j];
                if (curr == 1)
                    fresh += 1;
                if (curr == 2) {
                    Tuple t = new Tuple(i, j);
                    queue.offer(t);
                }
            }
        }

        if (fresh == 0) return 0;

        while (!queue.isEmpty()) {
            int level_size = queue.size();
            min_min++;
            for (int i = 0; i < level_size; i++) {
                Tuple popped = queue.pollFirst();
                List<Tuple> neighbors = getNeighbors(grid, popped);
                for (Tuple n : neighbors) {
                    grid[n.x][n.y] = 2;
                    fresh--;
                    queue.offer(n);
                }
            }
        }
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
}
