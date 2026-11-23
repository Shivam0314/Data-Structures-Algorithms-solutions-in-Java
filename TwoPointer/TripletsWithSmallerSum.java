import java.util.Arrays;

public class TripletsWithSmallerSum {

    public static void main(String[] args) {
        int sum = 2;
        int arr[] = { -2, 0, 1, 3 };
        System.out.println("Output : " + countTriplets(sum, arr));

        int sum2 = 12;
        int arr2[] = { 5, 1, 3, 4, 7 };
        System.out.println("Output : " + countTriplets(sum2, arr2));

    }

    public static int countTriplets(int sum, int arr[]) {

        Arrays.sort(arr);
        int arrLen = arr.length;
        int count = 0;

        for (int i = 0; i < arrLen - 2; i++) {

            int pointer1 = i + 1;
            int pointer2 = arrLen - 1;

            while (pointer1 < pointer2) {
                int total = arr[i] + arr[pointer1] + arr[pointer2];

                if (total < sum) {
                    count += (pointer2 - pointer1);
                    pointer1++;
                } else {
                    pointer2--;
                }

            }

        }
        
        return count;

    }

}
