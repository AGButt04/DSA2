package Matrix;

import java.util.HashMap;

public class FastSlowPointers {
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
		int[] nums = {1,3,4,2,2};
		System.out.println(findDuplicate(nums));
	}
	
	public static int findDuplicate(int[] nums) {
		/*
		 * Leet-code 287 (Medium)
		 */
		int slow = nums[0];
		int fast = nums[0];
		do {
			slow = nums[slow]; // slow = slow.next
			fast = nums[nums[fast]]; // fast = fast.next.next
		} while (slow != fast);
		
		slow = nums[0];
		while (slow != fast) {
			slow = nums[slow];
			fast = nums[fast];
		}
		
		return slow;
	}
	
	public static boolean isPalindrome(ListNode head) {
		/*
		 * Leet-code 234
		 */
		// Start two pointers, slow and fast at head.
		ListNode slow = head;
		ListNode fast = head;
		
		// Loop while the fast.next and fast != null
		while (fast != null && fast.next != null) {
			// Move both pointers, to the find the middle node
			slow = slow.next;
			fast = fast.next.next;
		}
		
		// Start a prev node as null and current at slow (middle node)
		ListNode prev = null;
		ListNode current = slow;
		
		// This loop will reverse the second half of the list.
		while (current != null) {
			ListNode ahead = current.next;
			current.next = prev;
			prev = current;
			current = ahead;
		}
		
		// Now, we can check if both divided lists are same.
		ListNode walker = head;
		while (prev != null) {
			// prev is at the head of the reversed half list,
			// and we just check if their values match, if don't return false
			if (walker.val != prev.val)
				return false;
			// If they do, move both pointers appropriately
			prev = prev.next;
			walker = walker.next;
		}
		
		// After the loop terminates that means, we have found
		// the list to be a Palindrome.
		return true;
	}
	
	public static ListNode middleNode(ListNode head) {
		/*
		 * Leet-code 876
		 */
		// Starting slow and fast pointers at head
		ListNode slow = head;
		ListNode fast = head;
		
		// Looping till fast and fast.next is not null
		while (fast != null && fast.next != null) {
			// Moving both pointers one and 2 nodes.
			slow = slow.next;
			fast = fast.next.next;
		}
		// When the fast pointer becomes null, slow
		// pointer will be at the middle node
		return slow;
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
	
	private static class ListNode {
		int val;
		ListNode next;
		
		ListNode(int x) {
			this.val = x;
			this.next = null;
		}
	}

	
		 

}
