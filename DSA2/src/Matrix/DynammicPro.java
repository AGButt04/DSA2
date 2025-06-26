package Matrix;

import java.util.HashMap;

public class DynammicPro {

	public static void main(String[] args) {
		int[] cost = {2, 5, 7, 8, 10};
		int n = 5;
		String st = "applepenapple";
		String[] words = {"apple", "pen"};
		String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab\"";
		String[] wordDict = {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"};
//		System.out.println(s.indexOf(wordDict[0]));
//		HashMap<String, Boolean> memo = new HashMap<>();
		System.out.println(wordbreakT(s, wordDict));
//		HashMap<Integer, Integer> map = new HashMap<>();
//		System.out.println(robTabulation(cost));
		//int num = rodCutting(cost, n);
//		int steps = climb(45, map);
//		System.out.println(steps);
	}
	
	public static boolean wordbreakT(String s, String[] wordDict) {
		Boolean[] memo = new Boolean[s.length()+1];
		for (int i = 0; i < memo.length; i++) {
			memo[i] = false;
		}
		memo[0] = true;
		for (int i = 0; i <= s.length(); i++) {
			if (memo[i] == true) {
				for (String word : wordDict) {
					if (i + word.length() <= s.length()) {
					    String st = s.substring(i, i + word.length());
					    if (st.equals(word)) {
					        memo[i + word.length()] = true;
					    }
					}
				}
			}
		}
		return memo[s.length()];
	}
	
	public static boolean wordBreakR(String s, String[] wordDict, HashMap<String, Boolean> memo) {
		if (memo.containsKey(s))
			return memo.get(s);
		if (s.equals(""))
			return true;
		
		for (String str : wordDict) {
			if (s.indexOf(str) == 0) {
				String sliced = s.substring(str.length(), s.length());
				if (wordBreakR(sliced, wordDict, memo) == true) {
					memo.put(sliced, true);
					return true;
				}	
			}
		}
		memo.put(s, false);
		return false;
	}
	
	public static int robRecursive(int[] nums, int index, HashMap<Integer, Integer> dp) {
		if (dp.containsKey(index))
			return dp.get(index);
		if (index >= nums.length)
			return 0;
		int option1 = robRecursive(nums, index + 1, dp);
		int option2 = nums[index] + robRecursive(nums, index + 2, dp);
		int result = Math.max(option1, option2);
		dp.put(index, result);
		return result;
	}
	
	public static int robTabulation(int[] nums) {
		int[] dp = new int[nums.length];
		dp[0] = nums[0];
		dp[1] = Math.max(nums[0], nums[1]);
		for (int i = 2; i < dp.length; i++) {
			int option1 = nums[i] + dp[i-2];
			int option2 = dp[i-1];
			dp[i] = Math.max(option1, option2);
		}
		return dp[nums.length-1];
	}
	
	public static int fib(int n) {
		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;
		else {
			int[] dp = new int[n + 1];
			dp[0] = 0;
			dp[1] = 1;
			for (int i = 2; i <= n; i++) {
				dp[i] = dp[i-1] + dp[i-2];
			}
			for (int i : dp) {
				System.out.print(i + " ");
			}
			System.out.println();
			return dp[n];
		}
	}
	
	public static int climbStairs(int n) {
		int[] dp = new int[n + 1];
		dp[0] = 1;
		dp[1] = 1;
		for (int i = 2; i <= n; i++) {
			dp[i] = dp[i - 1] + dp[i - 2];
		}
		return dp[n];
	}
	
	public static int minCostClimbingStairs(int[] cost) {
		int n = cost.length;
		int[] dp = new int[n];
		dp[0] = cost[0];
		dp[1] = cost[1];
		for (int i = 2; i < n; i++) {
			dp[i] = cost[i] + Math.min(dp[i-1], dp[i-2]);
		}
		return Math.min(dp[n-1], dp[n-2]);
	}
	
	public static int LGSum(int[] array) {
		int max  = array[0],cmax = array[0];
		for (int i = 0; i < array.length; i++) {
			int sum = cmax + array[i];
			cmax = Math.max(cmax, sum);
			max = Math.max(cmax, max);
		}
		return max;
	}
	
	public static int climb(int n, HashMap<Integer, Integer> map) {
		if (map.containsKey(n))
			return map.get(n);
		if (n == 1 || n == 0)
			return 1;
		
		
		map.put(n, climb(n - 1, map) + climb(n-2, map));
		return map.get(n);
	}
	
	public static int LISubsequence(int[] array) {
		int n = array.length;
		int[] dp = new int[n];
		dp[0] = 1;
		for (int i = 1; i < n; i++) {
			if (array[i] > array[i - 1])
				dp[i] = dp[i-1] + 1;
			else
				dp[i] = dp[i-1];
		}
		return dp[n-1];
		
	}
	
	public static int rodCutting(int[] prices, int length) {
		int n = prices.length;
		int[] dp = new int[length + 1];
		dp[0] = 0;
		for (int i = 1; i <= n; i++) {
			int profit = Integer.MIN_VALUE;
			for (int j = 1; j <= i; j++) {
				profit = Math.max(profit, prices[j-1] + dp[i-j]);
			}
			dp[i] = profit;
		}
		return dp[n];
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
