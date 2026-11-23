import java.util.Arrays;

public class Segregate0sand1s {

    public static void main(String[] args) {

        int[] arr = { 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1 };

        System.out.println(Arrays.toString(segregateZerosAndOnes(arr)));
    }

    public static int[] segregateZerosAndOnes(int[] arr) {

        int p1 = 0;
        int p2 = 0;

        while (p2 < arr.length) {
            if (arr[p2] == 0) {
                arr[p1] = arr[p2] + arr[p1] - (arr[p2] = arr[p1]);
                p1++;
            }
            p2++;
        }
        return arr;
    }
}
