package Trees;


public class BSTree {
	public static void main(String[] args) {
		BSTree tree = new BSTree();
		System.out.print("Nums: ");
		int[] a = {20,30,15,40,35,10,37,5,38,39,25};
		for (int i : a) {
			tree.add(i);
		}
		tree.print();
		System.out.println(tree.tHeight());
		tree.remove(40);
		tree.printInOrder();
	}
	
	private class Node {
		int element;
		Node right;
		Node left;
	}
	
	int size;
	Node root;
	
	public void add(int elem) {
		root = add(root, elem);
		size++;
	}
	
	private Node add(Node walker, int elem) {
		if (walker == null) {
			Node root = new Node();
			root.element = elem;
			return root;
		} else if (walker.element < elem) {
			walker.right = add(walker.right, elem);
			return walker;
		} else {
			walker.left = add(walker.left, elem);
			return walker;
		}
	}
	
	private Node expunge(Node doomed) {
		if (doomed.left == null && doomed.right == null) 
			return null;
		else if (doomed.left == null)
			return doomed.right;
		else if (doomed.right == null)
			return doomed.left;
		else {
			Node parent = doomed;
			Node follower = doomed.right;
			while (follower.left != null) {
				parent = follower;
				follower = follower.left;
			}
			if (parent != doomed) {
				parent.left = follower.right;
				follower.left = doomed.left;
				follower.right = doomed.right;
			} else
				follower.left = doomed.left;
			return follower;
		}
	}
	
	public boolean contains(int elem) {
		Node walker = root;
		while (walker != null) {
			if (walker.element == elem)
				return true;
			if (walker.element < elem)
				walker = walker.right;
			else
				walker = walker.left;
		}
		return false;
	}
	
	public void remove(int elem) {
		if (root.element == elem)
			root = expunge(root);
		else {
			Node walker = root;
			Node follower = null;
			while (walker != null && walker.element != elem) {
				follower = walker;
				if (walker.element < elem)
					walker = walker.right;
				else
					walker = walker.left;
			}
			if (walker != null && walker == follower.left)
				follower.left = expunge(walker);
			else if (walker != null)
				follower.right = expunge(walker);
			}
	}
	
	
	
	public void removeLeaf(int elem) {
		Node walker = root;
		Node follower = null;
		
		while (walker != null && walker.element != elem) {
			follower = walker;
			if (walker.element < elem)
				walker = walker.right;
			else
				walker = walker.left;
		}
		if (follower != null && follower.element < elem)
			follower.right = null;
		else if (follower != null)
			follower.left = null;
	}
	public int tHeight() {
		return tHeight(root);
	}
	
	private int tHeight(Node walker) {
		if (walker == null)
			return -1;
		else
			return Math.max(tHeight(walker.right), tHeight(walker.left)) + 1;
	}
	
	public void print() {
		print(root);
		System.out.println();
	}
	
	public void printInOrder() {
		printInOrder(root);
		System.out.println();
	}
	
	private void print(Node walker) {
		if (walker != null) {
			System.out.print(walker.element + " ");
			print(walker.left);
			print(walker.right);
		}
	}

	private void printInOrder(Node walker) {
		if (walker != null) {
			printInOrder(walker.left);
			System.out.print(walker.element + " ");
			printInOrder(walker.right);
		}
	}
}
