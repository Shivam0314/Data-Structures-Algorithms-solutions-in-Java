import java.util.Arrays;

public class ThreeSumClosest {

    public static void main(String[] args) {

        int[] nums = new int[] { -1, 2, 1, -4 };
        int target = 1;

        System.out.println("Output : " + threeSumClosest(nums, target));

    }

    public static int threeSumClosest(int[] nums, int target) {

        Arrays.sort(nums);
        int arrLen = nums.length;
        int minDiff = Integer.MAX_VALUE;
        int closestSum = 0;

        for (int i = 0; i < arrLen - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int pointer1 = i + 1;
            int pointer2 = arrLen - 1;

            while (pointer1 < pointer2) {
                int sum = nums[i] + nums[pointer1] + nums[pointer2];

                int currDiff = Math.abs(sum - target);

                if (currDiff < minDiff) {
                    minDiff = currDiff;
                    closestSum = sum;
                } 
                
                if(sum == target){
                    return sum;
                }else if (sum < target) {
                    pointer1++;
                } else {
                    pointer2--;
                }
            }
        }

        return closestSum;

    }

}
