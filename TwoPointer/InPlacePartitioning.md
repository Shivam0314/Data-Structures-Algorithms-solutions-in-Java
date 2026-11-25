# Two-Pointer Pattern: In-Place Partitioning & Deduplication (`O(n)` Time, `O(1)` Space)

All four problems in this group modify an array **in-place** in a single **O(n)** pass with **O(1)** extra space by maintaining strict boundary pointers (`p1`/`low` and `high`) while an explorer pointer (`p2`/`mid`) scans the array.

---

## 1. `Segregate0sand1s` — *2-Color In-Place Partitioning*

### Golden Rules & Thumb Rules
* **No sorting allowed:** Sorting takes **O(n log n)**; two pointers partition `0`s and `1`s in **O(n)** time and **O(1)** space.
* **Invariant Rule:** Every index strictly before `p1` (`0` to `p1 - 1`) is guaranteed to be `0`.
* **Only advance `p1` on a `0`:** `p2` moves every iteration, while `p1` only advances when a `0` is swapped into place.

### Variable Roles & Purpose
| Variable | Purpose in This Problem |
| :--- | :--- |
| `p1` | **Boundary / Write pointer** (`0`); marks the next slot where a `0` should be placed. |
| `p2` | **Explorer pointer** (`0`); scans the array looking for `0`s (`arr[p2] == 0`) to swap with `arr[p1]`. |

### Walkthrough with Your Input (`arr = { 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1 }`)
1. `p2 = 0` (`0`) -> swaps with `p1 = 0`, `p1 = 1`.
2. `p2 = 1, 2, 3` (`1`s) -> skipped (`p1` stays `1`).
3. `p2 = 4, 5, 6, 7` (`0`s) -> each `0` swaps into `p1` (`1, 2, 3, 4`), advancing `p1` to `5`.
4. **Final Output:** `[0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1]`

### Java Code (`Segregate0sand1s.java`)
```java
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
```

---

## 2. `Segregate0s1sand2s` (Dutch National Flag) — *3-Color In-Place Partitioning*

### Golden Rules & Thumb Rules
* **No sorting allowed:** Extends `Segregate0sand1s` from 2 groups to 3 groups (`0`s, `1`s, `2`s) in **O(n)** time and **O(1)** space.
* **Three Regions Invariant:**
  * `0` to `low - 1` -> All `0`s
  * `low` to `mid - 1` -> All `1`s
  * `mid` to `high` -> Unknown / Unsorted window
  * `high + 1` to `arrLen - 1` -> All `2`s
* **Why `mid` does NOT increment when `arr[mid] == 2`:** The element swapped back from `high` has not been inspected yet (it could be `0`, `1`, or `2`). Keeping `mid` in place forces the next loop iteration to inspect it.
* **Loop Condition MUST be `mid <= high` (`<=`):** You must inspect the final element when `mid` and `high` meet.
* **Always use a `temp` variable for swapping:** Arithmetic/XOR swaps break when `num1 == num2` (which happens whenever `low == mid` and `arr[mid] == 0`).

### Variable Roles & Purpose
| Variable | Purpose in This Problem |
| :--- | :--- |
| `low` | **Boundary pointer for `0`s** (starts at `0`); everything before `low` is guaranteed to be `0`. |
| `mid` | **Explorer pointer** (starts at `0`); scans the unknown window from left to right. |
| `high` | **Boundary pointer for `2`s** (starts at `arrLen - 1`); everything after `high` is guaranteed to be `2`. |

### Walkthrough with Your Input (`arr = { 0, 1, 2, 0, 1, 2 }`)
1. **`low = 0, mid = 0, high = 5` (`arr[mid] == 0`):** Swaps `arr[0]` with `arr[0]`, `low = 1`, `mid = 1`.
2. **`low = 1, mid = 1, high = 5` (`arr[mid] == 1`):** `1` belongs in the middle -> `mid = 2`.
3. **`low = 1, mid = 2, high = 5` (`arr[mid] == 2`):** Swaps `arr[2]` with `arr[5]`, `high = 4` (`mid` stays `2` to inspect the swapped element).
4. **`low = 1, mid = 2, high = 4` (`arr[mid] == 2`):** Swaps `arr[2]` (`2`) with `arr[4]` (`1`) -> `{ 0, 1, 1, 0, 2, 2 }`, `high = 3` (`mid` stays `2`).
5. **`low = 1, mid = 2, high = 3` (`arr[mid] == 1`):** `mid = 3`.
6. **`low = 1, mid = 3, high = 3` (`arr[mid] == 0`):** Swaps `arr[3]` (`0`) with `arr[1]` (`1`) -> `{ 0, 0, 1, 1, 2, 2 }`, `low = 2`, `mid = 4` -> Loop ends (`mid > high`).
7. **Final Output:** `[0, 0, 1, 1, 2, 2]`

### Java Code (`Segregate0s1sand2s.java`)
```java
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
        int temp = arr[num1];
        arr[num1] = arr[num2];
        arr[num2] = temp;
    }
}
```

---

## 3. `RemoveDuplicatesFromSortedArray` — *Keep At Most 1 Copy*

### Golden Rules & Thumb Rules
* **Array is ALREADY sorted:** Duplicate elements are always adjacent (`nums[p2] == nums[p2 - 1]`).
* **Compare against `nums[p2 - 1]`:** Whenever `nums[p2] != nums[p2 - 1]`, we found a brand-new unique number.
* **Return `p1 + 1`:** Because `p1` is a **0-based index** pointing to the *last* unique element written, the total count of unique elements is `p1 + 1`.

### Variable Roles & Purpose
| Variable | Purpose in This Problem |
| :--- | :--- |
| `p1` | **Last unique element index** (starts at `0`, since index `0` is always unique). Incremented **before** writing `nums[p1] = nums[p2]`. |
| `p2` | **Scanner pointer** (starts at `1`); skips duplicates and finds the next distinct value. |

### Walkthrough with Your Input (`arr = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4 }`)
1. `p2 = 1, 2` (`1`s) -> match `nums[p2 - 1]`, skipped.
2. `p2 = 3` (`2` != `1`) -> `p1 = 1`, writes `nums[1] = 2`.
3. `p2 = 6` (`3` != `2`) -> `p1 = 2`, writes `nums[2] = 3`.
4. `p2 = 10` (`4` != `3`) -> `p1 = 3`, writes `nums[3] = 4`.
5. **Final Output:** `4` (first 4 elements: `[1, 2, 3, 4]`)

### Java Code (`RemoveDuplicatesFromSortedArray.java`)
```java
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
```

---

## 4. `RemoveDuplicateFromSortedArray2` — *Keep At Most 2 Copies*

### Golden Rules & Thumb Rules
* **First 2 elements are ALWAYS valid:** Even if `nums[0] == nums[1]`, keeping both is allowed (`<= 2` copies), so both `p1` and `p2` start at index **`2`**.
* **General `k`-Duplicates Formula (`nums[p2] == nums[p1 - k]`):** Comparing `nums[p2]` against **`nums[p1 - 2]`** checks the *already-cleaned* prefix. If they match, writing `nums[p2]` at `p1` would create 3 identical numbers in a row, so we skip it. (For at most `k` duplicates, compare against `nums[p1 - k]`).
* **Return `p1` directly (not `p1 + 1`):** Unlike Problem 3, `p1` here points to the **next open write slot**, so `p1` already equals the exact count of valid elements.

### Variable Roles & Purpose
| Variable | Purpose in This Problem |
| :--- | :--- |
| `p1` | **Next open write index** (starts at `2`); increments **after** writing `nums[p1] = nums[p2]`. Equals final valid length. |
| `p2` | **Scanner pointer** (starts at `2`); compares `nums[p2]` against `nums[p1 - 2]` to allow at most 2 copies. |

### Walkthrough with Your Input (`nums = { 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5 }`)
1. `p2 = 2` (`nums[2] == 1` == `nums[0]`) -> 3rd `1`, skipped.
2. `p2 = 3, 4` (`2`s) -> written to `nums[2]` and `nums[3]`; `p2 = 5, 6` (`2`s) -> skipped.
3. Keeps two `3`s, two `4`s, and one `5` -> `p1` stops at `9`.
4. **Final Output:** `9` (first 9 elements: `[1, 1, 2, 2, 3, 3, 4, 4, 5]`)

### Java Code (`RemoveDuplicateFromSortedArray2.java`)
```java
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
```

---

## Quick Comparison Table

| Problem | Pointer Setup | Condition to Keep / Swap | Operation | Return Value |
| :--- | :--- | :--- | :--- | :--- |
| **`Segregate0sand1s`** | `p1 = 0`, `p2 = 0` | `arr[p2] == 0` | Swap `arr[p1]` & `arr[p2]`, `p1++` | `arr` (`int[]`) |
| **`Segregate0s1sand2s`** | `low = 0`, `mid = 0`, `high = n - 1` | `0` -> swap `low`; `1` -> skip; `2` -> swap `high` | `0`: `low++`, `mid++`; `1`: `mid++`; `2`: `high--` (no `mid++`) | `arr` (`int[]`) |
| **`RemoveDuplicatesFromSortedArray`** | `p1 = 0`, `p2 = 1` | `nums[p2] != nums[p2 - 1]` | `p1++`, `nums[p1] = nums[p2]` | `p1 + 1` (new length) |
| **`RemoveDuplicateFromSortedArray2`** | `p1 = 2`, `p2 = 2` | `nums[p2] != nums[p1 - 2]` | `nums[p1] = nums[p2]`, `p1++` | `p1` (new length) |
