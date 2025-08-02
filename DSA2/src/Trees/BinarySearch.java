package Trees;

public class BinarySearch {

	public static void main(String[] args) {
		int[] nums = {5,7,7,8,8,10};
		int target = 8;
//		System.out.println(searchinsert(nums, target));
		int[] range = searchRange(nums, target);
		System.out.println(range[0] + "," + range[1]);

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
