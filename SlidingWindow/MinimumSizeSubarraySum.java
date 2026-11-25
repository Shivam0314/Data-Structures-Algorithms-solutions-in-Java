package SlidingWindow;

public class MinimumSizeSubarraySum {


    public static void main(String[] args) {

        int[] nums = {2,3,1,2,4,3};
        int target = 7;

        System.out.println("Output : " + minSubArrayLen(target, nums));
        
    }


    public static int minSubArrayLen(int target, int[] nums) {
        int low = 0;
        int minLen = nums.length + 1;
        int sum = 0;


        for(int high = low; high < nums.length; high++){
            sum = sum + nums[high];

            while(sum >= target){
                int currLen = high - low + 1;
                minLen = Math.min(currLen, minLen);

                sum = sum - nums[low];
                low++;

            }
        }
         if (minLen > nums.length) {
            return 0;
        }
            return minLen;
    }

}
        

