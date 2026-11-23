import java.util.Arrays;

class TwoSum2InputArrayIsSorted {

    public static void main(String[] args) {

        int[] arr = { 2, 7, 11, 15 };
        int target = 9;

        System.out.println("Output : " + Arrays.toString(twoSum(arr, target)));

    }

    public static int[] twoSum(int[] numbers, int target) {

        int arrLen = numbers.length;

        int pointer1 = 0;
        int pointer2 = arrLen - 1;

        while (pointer2 > pointer1) {
            int sum = numbers[pointer2] + numbers[pointer1];
            if (sum > target) {
                pointer2 -= 1;
            } else if (sum < target) {
                pointer1 += 1;
            } else {
                return new int[] { pointer1 + 1, pointer2 + 1 };
            }

        }

        return new int[] { -1, -1 };
    }
}