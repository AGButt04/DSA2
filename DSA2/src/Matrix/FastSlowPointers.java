package Matrix;


public class FastSlowPointers {
	private static class ListNode {
		int val;
		ListNode next;
		
		ListNode(int x) {
			this.val = x;
			this.next = null;
		}
	}

	public static void main(String[] args) {
		ListNode head = new ListNode(11);
		ListNode two = new ListNode(22);
		ListNode three = new ListNode(33);
		ListNode four = new ListNode(44);
		ListNode five = new ListNode(55);
		ListNode six = new ListNode(66);
		ListNode tail = new ListNode(77);
		head.next = two; 
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = tail;
		tail.next = three;
		ListNode cycle = detectCycle(head);
		System.out.println(cycle.val);
	}
	
	public static ListNode detectCycle(ListNode head) {
		/*
		 * Leet-code 142 (Medium)
		 */
		ListNode fast = head;
		ListNode slow = head;
		
		while (fast != null && fast.next != null) {
			fast = fast.next.next; // Move fast pointer 2 nodes
			slow = slow.next; // Move slow pointer 2 node
			
			// Check if both pointers point to the same node (Cycle)
			if (fast == slow)
				break; // If cycle found, break.
		}
		
		// Check if fast and fast.next == null, return null if yes.
		if (fast == null || fast.next == null)
			return null;
		
		// Move slow back to head to find cycle
		slow = head;
		while (slow != fast) {
			// Keep looping and moving both pointers until they're same.
			fast = fast.next;
			slow = slow.next;
		}
		// If loop terminates, then slow is where cycle starts.
		return slow;
		
	}
	
	public static boolean hasCycle(ListNode head) {
		/*
		 * Leet-code 141
		 */
		// Using fast and slow pointers
		ListNode fast = head;
		ListNode slow = head;
		
		// Loop will go until fast and fast.next is not null,
		// fast.next check is important because fast jumps 2 Nodes
		// ahead so next pointer should not be null.
		while (fast != null && fast.next != null) {
			fast = fast.next.next; // Move fast pointer 2 nodes
			slow = slow.next; // Move slow pointer 2 node
			
			// Check if both pointers point to the same node (Cycle)
			if (fast == slow)
				return true;
		}
		
		// If loop terminates, then there's no cycle.
		return false;
	}
	
	
		 

}
