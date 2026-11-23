# Two-Pointer Pattern: The 3Sum Family in Java

All three problems in the **3Sum family** follow the exact same **O(n²) time, O(1) auxiliary space** blueprint:
1. **Sort the array (`Arrays.sort(arr)`)** so elements are ordered from smallest to largest.
2. **Fix one anchor element (`i`)** using an outer loop from `0` to `arrLen - 3`.
3. **Squeeze two pointers (`pointer1 = i + 1` and `pointer2 = arrLen - 1`)** inward from both ends of the remaining subarray.

Because the array is sorted, moving `pointer1++` is guaranteed to **increase** the sum, and moving `pointer2--` is guaranteed to **decrease** the sum. The only difference across the three problems is **how you handle duplicates, track results, and move `pointer1` and `pointer2`**.

---

## 1. `ThreeSum` (`sum == 0`) — *Find All Unique Triplets*

### Core Goal
Find all **unique** triplets `[nums[i], nums[pointer1], nums[pointer2]]` that sum to `0` and store them in `resList`.

### Walkthrough with Your Input (`nums = { -1, 0, 1, 2, -1, -4 }`)
1. **After `Arrays.sort(nums)`:** `{-4, -1, -1, 0, 1, 2}`
2. **Anchor `i = 0` (`nums = -4`):**
   * `pointer1 = 1` (`-1`), `pointer2 = 5` (`2`).
   * Even with the largest values, `-4 + 1 + 2 = -1 < 0`, so `pointer1` moves all the way right until `pointer1 == pointer2`. No triplets found.
3. **Anchor `i = 1` (`nums = -1`):**
   * **Step A:** `pointer1 = 2` (`-1`), `pointer2 = 5` (`2`).
     * `sum = -1 + (-1) + 2 = 0` -> **Match!** Add `[-1, -1, 2]` to `resList`.
     * Because the array is sorted, duplicate values sit next to each other. The `while` loops skip any repeated values at `pointer1` or `pointer2`, and then `pointer1++` and `pointer2--` move both inward.
   * **Step B:** Now `pointer1 = 3` (`0`), `pointer2 = 4` (`1`).
     * `sum = -1 + 0 + 1 = 0` -> **Match!** Add `[-1, 0, 1]` to `resList`.
     * Move `pointer1++` (`4`) and `pointer2--` (`3`). Loop ends (`pointer1 >= pointer2`).
4. **Anchor `i = 2` (`nums = -1`):**
   * `nums == nums` (`-1 == -1`), so `if (i > 0 && nums[i] == nums[i - 1]) continue;` skips it to prevent duplicate triplets.
5. **Final Output:** `[[-1, -1, 2], [-1, 0, 1]]`

### Java Code (`ThreeSum.java`)
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {

    public static void main(String args) {
        int nums = { -1, 0, 1, 2, -1, -4 };
        System.out.println("Output : " + threeSum(nums));
    }

    public static List<List<Integer>> threeSum(int nums) {
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
    }
}
```

---

## 2. `ThreeSumClosest` (`min |sum - target|`) — *Track Best Distance*

### Core Goal
Find the triplet sum whose distance `currDiff = Math.abs(sum - target)` is smallest and return `closestSum`.

> **Important Note on Pointer Movement:** Updating `minDiff` and moving `pointer1`/`pointer2` must be **two separate `if` blocks**. If you chain `else if (sum < target)` directly after `if (currDiff < minDiff)`, neither pointer moves whenever a closer sum is found, causing an infinite loop.

### Walkthrough with Your Input (`nums = { -1, 2, 1, -4 }`, `target = 1`)
1. **After `Arrays.sort(nums)`:** `{-4, -1, 1, 2}`
2. **Anchor `i = 0` (`nums = -4`):**
   * **Step A:** `pointer1 = 1` (`-1`), `pointer2 = 3` (`2`).
     * `sum = -4 + (-1) + 2 = -3`.
     * `currDiff = Math.abs(-3 - 1) = 4`. Since `4 < minDiff` (`Integer.MAX_VALUE`), `minDiff = 4` and `closestSum = -3`.
     * Since `sum (-3) < target (1)`, moving `pointer2` left would only make the sum smaller (further from `1`). Sorting guarantees `pointer1++` is the only direction that gets closer to `1`.
   * **Step B:** `pointer1 = 2` (`1`), `pointer2 = 3` (`2`).
     * `sum = -4 + 1 + 2 = -1`.
     * `currDiff = Math.abs(-1 - 1) = 2`. Since `2 < 4`, `minDiff = 2` and `closestSum = -1`.
     * `sum (-1) < target (1)` -> `pointer1++` (`3`), loop ends.
3. **Anchor `i = 1` (`nums = -1`):**
   * `pointer1 = 2` (`1`), `pointer2 = 3` (`2`).
   * `sum = -1 + 1 + 2 = 2`.
   * `currDiff = Math.abs(2 - 1) = 1`. Since `1 < 2`, `minDiff = 1` and `closestSum = 2`.
   * `sum (2) > target (1)` -> `pointer2--` (`2`), loop ends.
4. **Final Output:** `2`

### Java Code (`ThreeSumClosest.java`)
```java
import java.util.Arrays;

public class ThreeSumClosest {

    public static void main(String args) {
        int nums = new int { -1, 2, 1, -4 };
        int target = 1;

        System.out.println("Output : " + threeSumClosest(nums, target));
    }

    public static int threeSumClosest(int nums, int target) {
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

                // 1. Always check if this sum is closer to target
                if (currDiff < minDiff) {
                    minDiff = currDiff;
                    closestSum = sum;
                }

                // 2. Separately move pointers toward target
                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    pointer1++;
                } else {
                    pointer2--;
                }
            }
        }

        return closestSum;
    }
}
```

---

## 3. `TripletsWithSmallerSum` (`total < sum`) — *Bulk-Count Valid Pairs*

### Core Goal
Count all index triplets `(i, pointer1, pointer2)` where `arr[i] + arr[pointer1] + arr[pointer2] < sum`.

### Walkthrough with Your Inputs
#### Test Case 1: `arr = { -2, 0, 1, 3 }`, `sum = 2`
* **Sorted `arr`:** `{-2, 0, 1, 3}`
* **Anchor `i = 0` (`arr = -2`):**
  * `pointer1 = 1` (`0`), `pointer2 = 3` (`3`) -> `total = -2 + 0 + 3 = 1 < 2`.
  * Since `3` works, every element left of `pointer2` (`1` at index `2`) also works with `(-2, 0)`:
    * `(-2, 0, 3)` -> `1 < 2`
    * `(-2, 0, 1)` -> `-1 < 2`
  * `count += (3 - 1)` adds **2** triplets at once, then `pointer1++` (`2`).
  * Next: `pointer1 = 2` (`1`), `pointer2 = 3` (`3`) -> `total = -2 + 1 + 3 = 2` (not `< 2`), so `pointer2--` (`2`). Loop ends.
* **Anchor `i = 1` (`arr = 0`):** `0 + 1 + 3 = 4 >= 2` -> `pointer2--`.
* **Final Output:** `2`

#### Test Case 2: `arr2 = { 5, 1, 3, 4, 7 }`, `sum2 = 12`
* **After `Arrays.sort(arr2)`:** `{1, 3, 4, 5, 7}`
* **Anchor `i = 0` (`arr2 = 1`):**
  * **Step A:** `pointer1 = 1` (`3`), `pointer2 = 4` (`7`) -> `total = 1 + 3 + 7 = 11 < 12`.
    * Because `7` already keeps `total < 12`, every smaller number to its left (`5` and `4`) will also work with `(1, 3)`:
      * `(1, 3, 7)` -> `11 < 12`
      * `(1, 3, 5)` -> `9 < 12`
      * `(1, 3, 4)` -> `8 < 12`
    * `count += (4 - 1)` counts all **3** triplets in **O(1)** time, then `pointer1++` (`2`).
  * **Step B:** `pointer1 = 2` (`4`), `pointer2 = 4` (`7`) -> `total = 1 + 4 + 7 = 12` (not `< 12`), so `pointer2--` (`3`).
  * **Step C:** `pointer1 = 2` (`4`), `pointer2 = 3` (`5`) -> `total = 1 + 4 + 5 = 10 < 12`.
    * `count += (3 - 2)` adds **1** triplet `(1, 4, 5)`, then `pointer1++` (`3`). Loop ends.
* **Anchor `i = 1` (`arr2 = 3`):**
  * `pointer1 = 2` (`4`), `pointer2 = 4` (`7`) -> `3 + 4 + 7 = 14 >= 12` -> `pointer2--` (`3`).
  * `pointer1 = 2` (`4`), `pointer2 = 3` (`5`) -> `3 + 4 + 5 = 12 >= 12` -> `pointer2--` (`2`). Loop ends.
* **Final Output:** `4` (`3 + 1`)

### Java Code (`TripletsWithSmallerSum.java`)
```java
import java.util.Arrays;

public class TripletsWithSmallerSum {

    public static void main(String args) {
        int sum = 2;
        int arr = { -2, 0, 1, 3 };
        System.out.println("Output : " + countTriplets(sum, arr));

        int sum2 = 12;
        int arr2 = { 5, 1, 3, 4, 7 };
        System.out.println("Output : " + countTriplets(sum2, arr2));
    }

    public static int countTriplets(int sum, int arr) {
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
```

---

## Quick Comparison Table

| Problem | Goal | Skip Duplicates? | When Condition Matches | Time / Space |
| :--- | :--- | :--- | :--- | :--- |
| **`ThreeSum`** | Return unique triplets where `sum == 0` | **Yes** (`i`, `pointer1`, `pointer2`) | Add to `resList`, skip duplicates, move **both** `pointer1++` & `pointer2--` | **O(n²)** / **O(1)** aux |
| **`ThreeSumClosest`** | Return `closestSum` with min `\|sum - target\|` | Optional (`i` only) | Update `minDiff` & `closestSum`; separately move `pointer1++` or `pointer2--` | **O(n²)** / **O(1)** aux |
| **`TripletsWithSmallerSum`** | Count triplets where `total < sum` | **No** (indices matter) | Add `(pointer2 - pointer1)` to `count`, move **only** `pointer1++` | **O(n²)** / **O(1)** aux |
