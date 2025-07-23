package Matrix;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKElements {

	public static void main(String[] args) {
		int[] nums = {1,1,1,2,2,3};
		int k = 2;
		int[] answer = topKFrequent(nums, k);
		for (int a : answer) {
			System.out.print(a + " ");
		}
		System.out.println();
	}
	
	public static int findKthLargest(int[] nums, int k) {
		/*
		 * Leet-code 215 (Medium)
		 */
		/*
		 * This problem can be solved with using Min Heap.
		 * Where the kth largest element is required.
		 */
		PriorityQueue<Integer> minheap = new PriorityQueue<>();
		for (int n : nums) {
			/*
			 * Go over the array, and start adding the elements in
			 * the heap, as soon as the size of heap becomes larger
			 * than k, pop the top element which would be the minimum
			 * of that window of elements, and loop will finish, all the
			 * minimum number of elements less than kth largest element
			 * would have been popped and return the top of heap.
			 */
			minheap.offer(n);
			if (minheap.size() > k)
				minheap.poll();
		}
		return minheap.peek();
	}
	
	public static int[] topKFrequent(int[] nums, int k) {
		/*
		 * Leet-code 347 (Medium) O(n + n log(n))
		 */
		/*
		 * This problem is a little tricky, here we need the k most
		 * frequent elements, which can be achieved by using HashMap,
		 * where it would track frequencies of the numbers and max-heap
		 * will add the elements in the heap based on the frequency.
		 */
		int[] frequents = new int[k];
		HashMap<Integer, Integer> map = new HashMap<>();
		PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((a,b) -> b.getValue() - a.getValue());
		/*
		 * Go over the array and start adding the elements
		 * in the array, updating their count.
		 */
		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
		}
		/*
		 * Here we are adding the entries of the map as a whole
		 * and the max heap would take the value (frequency) as
		 * a comparator and order elements based on that.
		 */
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			heap.offer(entry);
		}
		/*
		 * Just pop the top k element as they are the most frequent ones.
		 */
		for (int i = 0; i < k; i++) {
			frequents[i] = heap.remove().getKey();
					
		}
		return frequents;
	}

}
