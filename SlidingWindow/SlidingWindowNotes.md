# Sliding Window Pattern: Complete Revision Guide (`O(n)` Basics)

The **Sliding Window** technique transforms nested-loop `O(n²)` subarray/substring problems into a single **`O(n)` pass** with **`O(1)` extra space**. 

Instead of recalculating the middle elements every time the window moves, maintain a running state (`sum`, `count`, `map`) and only update the two edges:
* **Right edge (`high`) enters:** Add `arr[high]`
* **Left edge (`low`) exits:** Subtract `arr[low]`

> **Golden Rule:** *"Expand `high` forward, shrink `low` when needed, and NEVER re-loop over the middle."*

---

## How to Recognize This Pattern in Interviews
Look for these 3 clues in the problem statement:
1. **Data Structure:** Array or String.
2. **Keyword:** Contiguous **subarray** or **substring**.
3. **Goal:** Find a **maximum**, **minimum**, **longest**, **shortest**, or **target sum/count**.

### Fixed vs. Variable Window: Which One Do I Use?
* **Fixed-Size Window:** The window size **`k` is given** in the problem (e.g., *"subarray of size `k`"*). Both `low` and `high` move forward together at the same pace.
* **Variable-Size Window:** The window size is **unknown**; instead, a **condition is given** (e.g., *"sum `>= target`"*, *"at most `k` distinct characters"*). `high` expands the window, and `low` shrinks it dynamically.

---

## 1. `MaxSumSubarrayOfSizeK` — *Fixed-Size Sliding Window*

### Problem Summary
Given an array of integers `arr` and a positive integer `k`, find the **maximum sum** of any contiguous subarray of size `k`.

### Golden Rules & Thumb Rules
* **Build the first window (`0` to `k - 1`) upfront:** Sum the first `k` elements once before starting the `while` loop so `sum` already holds a valid window.
* **Slide one step at a time:** Increment `low++` and `high++` together so the distance `high - low + 1` stays locked at `k`.
* **Update edges in exact order:** Subtract the element that just left (`arr[low - 1]`) and add the new element entering (`arr[high]`).
* **Guard against `ArrayIndexOutOfBoundsException`:** Check `if (high == arr.length) break;` right after `high++` and *before* reading `arr[high]`.
* **Initialize `max` to `Integer.MIN_VALUE`:** Never initialize `max = 0`, because all elements in `arr` could be negative!

### Variable Roles & Purpose

| Variable | Role & Purpose |
| :--- | :--- |
| `low` | **Left pointer** starting at `0`. Increments each slide; `arr[low - 1]` is the outgoing element removed from `sum`. |
| `high` | **Right pointer** starting at `k - 1`. Increments each slide; `arr[high]` is the incoming element added to `sum`. |
| `sum` | Running sum of the current `k` elements inside `[low ... high]`. |
| `max` | Tracks the highest `sum` recorded across all valid windows of size `k`. |

### Step-by-Step Walkthrough (`arr = { 100, 200, 300, 400 }`, `k = 2`)
1. **Build Initial Window (`0` to `1`):**
   * `low = 0`, `high = 1`
   * Loop `i = 0..1`: `sum = 100 + 200 = 300`
2. **Iteration 1 (`high = 1 < 4`):**
   * Record max: `max = Math.max(300, MIN_VALUE) = 300`
   * Slide window: `low = 1`, `high = 2` (`2 != 4`, continue)
   * Update sum: `sum = 300 - arr[0] (100) + arr[2] (300) = 500`
3. **Iteration 2 (`high = 2 < 4`):**
   * Record max: `max = Math.max(500, 300) = 500`
   * Slide window: `low = 2`, `high = 3` (`3 != 4`, continue)
   * Update sum: `sum = 500 - arr[1] (200) + arr[3] (400) = 700`
4. **Iteration 3 (`high = 3 < 4`):**
   * Record max: `max = Math.max(700, 500) = 700`
   * Slide window: `low = 3`, `high = 4`
   * Boundary hit: `high == arr.length` (`4 == 4`) -> `break`!
5. **Final Output:** `700`

### Java Code (`MaxSumSubarrayOfSizeK.java`)
```java
package SlidingWindow;

public class MaxSumSubarrayOfSizeK {

    public static void main(String[] args) {

        int[] arr = { 100, 200, 300, 400 };
        int k = 2;

        System.out.println("Output : " + maxSubarraySum(arr, k));

    }

    public static int maxSubarraySum(int[] arr, int k) {

        int low = 0;
        int high = k - 1;
        int sum = 0;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i <= high; i++) {
            sum = sum + arr[i];
        }

        while (high < arr.length) {
            max = Math.max(sum, max);
            low++;
            high++;

            if (high == arr.length) {
                break;
            }
            sum = sum - arr[low - 1];
            sum = sum + arr[high];

        }
        return max;

    }

}
```

---

## 2. `MinimumSizeSubarraySum` — *Variable-Size Sliding Window*

### Problem Summary
Given an array of positive integers `nums` and a positive integer `target`, return the **minimal length** of a contiguous subarray of which the sum is greater than or equal to `target`. If no such subarray exists, return `0`.

### Golden Rules & Thumb Rules
* **`high` explores, `low` shrinks:** Always drive the outer `for` loop with `high` (`0` to `nums.length - 1`). Use an inner `while` loop to advance `low` as far right as possible while the window stays valid (`sum >= target`).
* **Window length formula is `high - low + 1`:** Always add `+ 1` because both `low` and `high` indices are inclusive (e.g., from index `2` to `4` has `4 - 2 + 1 = 3` elements).
* **Update `minLen` INSIDE the `while` loop:** Because this is a *minimum* problem, every step we shrink `low` while `sum >= target` produces a valid, smaller window—so record `Math.min(currLen, minLen)` right before removing `nums[low]`.
* **Why is a `while` inside a `for` still `O(n)`?** Both `low` and `high` only move forward from `0` to `n - 1`. Each element is added at most once and subtracted at most once (`2n` total operations = `O(n)`).
* **Sentinel check for "No Answer":** Initialize `minLen = nums.length + 1` (or `Integer.MAX_VALUE`). After the loop, if `minLen > nums.length`, no valid window was ever found -> return `0`.

### Variable Roles & Purpose

| Variable | Role & Purpose |
| :--- | :--- |
| `low` | **Left pointer** starting at `0`. Advances inside `while (sum >= target)` to shrink the window and find smaller valid lengths. |
| `high` | **Right pointer** (`0` to `nums.length - 1`). Expands the window one element at a time in the outer `for` loop. |
| `sum` | Running sum of elements inside the current dynamic window `[low ... high]`. |
| `currLen` | Length of the current valid window (`high - low + 1`). |
| `minLen` | Tracks the smallest `currLen` seen so far (initialized to `nums.length + 1`). |

### Step-by-Step Walkthrough (`nums = { 2, 3, 1, 2, 4, 3 }`, `target = 7`)
1. `high = 0` (`2`) -> `sum = 2 < 7`
2. `high = 1` (`3`) -> `sum = 5 < 7`
3. `high = 2` (`1`) -> `sum = 6 < 7`
4. `high = 3` (`2`) -> `sum = 8 >= 7` (**Enters `while` loop**):
   * `low = 0, high = 3` (`[2, 3, 1, 2]`): `currLen = 3 - 0 + 1 = 4` -> `minLen = 4`
   * Shrink: `sum = 8 - nums[0] (2) = 6`, `low = 1` -> `6 < 7` (exit `while`)
5. `high = 4` (`4`) -> `sum = 6 + 4 = 10 >= 7` (**Enters `while` loop**):
   * `low = 1, high = 4` (`[3, 1, 2, 4]`): `currLen = 4 - 1 + 1 = 4` -> `minLen = 4`
   * Shrink: `sum = 10 - nums[1] (3) = 7`, `low = 2`
   * Still `7 >= 7`: `low = 2, high = 4` (`[1, 2, 4]`): `currLen = 4 - 2 + 1 = 3` -> `minLen = 3`
   * Shrink: `sum = 7 - nums[2] (1) = 6`, `low = 3` -> `6 < 7` (exit `while`)
6. `high = 5` (`3`) -> `sum = 6 + 3 = 9 >= 7` (**Enters `while` loop**):
   * `low = 3, high = 5` (`[2, 4, 3]`): `currLen = 5 - 3 + 1 = 3` -> `minLen = 3`
   * Shrink: `sum = 9 - nums[3] (2) = 7`, `low = 4`
   * Still `7 >= 7`: `low = 4, high = 5` (`[4, 3]`): `currLen = 5 - 4 + 1 = 2` -> `minLen = 2`
   * Shrink: `sum = 7 - nums[4] (4) = 3`, `low = 5` -> `3 < 7` (exit `while`)
7. **Final Check:** `minLen` (`2`) is not `> 6` -> **Output:** `2` (`[4, 3]`)

### Java Code (`MinimumSizeSubarraySum.java`)
```java
package SlidingWindow;

public class MinimumSizeSubarraySum {


    public static void main(String[] args) {

        int[] nums = {2,3,1,2,4,3};
        int target = 7;

        System.out.println("Output : " + minSubArrayLen(target, nums));
        
    }


    public static int minSubArrayLen(int target, int[] nums) {
        int low = 0;
        int minLen = nums.length + 1;
        int sum = 0;


        for(int high = low; high < nums.length; high++){
            sum = sum + nums[high];

            while(sum >= target){
                int currLen = high - low + 1;
                minLen = Math.min(currLen, minLen);

                sum = sum - nums[low];
                low++;

            }
        }
         if (minLen > nums.length) {
            return 0;
        }
            return minLen;
    }

}
```

---

## Side-by-Side Comparison

| Feature | `MaxSumSubarrayOfSizeK` | `MinimumSizeSubarraySum` |
| :--- | :--- | :--- |
| **Window Type** | **Fixed Size** (`k`) | **Variable Size** (`sum >= target`) |
| **Outer Loop Driver** | `while (high < arr.length)` | `for (int high = low; high < nums.length; high++)` |
| **How `low` Moves** | Increments once per step alongside `high` (`low++`, `high++`) | Shrinks inside `while (sum >= target)` until window becomes `< target` |
| **Target Metric** | Maximum **Sum** (`sum`) | Minimum **Length** (`high - low + 1`) |
| **Initial Best Value** | `Integer.MIN_VALUE` | `nums.length + 1` (or `Integer.MAX_VALUE`) |
| **Time / Space** | **O(n)** time / **O(1)** space | **O(n)** time / **O(1)** space |

---

## Mistakes & Traps NOT to Repeat (Personal Checklist)

Review these **6 common traps** before solving any Sliding Window problem:

1. **Re-looping over the middle (`O(n²)` Trap)**
   * *Mistake:* Writing `for (int i = low; i < low + k; i++)` inside your main loop to recalculate `sum` from scratch.
   * *Fix:* Never re-scan inside the window. Only subtract `arr[low]` and add `arr[high]`.
2. **Forgetting one half of the slide**
   * *Mistake:* Subtracting `arr[low - 1]` when sliding right, but forgetting to add `arr[high]`.
