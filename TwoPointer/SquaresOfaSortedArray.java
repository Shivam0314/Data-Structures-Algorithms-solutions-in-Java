import java.util.Arrays;

public class SquaresOfaSortedArray {

    public static void main(String[] args) {

        int[] nums = { -4, -1, 0, 3, 10 };

        System.out.println(Arrays.toString(sortedSquares(nums)));

    }

    public static int[] sortedSquares(int[] nums) {

        int arrLen = nums.length;
        int[] resArr = new int[arrLen];
        int p1 = 0;
        int p2 = arrLen - 1;
        int resIdx = arrLen - 1;

        while (p1 <= p2) {
            if (nums[p1] * nums[p1] < nums[p2] * nums[p2]) {
                resArr[resIdx] = nums[p2] * nums[p2];
                p2--;
            } else {
                resArr[resIdx] = nums[p1] * nums[p1];
                p1++;
            }
            resIdx--;
        }

        return resArr;

    }

}
