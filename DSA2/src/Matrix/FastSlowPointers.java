package Matrix;

import java.util.HashMap;

public class FastSlowPointers {
	public static void main(String[] args) {
		ListNode head = new ListNode(-1);
		ListNode two = new ListNode(5);
		ListNode three = new ListNode(3);
		ListNode four = new ListNode(4);
		ListNode five = new ListNode(0);
//		ListNode six = new ListNode(66);
//		ListNode tail = new ListNode(77);
		head.next = two; 
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = null;
//		six.next = tail;
//		tail.next = null;
//		ListNode cycle = detectCycle(head);
//		System.out.println(cycle.val);
		ListNode walker = sortList(head);
		while (walker != null) {
			System.out.print(walker.val + " ");
			walker = walker.next;
		}
		System.out.println();
	}
	
	public static ListNode merge(ListNode head1, ListNode head2) {
		/*
		 * Leet-code 148's helper method
		 */
		
		// Initializing dummy node to keep track of head of new merged list.
		ListNode dummy = new ListNode(0);
		ListNode walker = dummy; // Starting walker at the dummy node.
		
		// Looping till one of the lists are fully merged.
		while (head1 != null && head2 != null) {
			// Check if head1's value is smaller.
			if (head1.val <= head2.val) {
				// Yes, we just link them to our new list, and
				walker.next = head1;
				head1 = head1.next; // move the pointer which has been linked.
			} else {
				// Else, we do the same with the other pointer.
				walker.next = head2;
				head2 = head2.next;
			}
			
			// Move our walker ahead each iteration.
			walker = walker.next;
		}
		
		// At the end, we see which list still has elements in it,
		// and link that remaining list to the our new list.
		walker.next = (head1 == null)? head2 : head1;
		
		// return dummy.next as that is the head of the new list.
		return dummy.next;
	}
	
	public static ListNode sortList(ListNode head) {
		/*
		 * Leet-code 148 (medium)
		 * Using recursive merge sort in this problem.
		 */
		// Base case: If our head is null (size = 0), or 
		// head.next = null (size = 1) just return head to the recursive call.
		if (head == null || head.next == null)
			return head;
		
		// Using slow and fast pointers to find middle node
		// Basically, breaking the list in half using prev as well.
		ListNode slow = head;
		ListNode fast = head;
		ListNode prev = null;
		while (fast != null && fast.next != null) {
			prev = slow;
			slow = slow.next;
			fast = fast.next.next;
		}
		prev.next = null; // Break the link between two lists
		
		// Two recursive calls sorting left half and right half
		ListNode head1 = sortList(head);
		ListNode head2 = sortList(slow);
		
		// After sorting both halves, merge both back in using merge method.
		return merge(head1, head2);
	}
	
	public static ListNode rotateList(ListNode head, int k) {
		/*
		 * Leet-code 61 (medium)
		 */
		
		// Edge case: If the head == null (empty list) or 
		// if head.next == null (Length = 1) just return head.
		if (head == null || head.next == null || k == 0)
			return head;
		
		// First we have to find the length of the list
		// in order to walk to the (length - k) node.
		ListNode walker1 = head;
		int length = 0;
		while (walker1 != null) {
			length++;
			walker1 = walker1.next;
		}
		
		// This will take care of the fact if k is greater than list's length.
		// If it is say 4, and length is 3. We just need to rotate 4%3=1 times
		k = k % length;
		// And if the k == length of the list, then we just return head back.
		// Because rotating the list length number of times return the original list.
		if (k == 0)		return head;
		
		// Now, we have to walk to the (length - k)th node to break the link.
		walker1 = head;
		int index = 1;
		while (index < (length - k)) {
			index++;
			walker1 = walker1.next;
		}
		
		// New head is the next node of our (Length - k)th node.
		ListNode newhead = walker1.next;
		walker1.next = null; // Break the link between two nodes.
		walker1 = newhead;
		// Then we walk to the end of the new list (broken)
		while (walker1.next != null) {
			walker1 = walker1.next;
		}
		// And just attach the last node with previous list's head.
		walker1.next = head;
		
		// return new-head which is the head of the new list now.
		return newhead;
	}
	
	public static void reorder(ListNode head) {
		/*
		 * Leet-code 143 (Medium)
		 */
		/*
		 * Three steps for this problem:
		 * 1. Find the middle node using fast and slow pointers.
		 * 2. Reverse the second half of the list.
		 * 3. Merge the both list properly to satisfy the problem
		 */
		// Step-1: prevSlow is important here because
		// it will break the link between the last node of first half
		// and the slow pointer (middle node).
		ListNode slow = head;
		ListNode fast = head;
		ListNode prevSlow = null;
		while (fast != null && fast.next != null) {
			prevSlow = slow;
			slow = slow.next;
			fast = fast.next.next;
		}
		
		// Step-2: Reverse the linked list using prev
		// and current pointers, where slow points at middle.
		prevSlow.next = null;
		ListNode current = slow;
		ListNode prev = null;
		while (current != null) {
			ListNode ahead = current.next;
			current.next = prev;
			prev = current;
			current = ahead;
		}
		
		/*
		 * Step-3: We'll need two walkers for merging that would traverse
		 * both lists, simultaneously.
		 */
		ListNode walker1 = head;
		ListNode walker2 = prev;
		// Our loop condition stops the loop at the last nodes of both lists,
		// so that we can join the remaining nodes together at the end.
		while (walker1.next != null && walker2.next != null) {
			ListNode ahead1 = walker1.next; // pointer for next node in list1
			ListNode ahead2 = walker2.next; // pointer for next node in list2
			walker1.next = walker2; // walker1's link goes to walker2
			walker2.next = ahead1; // walker2's link goes to walker1's next node. (Draw this)
			// Just update both pointers at the end
			walker1 = ahead1;
			walker2 = ahead2;
		}
		// Link the end of list1 to the end of list2.
		walker1.next = walker2;
	}
	
	public static ListNode removeNthFromEnd(ListNode head, int n) {
		/*
		 * Leet-code 19 (Medium)
		 */
		
		// Using two pointers slow and fast at the head.
		ListNode slow = head;
		ListNode fast = head;
		// counter i to move fast pointer n steps ahead
		int i = 0;
		while (fast != null && i < n) {
			fast = fast.next;
			i++;
		}
		
		// If fast == null, means the n is the length of
		// the list, meaning removing head.
		if (fast == null)
			// return head's next node.
			return head.next;
		
		// Then we just move both pointers ahead till end 
		while (fast.next != null) {
			slow = slow.next;
			fast = fast.next;
		}
		
		// Then removing the middle node cuz slow would
		// be the previous node of the nth node.
		slow.next = slow.next.next;
		
		return head;
	}
	
	public static int findDuplicate(int[] nums) {
		/*
		 * Leet-code 287 (Medium)
		 */
		// This is an array version of detect cycle problem.
		// In this case, the pointers will point to the index,
		// taken as the element in their current place.
		// Starting both pointers at nums[0]
		int slow = nums[0];
		int fast = nums[0];
		
		// Using do-while here because we have to move the pointers
		// once before checking, then keep on going to see where the cycle happens
		do {
			slow = nums[slow]; // slow = slow.next
			fast = nums[nums[fast]]; // fast = fast.next.next
		} while (slow != fast);
		
		// Starting slow at nums[0] again and keep looping until we find duplicate.
		slow = nums[0];
		while (slow != fast) {
			// Moving one step at a time.
			slow = nums[slow];
			fast = nums[fast];
		}
		
		// Return slow as this will be the duplicate.
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
