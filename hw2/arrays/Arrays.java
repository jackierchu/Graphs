package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Jacqueline Chu
 */
class Arrays {
    /* C. */

    /**
     * Returns a new array consisting of the elements of A followed by the
     * the elements of B.
     */
    static int[] catenate(int[] A, int[] B) {
        int[] q = new int[A.length + B.length];
        System.arraycopy(A, 0, q, 0, A.length);
        System.arraycopy(B, 0, q, A.length, B.length);
        return q;
    }

    /**
     * Returns the array formed by removing LEN items from A,
     * beginning with item #START.
     */
    static int[] remove(int[] A, int start, int len) {
        if (len == 0) {
            return A;
        }
        int[] q = new int[A.length - len];
        System.arraycopy(A, 0, q, 0, start);
        System.arraycopy(A, start + len, q, start, A.length - start - len);
        return q;
    }

    /**
     * Returns the array of arrays formed by breaking up A into
     * maximal ascending lists, without reordering.
     * For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     * returns the three-element array
     * {{1, 3, 7}, {5}, {4, 6, 9, 10}}.
     */
    static int[][] naturalRuns(int[] A) {
        return naturalRuns(A, 0, 0);
    }

    private static int[][] naturalRuns(int[] A, int next, int len) {
        if (A.length <= next) {
            return new int[len][];
        }
        int x = runLength(A, next);
        int[][] result = naturalRuns(A, next + x, len + 1);
        result[len] = Utils.subarray(A, next, x);
        return result;
    }

    private static int runLength(int[] A, int x) {
        int i;
        i = x + 1;
        while (i < A.length && A[i] > A[i - 1]) {
            i += 1;
        }
        return i - x;
    }
}