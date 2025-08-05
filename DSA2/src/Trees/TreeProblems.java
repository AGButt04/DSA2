package Trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class TreeProblems {
	public static void main(String[] args) {
		TreeNode root = buildTree();
		 List<Integer> list = preorderTraaversalLoop(root);
		for (int n : list) {
			System.out.print(n + " ");
		}
		
	}
	
	public static class TreeNode {
		int val;
		TreeNode right;
		TreeNode left;
		
		TreeNode() {};
		TreeNode(int val) {this.val = val;}
		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = left;
		}
	}
	
	public static List<Integer> preorderTraaversalLoop(TreeNode root) {
		/*
		 * Leet-code 144 (Iterative Solution)
		 */
		/*
		 * In iterative solution, we will use stack again to push each node
		 * but this time we push right children of each node if there is one,
		 * and we will backtrack by popping the stack to get to the right child.
		 */
		List<Integer> traversal = new ArrayList<>();
		Stack<TreeNode> righties = new Stack<>();
		TreeNode walker = root;
		while (!righties.isEmpty() || walker != null) {
			/*
			 * If the walker become null, just pop the stack for next node.
			 */
			if (walker == null)
				walker = righties.pop();
			/*
			 * Else, you add each node first in the list, then check if this
			 * node has a right child, push it on the stack and move left.
			 */
			traversal.add(walker.val);
			if (walker.right != null)
				righties.push(walker.right);
			walker = walker.left;		
		}
		return traversal;
	}
	
	public static List<Integer> preorderTraversal(TreeNode root) {
		 List<Integer> list = new ArrayList<>();
		 preorderTraversal(root, list);
		 return list;
	}
	
	public static void preorderTraversal(TreeNode root, List<Integer> list) {
		/*
		 * Leet-code 144
		 */
		/*
		 * Pre-order traversal is root -> left child -> right child.
		 *  Same recursive calls will be done in that order.
		 */
		if (root != null) {
			list.add(root.val);
			preorderTraversal(root.left, list);
			preorderTraversal(root.right, list);
		}
	}
	
	public static List<Integer> inorderTraaversalLoop(TreeNode root) {
		/*
		 * Leet-code 94 (Iterative Solution)
		 */
		/*
		 * In iterative solution, we will use stack to push each node
		 * and keep on moving left, until walker becomes null, and then
		 * we will backtrack by popping the stack, add it to the list, as
		 * it would be the smallest element, move right, and then keep going left.
		 */
		List<Integer> traversal = new ArrayList<>();
		Stack<TreeNode> parents = new Stack<>();
		TreeNode walker = root;
		/*
		 * Keep on going until stack is not empty or walker is not null.
		 */
		while (!parents.isEmpty() || walker != null) {
			/*
			 * If walker became null, then pop the stack for the next smaller
			 * element, add it to our list and move right. If the walker is not
			 * null, then we just keep on going left while pushing the nodes.
			 */
			if (walker == null) {
				walker = parents.pop();
				traversal.add(walker.val);
				walker = walker.right;
			} else {
				parents.push(walker);
				walker = walker.left;
			}
		}
		
		return traversal;
	}
	
	public static List<Integer> inorderTraversal(TreeNode root) {
	    List<Integer> result = new ArrayList<>();
	    inorderTraversal(root, result);
	    return result;
	}
	
	public static void inorderTraversal(TreeNode root, List<Integer> list) {
		/*
		 * Leet-code 94
		 */
		/*
		 * In-order traversal is you go left first, then root, and then right,
		 * and that's exactly the way we will explore the tree for traversal.
		 */
		if (root != null) {
			inorderTraversal(root.left, list);
			list.add(root.val);
			inorderTraversal(root.right, list);
		}
	}
	
	public static TreeNode buildTree() {
        Integer[] values = {1, 2, 3, 4, 5, null, 8, null, null, 6, 7, 9};

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
