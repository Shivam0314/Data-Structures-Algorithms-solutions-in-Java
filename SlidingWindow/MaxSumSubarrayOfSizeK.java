package SlidingWindow;

public class MaxSumSubarrayOfSizeK {

    public static void main(String[] args) {

        int[] arr = {100, 200, 300, 400};
        int k = 2;

        System.out.println("Output : " + maxSubarraySum(arr, k));
        
    }



    public static int maxSubarraySum(int[] arr, int k) {

        int low = 0;
        int max = Integer.MIN_VALUE;
        
        while(low <= arr.length-k){
            int sum = 0;

            for(int high = low;high < low + k; high++){
                sum += arr[high];
            }
                max = Math.max(max, sum);
            low++;   
            
        }
        return max;
        
    }
    
}
