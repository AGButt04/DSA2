package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class TreeProblems {
	public static void main(String[] args) {
		TreeNode root = buildTree();
		List<List<Integer>> list = levelOrder(root);
		for (List<Integer> n : list) {
			for (int i : n) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
	
	public class Codec {
		/*
		 * Leet-code 297 (Hard)
		 */
	    // Encodes a tree to a single string.
	    public String serialize(TreeNode root) {
	        StringBuilder sb = new StringBuilder();
	        serialize(root, sb);
	        return sb.toString();
	    }
	    public void serialize(TreeNode root, StringBuilder sb) {
	        if (root == null)
	            sb.append("null,");
	        else {
	            sb.append(root.val).append(",");
	            serialize(root.left, sb);
	            serialize(root.right, sb);
	        }
	    }
	   
	    // Decodes your encoded data to tree.
	    
	    public TreeNode deserialize(String data) {
	        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
	        return deserialize(queue);
	    }
	    public TreeNode deserialize(Queue<String> queue) {
	        String val = queue.poll();
	        if (val.equals("null")) return null;

	        TreeNode root = new TreeNode(Integer.parseInt(val));
	        root.left = deserialize(queue);
	        root. right = deserialize(queue);
	        return root;
	    }
	}
	
	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		/*
		 * Leet-code 236 (Medium)
		 */
		if (root == null || root == p || root == q) return root;
		
		TreeNode left = lowestCommonAncestor(root.left, p , q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);
		
		if (left != null && right != null) return root;
		
		return (left != null)? left : right;
		
	}
	
	public boolean isValidBST(TreeNode root) {
		/*
		 * Leet-code 98 (Medium)
		 */
        return validate(root, null, null);
    }

    public static boolean validate(TreeNode root, Integer min, Integer max) {
        if (root == null) return true;

        if (min != null && min >= root.val || max != null && max <= root.val)
            return false;

        boolean lefttree = validate(root.left, min, root.val);
        boolean righttree = validate(root.right, root.val, max);

        return lefttree && righttree;
    }
	
	private static int global_max;
	
	public static int maxPathSum(TreeNode root) {
		/*
		 * Leet-code 124
		 */
		// Driver method for path sum where we are updating the
		// the global_max at each recursive call.
		global_max = Integer.MIN_VALUE;
		maxPathSum(root);
		return global_max;
	}
	
	public static int maxPathHelper(TreeNode root) {
		if (root == null) return 0;
		
		/*
		 * Get the max values for both left and right subtrees
		 * and see which one is bigger to take for next call.
		 * If they return -ve values, just put zero for convenience.
		 */
		int leftval = Math.max(0, maxPathHelper(root.left));
		int rightval = Math.max(0, maxPathHelper(root.right));
		
		/*
		 * Option 1: Don't extend the path, take both children values.
		 */
		int not_extend = root.val + leftval + rightval;
		global_max = Math.max(not_extend, global_max);
		/*
		 * Option 2: Take bigger child's value and extend the path
		 */
		int extend = root.val + Math.max(rightval, leftval);
		return extend;
	}
	
	public static boolean hasPathSum(TreeNode root, int targetSum) {
		/*
		 * Leet-code 112
		 */
		// Base case is if we have fallen off the tree, that means no targetSum found.
		if (root == null) return false;
		// This says that if we are at a leaf, and we have found target sum, return true.
        if (root.left == null && root.right == null && targetSum - root.val == 0) return true;
        	
        // Here, we recurse on both left and right subtrees to find the target sum.
        boolean left = hasPathSum(root.left, targetSum - root.val);
        boolean right = hasPathSum(root.right, targetSum - root.val);
        
        // OR is being used because if any of them return true, we're good.
        return left || right;
	}
	
	public static TreeNode invertTree(TreeNode root) {
		/*
		 * Leet-code 226
		 */
		// Base case, If we have fell of the tree, just return root.
		if (root == null) return root;
		
		/*
		 * Here we are inverting the right subtree and left subtree.
		 * Which will go all the way to the leaves and start inverting
		 * on its way back up by the code we wrote below.
		 */
		invertTree(root.right);
		invertTree(root.left);
		/*
		 * Here, it simple is swapping the both children, bear in mind
		 * swapping the nodes, not the values. If we swap only values,
		 * that would not carry over the children with the subtrees.
		 */
		TreeNode left_tree = root.left;
		root.left = root.right;
		root.right = left_tree;
		
		return root;
	}
	
	public static boolean isSame(TreeNode p, TreeNode q) {
		/*
		 * Leet-code 100
		 */
		/*
		 * Simple recursion, where our base case is if any of
		 * the nodes became null, we just return p == q, which will
		 * check if they were the same. And if both nodes were 
		 * different, we just return false. And at the end return
		 * the recursive call of left and right subtrees of both trees.
		 */
		if (p == null || q == null) return p == q;
		if (p.val != q.val) return false;
		
		return isSame(p.left, q.left) && isSame(p.right, q.right);
	}
	
	public static int maxDepth(TreeNode root) {
		/*
		 * Leet-code 104
		 */
		if (root == null) return 0;

        int height = Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
        return height;
	}
	
	public static List<List<Integer>> levelOrder(TreeNode root) {
		/*
		 * Leet-code 102 (Medium)
		 */
		if (root == null) return new ArrayList<>();
		
		/*
		 * This is a BFS approach as BFS does level by level traversal.
		 * We will use a Queue where we will push the current children
		 * of the node and start processing them level by level.
		 */
		Deque<TreeNode> levels = new ArrayDeque<>();
		List<List<Integer>> levelorder = new ArrayList<>();
		levels.add(root); // Add the root to start the process

		while (!levels.isEmpty()) {
			/*
			 * We will loop Queue's size of times because that is how 
			 * many nodes this current level has and we will add all
			 * of their children to queue to process the next level.
			 */
		    int levelsize = levels.size();  // Key insight!
		    List<Integer> lev = new ArrayList<>();
		    
		    for (int i = 0; i < levelsize; i++) {
		    	/*
		    	 * Process the popped node, add its value to the list, and 
		    	 * see if this has a left/right children, push them on the queue.
		    	 */
		        TreeNode node = levels.pollFirst();
		        lev.add(node.val);
		        if (node.left != null) levels.add(node.left);
		        if (node.right != null) levels.add(node.right);
		    }
		    
		    // After we have added all the level's node, add the list to levels list.
		    levelorder.add(lev);
		}
		return levelorder;
	}
	
	public static List<Integer> postorderTraversalLoop(TreeNode root) {
		/*
		 * Leet-code 145 (Iterative Solution)
		 */
		/*
		 * In this iterative solution for post-order traversal, we will
		 * use a visited node that will keep track of any node we have
		 * already added so far in the list. The idea is the same as 
		 * pre-order traversal but here we will not push the right child,
		 * but just check if its not visited, then we move the walker to it.
		 */
		Stack<TreeNode> st = new Stack<>();
		List<Integer> traversal = new ArrayList<>();
		TreeNode walker = root;
		TreeNode visitedNode = null;
		while (!st.isEmpty() || walker != null) {
			if (walker == null) {
				/*
				 * Here we will check to see if our walker is null, if it is
				 * that means we don't have left children anymore, and we will
				 * check if top of the stack has a right child and its not visited,
				 * if its not, move the walker to the right child as it makes sense
				 * in post-order traversal, else we pop the stack and move the walker to top.
				 */
				TreeNode topNode = st.peek();
				if (topNode.right != null && topNode.right != visitedNode) {
					walker = topNode.right;
				} else {
					st.pop();
					traversal.add(topNode.val);
					visitedNode = topNode;
				}
			} else {
				/*
				 * Else we just push the current node and move the walker to left child.
				 */
				st.push(walker);
				walker = walker.left;
			}
		}
		return traversal;
	}

	
	public static List<Integer> postorderTraversal(TreeNode root) {
		List<Integer> traversal = new ArrayList<>();
		postorderTraversal(root, traversal);
		return traversal;
	}
	
	public static void postorderTraversal(TreeNode root, List<Integer> traversal) {
		/*
		 * Leet-code 145
		 */
		/*
		 * Post-order traversal is left -> right -> root.
		 * Call the function recursively in that order.
		 */
		if (root != null) {
			postorderTraversal(root.left, traversal);
			postorderTraversal(root.right, traversal);
			traversal.add(root.val);
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
        Integer[] values = {3,9,20,null,null,15,7};

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

}
