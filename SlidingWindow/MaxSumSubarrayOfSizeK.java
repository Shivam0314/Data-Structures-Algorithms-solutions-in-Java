package SlidingWindow;

public class MaxSumSubarrayOfSizeK {

    public static void main(String[] args) {

        int[] arr = { 100, 200, 300, 400 };
        int k = 2;

        System.out.println("Output : " + maxSubarraySum(arr, k));

    }

    public static int maxSubarraySum(int[] arr, int k) {

        int low = 0;
        int high = k - 1;
        int sum = 0;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i <= high; i++) {
            sum = sum + arr[i];
        }

        while (high < arr.length) {
            max = Math.max(sum, max);
            low++;
            high++;

            if (high == arr.length) {
                break;
            }
            sum = sum - arr[low - 1] + arr[high];

        }
        return max;

    }

}
