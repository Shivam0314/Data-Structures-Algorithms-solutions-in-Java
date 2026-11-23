import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {

    public static void main(String[] args) {

        int[] nums = { -1, 0, 1, 2, -1, -4 };

        System.out.println("Output : " + threeSum(nums));

    }

    public static List<List<Integer>> threeSum(int[] nums) {

        int arrLen = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> resList = new ArrayList<>();

        for (int i = 0; i < arrLen - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int pointer1 = i + 1;
            int pointer2 = arrLen - 1;

            while (pointer1 < pointer2) {
                int sum = nums[i] + nums[pointer1] + nums[pointer2];

                if (sum == 0) {
                    resList.add(Arrays.asList(nums[i], nums[pointer1], nums[pointer2]));

                    while (pointer1 < pointer2 && nums[pointer1] == nums[pointer1 + 1]) {
                        pointer1++;
                    }
                    while (pointer1 < pointer2 && nums[pointer2] == nums[pointer2 - 1]) {
                        pointer2--;
                    }

                    pointer1++;
                    pointer2--;
                } else if (sum < 0) {
                    pointer1++;
                } else if (sum > 0) {
                    pointer2--;
                }
            }

        }

        return resList;

        // for(int i = 0; i < arrLen; i++){
        // for(int j = i + 1; j < arrLen; j++){
        // for (int k = j + 1;k < arrLen; k++){
        // if(nums[i] + nums[j] + nums[k] == 0){

        // }

        // }
        // }
        // }
    }
}
