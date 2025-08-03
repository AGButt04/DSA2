package Trees;

public class BinarySearch {

	public static void main(String[] args) {
		int[] nums = {4,5,6,7,0,1,2};
		int[] nums2 = {5,1,2,3,4};
		int target = 3;
//		System.out.println(searchinsert(nums, target));
//		int num = searchRotated(nums, target);
//		System.out.println(num);
		System.out.println(findMin(nums2));

	}
	
    public static int findMin(int[] nums) {
    	/*
    	 * Leet-code 153 (Medium)
    	 */
    	/*
    	 * This problem is similar to the search in rotated array
    	 * but here we are looking to find the minimum element in
    	 * array and as array is rotated, it won't be at the front.
    	 */
    	int curr_min = Integer.MAX_VALUE;
    	int left = 0;
    	int right = nums.length-1;
    	
    	while (left <= right) {
    		/*
    		 * For this, the idea is the same, we keep on searching the
    		 * halves of the array based on our mid element, as does it
    		 * lie in the left half or the right half, and while doing
    		 * that we have to keep updating our current minimum with
    		 * the minimum of each half.
    		 */
    		int mid = (right+left)/2;
    		int num = nums[mid];
    		
    		if (nums[left] < num) {
    			/*
    			 * If left element is less than our minimum, that means
    			 * we will look on the left side, and update our minimum
    			 */
    			curr_min = Math.min(nums[left], curr_min);
    			left = mid + 1;
    		} else {
    			/*
    			 * Else if our mid element is greater than left element which
    			 * should be the minimum of current half, we just increment the
    			 * left pointer by one to jump to the next element, and update min.
    			 */
    			curr_min = Math.min(curr_min, num);
    			left += 1;
    		}
    		
    	}
    	
    	return curr_min;
    }

	
    public static int searchRotated(int[] nums, int target) {
    	/*
    	 * Leet-code 33 (Medium)
    	 */
    	/*
    	 * We are trying to find the number in a sorted rotated array,
    	 * that means the array has elements in order but in circular.
    	 * We will use a simple binary search with a little bit of 
    	 * modification, where we check to see in which sorted half
    	 * the element will be in, and search that part of the array.
    	 */
    	int left = 0;
    	int right = nums.length-1;
    	
    	while (left <= right) {
    		int mid = (right+left)/2;
    		int n = nums[mid];
    		
    		// If found the element, just return.
    		if (n == target)
    			return mid;
    		/*
    		 * Else, if the left element is less or equal to the current
    		 * mid element, that means we are in sorted half.
    		 */
    		if (nums[left] <= n) {
    			/*
    			 * We check to see where our element would fall in and search
    			 * that half. If the target is greater than left element of the
    			 * half and less than mid element that means its in this half, so
    			 * we just move the right pointer to the search left half, but if
    			 * one of these conditions are false, that mean we search the other half
    			 */
    			if (target >= nums[left] && target < n) {
    				right = mid - 1;
    			} else
    				left = mid + 1;
    		} else {
    			/*
    			 * The idea is the same here, we check to see if our element is in the
    			 * right half, if greater than mid element and less than right element.
    			 * If yes, search the right part, else search the left part of the array.
    			 */
    			if (target > n && target <= nums[right]) {
    				left = mid + 1;
    			} else
    				right = mid - 1;
    		}
    	}
    	
    	return -1;
    }

	
    public static int[] searchRange(int[] nums, int target) {
    	/*
    	 * Leet-code 34
    	 */
    	/*
    	 * This problem wants the first and the last index of
    	 * the target number in the array, and [-1,-1] if not found.
    	 */
    	if (nums.length == 0) return new int[] {-1,-1};
    	
    	/*
    	 * For that, we will need 2 searches, where when we find the target,
    	 * it keeps on finding for the start index by recursing on the left part.
    	 */
    	int left = 0, right = nums.length-1, start = -1;
    	while (left <= right) {
    		int mid = (right + left) / 2;
    		int n = nums[mid];
    		
    		if (n == target) {
    			start = mid;
    			right = mid-1;
    		} else if (n > target)
    			right = mid-1;
    		else
    			left = mid+1;
    	}
    	/*
    	 * Here, it does the same thing with the right part to find last index.
    	 */
    	int left1 = 0, right1 = nums.length-1, end = -1;
    	while (left1 <= right1) {
    		int mid = (right1 + left1) / 2;
    		int n = nums[mid];
    		
    		if (n == target) {
    			end = mid;
    			left1 = mid+1;
    		} else if (n > target)
    			right1 = mid-1;
    		else
    			left1 = mid+1;
    	}
    	
    	return new int[] {start, end};
    }

	
	public static int searchinsert(int[] nums, int target) {
		/*
		 * Leet-code 35
		 */
		int left = 0;
		int right = nums.length-1;
		
		while (left <= right) {
			int mid = (right+left) / 2;
			int n = nums[mid];
			
			if (n == target)
				return mid;
			else if (n > target)
				right = mid - 1;
			else
				left = mid+1;
		}
		
		return left;
	}
	
	public static int search(int[] nums, int target) {
		/*
		 * Leet-code 704
		 */
		int left = 0;
		int right = nums.length-1;
		
		while (left <= right) {
			int mid = (right + left) / 2;
			int num = nums[mid];
			
			if (num == target)
				return mid;
			else if (num > target)
				right = mid-1;
			else
				left = mid+1;
		}
		
		return -1;
	}
}
