
class RemoveDuplicateFromSortedList {

    public static void main(String[] args) {

        int[] arr = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4 };

        System.out.println("Output : " + removeDuplicates(arr));

    }

    public static int removeDuplicates(int[] nums) {

        int p1 = 0;
        int p2 = 1;
        int arrLen = nums.length;

        while (p2 < arrLen) {
            if (nums[p2] == nums[p2 - 1]) {
                p2++;

            } else {
                p1++;
                nums[p1] = nums[p2];
                p2++;
            }
        }

        return p1 + 1;
    }
}