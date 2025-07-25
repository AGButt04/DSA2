package Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKElements {

	public static void main(String[] args) {
		int[] nums = {1,2,3,4,5};
		String[] words = {"i","love","leetcode","i","love","coding"};
		int[][] matrix = {{1,5,9},{10,11,13},{12,13,15}};
		int[][] mat = {{-5}};
		int k = 4;
		System.out.println(kthsmallest(matrix, 6));
		List<String> answer = topKfrequent(words, 2);
		for (String a : answer) {
			System.out.print(a + " ");
		}
		System.out.println();
	}
	
	public static int kthSmallestMatrix(int[][] matrix, int k) {
		/*
		 * Leet-code 378 (Matrix Traversal)
		 */
		/*
		 * This is a faster version of kthSmallest, where we take advantage
		 * of the sorted property of the matrix and use a BFS-type approach
		 * to find the k smallest, as the next smallest number from the current
		 * location is either to the right or down, as both columns and rows are
		 * sorted, so we check each neighbor, and add them to heap and remove the
		 * top of the heap at each step. At the end (k-1) times, the top will be the target.
		 */
		PriorityQueue<int[]> minheap = new PriorityQueue<>((a, b) ->
			matrix[a[0]][a[1]] - matrix[b[0]][b[1]]);
		HashSet<String> visitedset = new HashSet<>(); // Sets can't see the diff between duplicate arrays so need strings.
		minheap.offer(new int[] {0,0});
		for (int i = 0; i < k-1; i++) {
			int[] indexes = minheap.poll();
			int[] right = new int[] {indexes[0], indexes[1]+1};
			String rightkey = right[0] + "," + right[1];
			int[] down = new int[] {indexes[0]+1, indexes[1]};
			String downkey = down[0] + "," + down[1];
			if (right[0] >= 0 && right[0] < matrix.length && right[1] >= 0 && right[1] < matrix[0].length) {
				if (!visitedset.contains(rightkey)) {
					visitedset.add(rightkey);
					minheap.offer(right);
				}
			}
			if (down[0] >= 0 && down[0] < matrix.length && down[1] >= 0 && down[1] < matrix[0].length) {
				if (!visitedset.contains(downkey)) {
					visitedset.add(downkey);
					minheap.offer(down);
				}
			}
		}
		return matrix[minheap.peek()[0]][minheap.peek()[1]];
	}
	
	public static int kthsmallest(int[][] matrix, int k) {
		/*
		 * Leet-code 378 (Medium)
		 */
		/*
		 * This one is a brute-force type solution, where we initialize
		 * a max-heap and keep on adding elements and as soon as heap's count
		 * goes bigger than k, we pop the element to keep the k window elements.
		 */
		PriorityQueue<Integer> maxheap = new PriorityQueue<>((a, b) -> b - a);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				maxheap.offer(matrix[i][j]);
				if (maxheap.size() > k)
					maxheap.poll();
			}
		}
		// return the top element.
		return maxheap.peek();
	}
	
	public static List<String> topKfrequent(String[] words, int k) {
		/*
		 * Leet-code 692 (Medium)
		 */
		/*
		 * This problem is the somewhat same as the top k frequent
		 * elements instead it requires list of k frequent words.
		 */
		List<String> result = new ArrayList<>();
		HashMap<String, Integer> map = new HashMap<>();
		/*
		 * Here, we also store the words and their frequency in the HashMap.
		 */
		for (int i = 0; i < words.length; i++) {
			map.put(words[i], map.getOrDefault(words[i], 0) + 1);
		}
		/*
		 * This is the key step as we define the comparator lambda function, where
		 * we are handling the min heap and max heap behavior, based on the condition.
		 */
		PriorityQueue<Map.Entry<String, Integer>> heap = new PriorityQueue<>((a, b) -> {
			// This handles max heap property, prioritizing high frequency words.
			int diff = b.getValue() - a.getValue();
			// If the frequency is not same, we just return difference.
			if (diff != 0) return diff;
				
			// Else, we compare the words in min heap fashion and prioritize
			// according to the lexicographical ordering.
			return a.getKey().compareTo(b.getKey());
		});
		
		/*
		 * Here same steps add the entries
		 */
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			heap.offer(entry);
		}
		// And remove the k frequent words from the heap.
		for (int i = 0; i < k; i++) {
			result.add(heap.poll().getKey());
		}
		return result;
	}
	
	public static List<Integer> findClosestElements(int[] arr, int k, int x) {
		/*
		 * Leet-code 658 (medium)
		 */
		/*
		 * Here, we are trying to find K closest element to x,
		 * considering array is sorted, easiest approach here
		 * would be defining the comparator function for min-heap,
		 * and just add elements to it and pop k top elements
		 * because those are the k closest ones to x, as their distance is minimum.
		 */
		List<Integer> result = new ArrayList<>();
		PriorityQueue<Integer> minheap = new PriorityQueue<>((a,b) -> {
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
