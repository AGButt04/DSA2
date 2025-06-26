package SortingAlgorithms;

import java.util.PriorityQueue;

public class BinarySearch {
	public static void main(String args[]) {
		int[] nums = {3,2,3,1,2,4,5,5,6};
		int target = 4;
		System.out.println(findKthLargest(nums, target));
	}
	
	public static int insertPosition(int[] nums, int target) {
		int left = 0;
		int right = nums.length-1;
		while (left <= right) {
			int mid = (left+right)/2;
			int num = nums[mid];
			if (num == target)
				return mid;
			else if (num < target)
				left = mid + 1;
			else
				right = mid - 1;
		}
		return left;
	}
	
	// Using Heap:
	public static int kthLargest(int[] nums, int k) {
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		for (int num : nums) {
			heap.add(num);
			if (heap.size() > k)
				heap.poll();
		}
		return heap.peek();
	}
	
	//Using quickSelect
	public static int findKthLargest(int[] nums, int k) {
	    int target = nums.length - k;
	    return quickSelect(nums, 0, nums.length - 1, target);
	}

	public static int quickSelect(int[] nums, int left, int right, int k) {
	    if (left == right) return nums[left]; // base case

	    int pivotIndex = partition(nums, left, right);

	    if (pivotIndex == k) return nums[pivotIndex];
	    else if (pivotIndex > k) return quickSelect(nums, left, pivotIndex - 1, k);
	    else return quickSelect(nums, pivotIndex + 1, right, k);
	}

	public static int partition(int[] nums, int left, int right) {
	    int randomIndex = left + (int)(Math.random() * (right - left + 1));
	    swap(nums, randomIndex, right);

	    int pivot = nums[right];
	    int storeIndex = left;

	    for (int i = left; i < right; i++) {
	        if (nums[i] <= pivot) {
	            swap(nums, storeIndex, i);
	            storeIndex++;
	        }
	    }

	    swap(nums, storeIndex, right); // place pivot in correct position
	    return storeIndex; // correct pivot index
	}

	public static void swap(int[] nums, int i, int j) {
	    int temp = nums[i];
	    nums[i] = nums[j];
	    nums[j] = temp;
	}
	
	

}
