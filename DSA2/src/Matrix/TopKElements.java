package Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKElements {

	public static void main(String[] args) {
		int[] nums = {1,2,3,4,5};
		int k = 4;
		List<Integer> answer = findClosestElements(nums, k, 3);
		for (int a : answer) {
			System.out.print(a + " ");
		}
		System.out.println();
	}
	
	public static List<Integer> findClosestElements(int[] arr, int k, int x) {
		/*
		 * Leet-code 658 (medium)
		 */
		/*
		 * Here, we are trying to find K closest element to x,
		 * considering array is sorted, easiest approach here
		 * would be defining the comparator function for minheap,
		 * and just add elements to it and pop k top elements
		 * because those are the k closest ones to x, as their distance is minimum.
		 */
		List<Integer> result = new ArrayList<>();
		PriorityQueue<Integer> minheap = new PriorityQueue<>((a,b) -> {\
			/*
			 * The comparator says compute the difference of both
			 * elements and check which one is smaller and return that.
			 */
			int diff1 = Math.abs(a - x);
			int diff2 = Math.abs(b - x);
			if (diff1 == diff2)
				// If the difference is same, we do comparison on the
				// number in question instead to see which one is smaller.
				return a - b;
			
			return diff1 - diff2;
		});
		// Simply add
		for (int i = 0; i < arr.length; i++) {
			minheap.offer(arr[i]);
		}
		// Simply remove k times
		for (int i = 0; i < k; i++) {
			result.add(minheap.poll());
		}
		// Sort the array, and return. However, this is not the most efficient solution.
		Collections.sort(result);
		return result;
	}
	
	public static int[][] kClosest(int[][] points, int k) {
		/*
		 * Leet-code 973 (medium)
		 */
		/*
		 * Here are trying to find k closest points to the origin.
		 * Using the same minheap approach with defining the comparator
		 * function for the heap so it knows which elements are smaller.
		 */
		int[][] result = new int[k][2];
		PriorityQueue<int[]> minheap = new PriorityQueue<>((a,b) -> {
			/*
			 * Compute the distance of points points from origin and 
			 * return their difference. If comparison is positive, that
			 * means distB is smaller and it'll give it priority as this
			 * is a minheap, and vice verse.
			 */
			int distA = a[0] * a[0] + a[1] * a[1];
			int distB = b[0] * b[0] + b[1] * b[1];
			return distA - distB;
		});
		// Simply add all points.
		for (int[] point : points) {
			minheap.offer(point);
		}
		// Simple remove k top points.
		for (int i = 0; i < k; i++) {
			result[i] = minheap.poll();
		}
		
		return result;
	}
	
	public static int findKthLargest(int[] nums, int k) {
		/*
		 * Leet-code 215 (Medium)
		 */
		/*
		 * This problem can be solved with using Min Heap.
		 * Where the kth-largest element is required.
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
