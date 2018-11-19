import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.Random;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Distribution Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. FIXED */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = 1; i < k; i = i +1) {
                int newvariable = array[i];
                int j = i - 1;

                while ((j >= 0) && (array[j] > newvariable)) {
                    array[j + 1] = array[j];
                    j = j - 1;
                }
                array[j + 1] = newvariable;
            }
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort). FIXED
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = 0; i < k; i++) {
                int min = i;
                for (int j = i + 1; j < k; j++) {
                    min = array[min] > array[j] ? j : min;
                }
                swap(array, min, i);
            }
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively. FIXED
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int[] tempArray = new int[k];
            mergeSort(array, tempArray, 0, k);

        }

        private void mergeSort(int[] array, int[] tempArray, int left, int right) {
            int mid = (left + right) / 2;
            if (right > left + 1) {
                //left recursive
                mergeSort(array, tempArray, left, mid);
                //right recursive
                mergeSort(array, tempArray, mid, right);
                merge(array, left, mid, right, tempArray);

                System.arraycopy(tempArray, left, array, left, right - left);
            }
        }

        private void merge(int[] array, int left, int mid, int right,
                           int[] tempArray) {
            int l = left, r = mid;
            while (l < mid || r < right) {
                if ((r == right)||(l < mid && array[l] < array[r])) {
                    //Use the left
                    tempArray[l + r - mid] = array[l];
                    l++;
                }
                else {
                    //use the right
                    tempArray[l + r - mid] = array[r];
                    r++;
                }
            }
        }

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Distribution Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array. FIXED
     */

    public static class DistributionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            //Do nothing for one or empty
            if (k <= 1)
                return;

            int largest = getLargestFromArray(array, k);
            HashMap<Integer, Integer> buckets = generateCountsMap(largest);

            for (int i = 0; i < k; i++){
                buckets.put(array[i], buckets.get(array[i])+1 );
            }

            //Filling values from hashmap to array
            int index = 0;
            for (int elem = 0; elem < largest + 1; elem++) {
                int end = buckets.get(elem) + index;
                if (end != index)
                    Arrays.fill(array, index, end, elem);
                index = end;
            }
        }

        private HashMap generateCountsMap(int largest) {
            HashMap<Integer, Integer> counts = new HashMap<>();
            for(int i =0; i<=largest; i++) {
                counts.put(i,0);
            }
            return counts;

        }

        private int getLargestFromArray(int[] array, int k) {
            int max = array[0];

            for (int i = 1; i < array.length; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
            return max;
        }

        @Override
        public String toString() {
            return "Distribution Sort";
        }
    }

    /** Your Heapsort implementation. FIXED
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {

            int n = k;
            for (int i = n / 2 - 1; i >= 0; i--)
                heapify(array, n, i);
            for (int i=n-1; i>=0; i--)
            {
                swap(array, 0, i);
                heapify(array, i, 0);
            }
        }
        private void heapify(int array[], int sizeOfHeap, int rootIndex) {
            int max = rootIndex;
            int leftNodeIndex = 2 * rootIndex + 1;
            int rightNodeIndex = 2 * rootIndex + 2;

            max = compareHelper(max,leftNodeIndex,rightNodeIndex, array,sizeOfHeap);

            if(max != rootIndex) {
                swap(array, rootIndex, max);
                heapify(array, sizeOfHeap, max);
            }

        }

        private int compareHelper(int max, int leftNodeIndex, int rightNodeIndex, int array[], int sizeOfHeap) {

            if (leftNodeIndex < sizeOfHeap && array[leftNodeIndex] > array[max])
                max = leftNodeIndex;

            if (rightNodeIndex < sizeOfHeap&&array[rightNodeIndex] > array[max])
                max = rightNodeIndex;
            return max;
        }

        private void swap(int array[], int a, int b) {
            int swap = array[a];
            array[a] = array[b];
            array[b] = swap;
        }


        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation. FIXED
     */
    public static class QuickSort implements SortingAlgorithm {

        @Override
        public void sort(int[] array, int k) {
            k = Math.min(k,array.length);
            quickSort(array,0,k-1);
        }

        private static void swap(int array[], int i, int j) {
            int temporary = array[i];
            array[i] = array[j];
            array[j] = temporary;
        }

        public static void quickSort(int[] array, int low, int high) {
            int IndexPivot = ThreadLocalRandom.current().nextInt(low, high + 1);
            int pivoting = array[IndexPivot];
            int m = low, n = high;
            while (m <= n) {
                while (array[m] < pivoting) {
                    m = m + 1;
                }
                while (array[n] > pivoting) {
                    n--;
                }
                if (m <= n) {
                    swap(array, m, n);
                    m++;
                    n--;
                }
            }
            if (low < n)
                quickSort(array, low, n);
            if (high > m)
                quickSort(array, m, high);
        }

        /** End of implementation. */

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation. FIXED
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int digits = getMaxDigit(array);
            for (int i = 1; i <= digits; i++) {
                sortDigit(array, k, i);
            }
        }

        private int getMaxDigit(int[] array) {
            int max = 1;
            for (int i = 0; i < array.length; i++) {
                int numLength = String.valueOf(array[i]).length();
                if (numLength > max) {
                    max = numLength;
                }
            }
            return max;
        }

        private static void sortDigit(int[] array, int numOfElements, int digitIndex) {
            HashMap<Integer, LinkedList<Integer>> buckets = new HashMap<>();
            for (int i = 0; i < 10; i++)
                buckets.put(i, new LinkedList<>());

            for (int i = 0; i < numOfElements; i++) {
                int digit = array[i];
                for (int j = 1; j < digitIndex; j++) {
                    digit /= 10;
                }
                int bucket = digit % 10;
                buckets.get(bucket).add(array[i]);
            }

            int index = 0;
            for (int b = 0; b < 10; b++) {
                LinkedList<Integer> bucket = buckets.get(b);
                while (!bucket.isEmpty()) {
                    array[index++] = bucket.remove();
                }
            }
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation. FIXED
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int[] tempArray = new int[k];
            sort(array, 0, k - 1, 0, tempArray);
        }

        private int[] getCountsOfNumber(int pos, int low, int high, int[] array, int shift ) {
            int[] count = new int[pos + 1];
            int mask = pos - 1;   // 0xFF;
            for (int i = low; i <= high; i++) {
                int digit = (array[i] >> shift) & mask;
                count[digit + 1]++;
            }
            return count;
        }

        private void transform(int pos, int[] array, int[] count, int[] tempArray, int low, int high, int shift) {
            int mask = pos - 1;
            for (int k = 0; k < pos; k++)
                count[k + 1] += count[k];

            for (int i = low; i <= high; i++) {
                int c = (array[i] >> shift) & mask;
                tempArray[count[c]++] = array[i];
            }
        }

        public void sort(int[] array, int low, int high, int digit, int[] tempArray) {
            int bitsForByte = 8;
            int bitsForInt= 32;
            int pos = 1 << bitsForByte;

            int shift = bitsForInt - bitsForByte * digit - bitsForByte;

            int[] count = getCountsOfNumber(pos, low, high,  array, shift);

            transform(pos, array, count, tempArray, low, high, shift);

            System.arraycopy(tempArray, 0, array, low, high - low+1);

            if (digit == 4) return;

            if (count[0] > 0){
                sort(array, low, low + count[0] - 1, digit + 1, tempArray);
            }
            for (int m = 0; m< pos; m++)
                if (count[m + 1] > count[m]){
                    sort(array, low + count[m], low + count[m + 1] - 1, digit + 1, tempArray);
                }
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
