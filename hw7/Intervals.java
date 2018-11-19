import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/** HW #7, Sorting ranges.
 *  @author
  */
class SortComparision implements Comparator<int[]> {
    // Used for sorting in ascending order of
    // roll name
    public int compare(int[] a, int[] b) {
        return a[0] - b[0];
    }
}
public class Intervals {
    /** Assuming that INTERVALS contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns the
     *  total length covered by the union of the intervals. FIXED */
    public static int coveredLength(List<int[]> intervals) {
        intervals.sort( new SortComparision());

        int result = 0;

        int start = Integer.MIN_VALUE;
        int end = Integer.MIN_VALUE;

        for (int[] interval : intervals) {
            if (interval[0] > end) {
                result += (end - start);
                start = interval[0];
                end = interval[1];
            } else if (interval[0] <= end && interval[1] > end) {
                end = interval[1];
            }
        }
        result += (end - start);

        return result;

    }

    /** Test intervals. */
    static final int[][] INTERVALS = {
        {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
    };
    /** Covered length of INTERVALS. */
    static final int CORRECT = 23;

    /** Performs a basic functionality test on the coveredLength method. */
    @Test
    public void basicTest() {
        assertEquals(CORRECT, coveredLength(Arrays.asList(INTERVALS)));
    }

    /** Runs provided JUnit test. ARGS is ignored. */
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Intervals.class));
    }

}
