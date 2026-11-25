# Two-Pointer Pattern: The 3Sum Family in Java

All three problems in the **3Sum family** follow the exact same **O(n²) time, O(1) auxiliary space** blueprint:
1. **Sort the array (`Arrays.sort`)** so numbers are arranged from smallest to largest.
2. **Fix one anchor element (`i`)** using an outer loop from `0` to `arrLen - 3`.
3. **Squeeze two pointers (`pointer1 = i + 1` and `pointer2 = arrLen - 1`)** inward from both ends of the remaining subarray.

---

## 1. `ThreeSum` (`sum == 0`) — *Find All Unique Triplets*

### Golden Rules & Thumb Rules
* **Sorting is MANDATORY:** Without sorting, you don't know whether to move `pointer1` or `pointer2`, and duplicate values won't sit next to each other.
* **Duplicate skipping is MANDATORY:** Because the array is sorted, identical numbers sit side-by-side. You must skip duplicates for `i`, `pointer1`, AND `pointer2` so `resList` only contains unique triplets.
* **Move BOTH pointers on a match:** After recording a valid triplet (`sum == 0`), increment `pointer1++` and decrement `pointer2--` together.

### Variable Roles & Purpose
| Variable | Purpose in This Problem |
| :--- | :--- |
| `i` | **Anchor index** fixing the 1st element of the triplet (`0` to `arrLen - 3`). |
| `pointer1` | **Left pointer** starting at `i + 1`; moves right (`++`) when `sum` is too small (`< 0`). |
| `pointer2` | **Right pointer** starting at `arrLen - 1`; moves left (`--`) when `sum` is too large (`> 0`). |
| `sum` | Current triplet sum (`nums[i] + nums[pointer1] + nums[pointer2]`) compared against `0`. |
| `resList` | Output list storing all unique valid triplets. |

### Walkthrough with Your Input (`nums = { -1, 0, 1, 2, -1, -4 }`)
1. **Sorted `nums`:** `{-4, -1, -1, 0, 1, 2}`
2. **Anchor `i = 0` (`-4`):** Even with largest values (`-4 + 1 + 2 = -1 < 0`), `pointer1` reaches `pointer2`. No match.
3. **Anchor `i = 1` (`-1`):**
   * `pointer1 = 2` (`-1`), `pointer2 = 5` (`2`) -> `sum = 0` -> Add `[-1, -1, 2]`, slide past duplicates, move both inward.
   * `pointer1 = 3` (`0`), `pointer2 = 4` (`1`) -> `sum = 0` -> Add `[-1, 0, 1]`, move both inward.
4. **Anchor `i = 2` (`-1`):** `nums == nums` -> skipped by `continue`.
5. **Output:** `[[-1, -1, 2], [-1, 0, 1]]`

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

### Golden Rules & Thumb Rules
* **Sorting is MANDATORY:** Guarantees `pointer1++` increases the sum toward `target` and `pointer2--` decreases it.
* **Initialize `minDiff = Integer.MAX_VALUE` (Infinity):** Never use `Integer.MIN_VALUE`, because distance (`Math.abs`) is always `>= 0`.
* **Never chain pointer movement onto `if (currDiff < minDiff)`:** Updating `closestSum` and moving `pointer1`/`pointer2` must be **two separate `if` blocks**, otherwise the pointers freeze and cause an infinite loop.
* **Duplicate skipping is OPTIONAL:** Only skipping `i` is useful for speed; returning a single `int` means duplicates won't hurt correctness.

### Variable Roles & Purpose
| Variable | Purpose in This Problem |
| :--- | :--- |
| `i` | **Anchor index** fixing the 1st element (`0` to `arrLen - 3`). |
| `pointer1` | **Left pointer** (`i + 1`); moves right (`++`) when `sum < target`. |
| `pointer2` | **Right pointer** (`arrLen - 1`); moves left (`--`) when `sum > target`. |
| `currDiff` | Absolute distance `Math.abs(sum - target)` for the current triplet. |
| `minDiff` | Smallest distance seen so far (initialized to `Integer.MAX_VALUE`). |
| `closestSum` | The actual triplet sum that produced `minDiff` (this is what we return). |

### Walkthrough with Your Input (`nums = { -1, 2, 1, -4 }`, `target = 1`)
1. **Sorted `nums`:** `{-4, -1, 1, 2}`
2. **`i = 0` (`-4`), `pointer1 = 1` (`-1`), `pointer2 = 3` (`2`):** `sum = -3`, `currDiff = 4 < MAX_VALUE` -> `minDiff = 4`, `closestSum = -3`. Since `-3 < 1`, `pointer1++`.
3. **`i = 0` (`-4`), `pointer1 = 2` (`1`), `pointer2 = 3` (`2`):** `sum = -1`, `currDiff = 2 < 4` -> `minDiff = 2`, `closestSum = -1`. Since `-1 < 1`, `pointer1++` (ends).
4. **`i = 1` (`-1`), `pointer1 = 2` (`1`), `pointer2 = 3` (`2`):** `sum = 2`, `currDiff = 1 < 2` -> `minDiff = 1`, `closestSum = 2`.
5. **Output:** `2`

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

                if (currDiff < minDiff) {
                    minDiff = currDiff;
                    closestSum = sum;
                }

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

### Golden Rules & Thumb Rules
* **Sorting is MANDATORY:** Allows O(1) bulk-counting when `total < sum`.
* **Bulk-Count Formula `count += (pointer2 - pointer1)`:** If `arr[i] + arr[pointer1] + arr[pointer2] < sum`, then because `arr[pointer2]` is the **largest** element in that window, **every element between `pointer1` and `pointer2`** will also form a valid triplet with `arr[i]` and `arr[pointer1]`.
* **Do NOT skip duplicates:** Distinct index triplets `(i, j, k)` with equal values still count separately.
* **Move ONLY `pointer1++` when `total < sum`:** After bulk-counting all pairs for the current `pointer1`, advance `pointer1++` to test the next left element. When `total >= sum`, move `pointer2--`.

### Variable Roles & Purpose
| Variable | Purpose in This Problem |
| :--- | :--- |
| `i` | **Anchor index** fixing the 1st element (`0` to `arrLen - 3`). |
| `pointer1` | **Left pointer** (`i + 1`); incremented (`++`) after bulk-counting all valid pairs for it. |
| `pointer2` | **Right pointer** (`arrLen - 1`); decremented (`--`) whenever `total >= sum`. |
| `total` | Current triplet sum (`arr[i] + arr[pointer1] + arr[pointer2]`) compared against `sum`. |
| `count` | Running total of valid index triplets found (`count += pointer2 - pointer1`). |

### Walkthrough with Your Input (`arr2 = { 5, 1, 3, 4, 7 }`, `sum2 = 12`)
1. **Sorted `arr2`:** `{1, 3, 4, 5, 7}`
2. **`i = 0` (`1`), `pointer1 = 1` (`3`), `pointer2 = 4` (`7`):**
   * `total = 1 + 3 + 7 = 11 < 12`.
   * Since `7` works, `5` and `4` also work with `(1, 3)` -> `(1, 3, 7)`, `(1, 3, 5)`, `(1, 3, 4)`.
   * `count += (4 - 1)` adds **3** triplets at once, then `pointer1++` (`2`).
3. **`i = 0` (`1`), `pointer1 = 2` (`4`), `pointer2 = 4` (`7`):** `total = 12` (not `< 12`) -> `pointer2--` (`3`).
4. **`i = 0` (`1`), `pointer1 = 2` (`4`), `pointer2 = 3` (`5`):** `total = 10 < 12` -> `count += (3 - 2)` adds **1** triplet `(1, 4, 5)`, `pointer1++` (ends).
5. **Output:** `4`

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
