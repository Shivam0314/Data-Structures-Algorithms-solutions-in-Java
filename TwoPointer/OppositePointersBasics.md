# Two-Pointer Pattern: Opposite-Direction Pointers (`O(n)` Basics)

Both problems start two pointers at the **opposite ends** of a **sorted** array (`left = 0` and `right = arrLen - 1`) and converge inward in a single **O(n)** pass.

---

## 1. `TwoSum2InputArrayIsSorted` — *Converge on a Target Sum*

### Golden Rules & Thumb Rules
* **Input is ALREADY sorted:** Do not call `Arrays.sort()`—it would ruin the original indices!
* **Loop condition is `pointer2 > pointer1` (strictly `>`):** You cannot use the same element twice (`pointer1 == pointer2` is invalid).
* **1-Based Indexing:** Always add `+ 1` to both indices when returning (`new int { pointer1 + 1, pointer2 + 1 }`).
* **O(1) Space Advantage:** Unlike unsorted Two Sum (which needs an O(n) `HashMap`), opposite pointers solve this in **O(1)** extra space.

### Variable Roles & Purpose
| Variable | Purpose in This Problem |
| :--- | :--- |
| `pointer1` | **Left pointer** starting at `0` (smallest element); moves right (`+= 1`) when `sum < target`. |
| `pointer2` | **Right pointer** starting at `arrLen - 1` (largest element); moves left (`-= 1`) when `sum > target`. |
| `sum` | Current pair sum (`numbers[pointer2] + numbers[pointer1]`) compared against `target`. |

### Walkthrough with Your Input (`arr = { 2, 7, 11, 15 }`, `target = 9`)
1. `pointer1 = 0` (`2`), `pointer2 = 3` (`15`) -> `sum = 17 > 9` -> `pointer2 -= 1` (`2`).
2. `pointer1 = 0` (`2`), `pointer2 = 2` (`11`) -> `sum = 13 > 9` -> `pointer2 -= 1` (`1`).
3. `pointer1 = 0` (`2`), `pointer2 = 1` (`7`) -> `sum = 9 == 9` -> Return `{ 0 + 1, 1 + 1 }` -> ``.

### Java Code (`TwoSum2InputArrayIsSorted.java`)
```java
import java.util.Arrays;

class TwoSum2InputArrayIsSorted {

    public static void main(String args) {
        int arr = { 2, 7, 11, 15 };
        int target = 9;

        System.out.println("Output : " + Arrays.toString(twoSum(arr, target)));
    }

    public static int twoSum(int numbers, int target) {
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
                return new int { pointer1 + 1, pointer2 + 1 };
            }
        }

        return new int { -1, -1 };
    }
}
```

---

## 2. `SquaresOfaSortedArray` — *Fill Largest-to-Smallest from Both Ends*

### Golden Rules & Thumb Rules
* **Largest squares ALWAYS sit at the extremes (`p1` or `p2`):** Because `nums` is sorted and can contain negative numbers, squaring a large negative on the left (`(-4)² = 16`) can beat a positive on the right (`3² = 9`), while `0` sits in the middle.
* **Fill `resArr` BACKWARDS (`resIdx = arrLen - 1` down to `0`):** Since we easily find the *largest* square at each step, placing it at the end of `resArr` builds a sorted result in **O(n)** without needing `Arrays.sort()`.
* **Loop condition MUST be `p1 <= p2` (`<=`):** Unlike `TwoSum2`, you must process every single element—including the final middle element when `p1 == p2`.

### Variable Roles & Purpose
| Variable | Purpose in This Problem |
| :--- | :--- |
| `p1` | **Left pointer** (`0`); tracks the largest negative candidate on the left. |
| `p2` | **Right pointer** (`arrLen - 1`); tracks the largest positive candidate on the right. |
| `resArr` | Output array of size `arrLen` storing squared numbers in ascending order. |
| `resIdx` | **Write pointer** starting at `arrLen - 1` (end of `resArr`); decrements (`--`) after placing the larger square. |

### Walkthrough with Your Input (`nums = { -4, -1, 0, 3, 10 }`)
1. `p1 = 0` (`16`), `p2 = 4` (`100`) -> `100` is larger -> `resArr = 100`, `p2--`, `resIdx--`.
2. `p1 = 0` (`16`), `p2 = 3` (`9`) -> `16` is larger -> `resArr = 16`, `p1++`, `resIdx--`.
3. `p1 = 1` (`1`), `p2 = 3` (`9`) -> `9` is larger -> `resArr = 9`, `p2--`, `resIdx--`.
4. `p1 = 1` (`1`), `p2 = 2` (`0`) -> `1` is larger -> `resArr = 1`, `p1++`, `resIdx--`.
5. `p1 = 2` (`0`), `p2 = 2` (`0`) -> `p1 <= p2` processes `0` -> `resArr = 0`.
6. **Output:** ``

### Java Code (`SquaresOfaSortedArray.java`)
```java
import java.util.Arrays;

public class SquaresOfaSortedArray {

    public static void main(String args) {
        int nums = { -4, -1, 0, 3, 10 };

        System.out.println(Arrays.toString(sortedSquares(nums)));
    }

    public static int sortedSquares(int nums) {
        int arrLen = nums.length;
        int resArr = new int[arrLen];
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
```

---

## Quick Comparison Table

| Problem | Loop Condition | How Pointers Decide Who Moves | Output & Space | Time / Space |
| :--- | :--- | :--- | :--- | :--- |
| **`TwoSum2InputArrayIsSorted`** | `pointer2 > pointer1` (strictly distinct pair) | Compare `sum` vs `target` (`sum > target` -> `pointer2--`, `sum < target` -> `pointer1++`) | Returns 1-based indices `[pointer1 + 1, pointer2 + 1]` | **O(n)** time / **O(1)** aux |
| **`SquaresOfaSortedArray`** | `p1 <= p2` (must process final middle element) | Compare `nums[p1]²` vs `nums[p2]²` (write the **larger** square to `resArr[resIdx--]`) | Builds new `resArr` from back to front | **O(n)** time / **O(n)** output |