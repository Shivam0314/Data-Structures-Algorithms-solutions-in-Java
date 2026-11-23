
class RemoveDuplicateFromSortedArray2 {

    public static void main(String[] args) {
        int[] nums = { 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5 };

        System.out.println("Output : " + removeDuplicates(nums));

    }

    public static int removeDuplicates(int[] nums) {
        int arrLen = nums.length;

        if (arrLen <= 2) {
            return arrLen;
        }

        int p1 = 2;
        int p2 = 2;

        while (p2 < arrLen) {
            if (nums[p2] == nums[p1 - 2]) {
                p2++;
            } else {
                nums[p1] = nums[p2];
                p1++;
                p2++;
            }
        }

        return p1;

    }

}
