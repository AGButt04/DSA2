package Trees;
import java.util.ArrayList;
import java.util.Stack;

public class AVLTree {
	public static void main(String[] args) {
		int[] numbers = {20,30,15,40,35,10,37,5,38,39,25};
		AVLTree tree = new AVLTree();
		System.out.println("Adding to AVL:");
		for(int i = 0; i < numbers.length; i++) {
			tree.add(numbers[i]);
		}
		System.out.println("AVL height: " + tree.height());
		tree.print();
		tree.remove(20);
		tree.print();
		tree.add(20);
		tree.add(34);
		tree.print();
	}
	
	private class Node{
		int element;
		Node right;
		Node left;
		int height;
		
		public Node(int elem) {
			element = elem;
			right = null;
			left = null;
			height = 0;
		}
	}
	
	private Node root;
	private int size;
	
	public AVLTree() {
		root = null;
		size = 0;
	}

	public int height() {
		return getHeight(root);
	}
	
	private int getHeight(Node n) {
		if (n == null)
			return -1;
		else 
			return n.height;
	}
	
	private int getBalance(Node n) {
		return getHeight(n.left) - getHeight(n.right);
	}
	
	
	private Node LeftRot(Node n) {
		Node t = n.right;
		n.right = t.left;
		t.left = n;
		updateHeight(n);
		updateHeight(t);
		return t;
	}
	
	private Node RightRot(Node n) {
		Node t = n.left;
		n.left = t.right;
		t.right = n;
		updateHeight(n);
		updateHeight(t);
		return t;
	}
	
	private Node LeftRight(Node n) {
		Node t = LeftRot(n.left);
		n.left = t;
		return RightRot(n);
	}
	
	private Node RightLeft(Node n) {
		Node t = RightRot(n.right);
		n.right = t;
		return LeftRot(n);
	}
	
	public void add(int elem) {
		root = add(elem, root);
		size++;
	}
	
	private Node add(int elem, Node walker) {
		if (walker == null) {
			Node root = new Node(elem);
			return root;
		} else if (walker.element < elem) {
			walker.right = add(elem, walker.right);
			if (getHeight(walker.right) - getHeight(walker.left) == 2) {
				if (elem > walker.right.element)
					walker = LeftRot(walker);
				else
					walker = RightLeft(walker);
			}
		} else {
			walker.left =  add(elem, walker.left);
			if (getHeight(walker.left) - getHeight(walker.right) == 2) {
				if (elem < walker.left.element)
					walker = RightRot(walker);
				else
					walker = LeftRight(walker);
			}
		}
		updateHeight(walker);
		return walker;
	}
	
	private void updateHeight(Node n) {
		if (n != null)
			n.height = Math.max(getHeight(n.left), getHeight(n.right)) + 1;
	}
	
	public void remove(int elem) {
		root = remove(root, elem);
		size--;
	}
	
	private Node remove(Node walker, int elem) {
		if (walker == null)
			return null;
		else if (walker.element < elem) {
			walker.right =  remove(walker.right, elem);
		} else if (walker.element > elem) {
			walker.left = remove(walker.left, elem);
		} else
			walker = expunge(walker);
		if (walker == null)
			return null;
		return ReBalance(walker);
	}

	private Node expunge(Node doomed) {
	    if (doomed.left == null && doomed.right == null) {
	        return null;  // Leaf node, remove it
	    } else if (doomed.left == null) {
	        return doomed.right;  // Only right child, replace with it
	    } else if (doomed.right == null) {
	        return doomed.left;  // Only left child, replace with it
	    } else {
	        // Node has two children, replace with in-order successor
	        Node parent = doomed;
	        Node follower = doomed.right;
	        while (follower.left != null) {
	            parent = follower;
	            follower = follower.left;
	        }
	        
	        // Re-link the successor
	        if (parent != doomed) {
	            parent.left = follower.right;
	            follower.right = doomed.right;
	        }
	        follower.left = doomed.left;  // Connect the left subtree of doomed
	        
	        updateHeight(follower);  // Update the height of the successor
	        return ReBalance(follower);  // Re-balance the successor before returning
	    }
	}

	private Node ReBalance(Node walker) {
	    updateHeight(walker);  // Always update height before balancing
	    
	    if (getBalance(walker) < -1) {
	        if (getBalance(walker.right) <= 0)
	            walker = LeftRot(walker);
	        else
	            walker = RightLeft(walker);
	    } else if (getBalance(walker) > 1) {
	        if (getBalance(walker.left) >= 0)
	            walker = RightRot(walker);
	        else
	            walker = LeftRight(walker);
	    }
	    return walker;
	}
	
	public void print() {
		print(root);
		System.out.println();
	}
	
	private void print(Node walker) {
		if (walker != null) {
			System.out.print(walker.element + " ");
			print(walker.left);
			print(walker.right);
		}
	}
	
	public void printInOrder() {
		print(root);
		System.out.println();
	}
	
	private void printInOrder(Node walker) {
		if (walker != null) {
			printInOrder(walker.left);
			System.out.print(walker.element + " ");
			printInOrder(walker.right);
		}
	}
	
}
