import java.util.Arrays;

public class Segregate0s1sand2s {

    public static void main(String[] args) {

        int[] arr = { 0, 1, 2, 0, 1, 2 };
        System.out.println("Output : " + Arrays.toString(sort012(arr)));

    }

    public static int[] sort012(int[] arr) {

        int arrLen = arr.length;
        int low = 0;
        int mid = 0;
        int high = arrLen - 1;

        while (mid <= high) {
            if (arr[mid] == 0) {
                swapNumbers(arr, mid, low);
                low++;
                mid++;
            } else if (arr[mid] == 1) {
                mid++;
            } else {
                swapNumbers(arr, mid, high);
                high--;
            }
        }

        return arr;

    }

    public static void swapNumbers(int[] arr, int num1, int num2) {
        // arr[num2] = arr[num1] + arr[num2] - (arr[num1] = arr[num2]);
        int temp = arr[num1];
        arr[num1] = arr[num2];
        arr[num2] = temp;

    }

}
