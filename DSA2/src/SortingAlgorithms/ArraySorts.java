package SortingAlgorithms;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ArraySorts {

	public static void main(String[] args) {
		int[] nums1 = {3, 2, 1, 5, 6, 4};
		quickSort(nums1);
		//int[] nums1 = {5, 4, 6, 9, 8, 7, 6, 2};
		//int[] nums1 = {17, 743, 672, 780, 917, 743, 623, 288, 432, 281, 76};
		int[] nums2 = generateRandomArray(1000000);
		System.out.println("Sorting...");
		long time = System.currentTimeMillis();
		//mergeSort(nums2);
		LSDradixSort(nums2, 3);
		System.out.println("Done!\nTime: "+(System.currentTimeMillis() - time + " ms"));
		System.out.println("Is sorted? "+isSorted(nums2));
		System.out.println(comparisons + " comparisons"+ ", " + swaps + " swaps.");
		for (int i : nums1) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	private static long comparisons;
	private static long swaps;
	private static Random rand = new Random();
	
	public static boolean isSorted(int[] nums) {
		for (int i = 1; i < nums.length; i++) {
			if (nums[i-1] > nums[i])
				return false;
		}
		return true;
	}
	
	public static void swap(int[] arr, int ind1, int ind2) {
		int temp = arr[ind1];
		arr[ind1] = arr[ind2];
		arr[ind2] = temp;
		swaps++;
	}
	
	public static int quickSelect(int[] nums, int k) {
		return quickSelect(nums, 0, nums.length-1, k);
	}
	
	private static int quickSelect(int[] nums, int start, int end, int k) {
		int j = partition(nums, start, end);
		if (j == k-1)
			return nums[j];
		if (j > k-1)
			return quickSelect(nums, start, j - 1, k);
		else
			return quickSelect(nums, j + 1, end, k);
	}
	
	public static void LSDradixSort(int[] nums, int k) {
		List<Integer>[] buckets = new LinkedList[19];
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new LinkedList<>();
		}
		int base = 1;
		while (k-- > 0) {
			for (int num : nums) {
				int digit = (num / base) % 10;
				buckets[digit + 9].add(num);
			}
			int index = 0;
			for (List<Integer> bucket:  buckets) {
				while (!bucket.isEmpty()) {
					nums[index++] = (int) bucket.removeFirst();
				}
			}
			base *= 10;
		}
	}
	
	
	public static void quickSort(int[] nums) {
		quickSort(nums, 0, nums.length-1);
	}
	
	private static void quickSort(int[] nums, int start, int end) {
		if (start >= 0 && end >= 0 && start < end) {
			int pivot = partition(nums, start, end);
			quickSort(nums, start, pivot-1);
			quickSort(nums, pivot+1, end);
		}
	}
	
	private static int partition(int[] nums, int start, int end) {
		int pivot = end;
		int max = start-1;
		for (int i = start; i <= end; i++) {
			comparisons++;
			if (nums[i] <= nums[pivot]) {
				max++;
				swap(nums, i, max);
			}
		}
		return max;
	}
	
	public static void mergeSort(int[] nums) {
		mergeSort(nums, 0, nums.length-1);
	}
	
	private static void mergeSort(int[] nums, int start, int end) {
		if (start < end) {
			int mid = (start+end)/2;
			mergeSort(nums, start, mid);
			mergeSort(nums, mid + 1, end);
			merge(nums, start, mid , end);
		}
	}
	
	private static void merge(int[] nums, int start, int mid, int end) {
		int[] array = new int[end-start+1];
		int left = start;
		int right = mid+1;
		for (int i = 0; i < nums.length; i++) {
			comparisons++;
			if (left <= mid && right <= end) {
				if (nums[left] < nums[right]) {
					array[i] = nums[left++];
				} else {
					array[i] = nums[right++];
				}
			} else if (left <= mid) {
				array[i] = nums[left++];
			} else if (right <= end) {
				array[i] = nums[right++];
			}
		}
		for (int i = 0; i < array.length; i++) {
			nums[start++] = array[i];
		}
	}
	
	public static void cocktailShakerSort(int[] nums) {
		int start = 0, end = nums.length-1, lastSwap = 0;
		while (start <  end) {
			lastSwap = start;
			for (int i =  start; i < end; i++) {
				comparisons++;
				if (nums[i] > nums[i+1]) {
					swap(nums, i, i+1);
					lastSwap = i;
				}
			}
			end = lastSwap;
			for (int i = end; i < start; i--) {
				comparisons++;
				if (nums[i] < nums[i-1]) {
					swap(nums, i, i-1);
					lastSwap = i;
				}
			}
		}
	}
	
	public static void selectionSort(int[] nums) {
		for (int i = nums.length-1; i > 0; i--) {
			int max = i;
			for (int j = i; j >= 0; j--) {
				if (nums[j] > nums[max]) {
					max = j;
				}
				comparisons++;
			}
			swap(nums, i, max);
		}
	}
	
	public static void insertionSort(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			int index = i;
			comparisons++;
			while (index != 0 && nums[index] < nums[index-1]) {
				swap(nums, index, index-1);
				comparisons++;
				index--;
			}
		}
	}
	
	public static void bubbleSort(int[] nums) {
		int stop = nums.length-1;
		while (stop != 0) {
			int i = 0;
			int lastSwap = 0;
			while (i < stop) {
				comparisons++;
				if (nums[i] > nums[i+1]) {
					swap(nums, i, i+1);
					swaps++;
					lastSwap = i;
				}
				i++;
			}
			stop = lastSwap;
		}
	}

	public static int[] generateRandomArray(int length) {
		int[] a = new int[length];
		for(int i=0; i<length; i++)
			a[i] = rand.nextInt(length*10);
		return a;
	}

}
