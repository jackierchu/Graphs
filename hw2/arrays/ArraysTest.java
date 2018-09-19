package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class ArraysTest {
    /** FIXME
     */

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
    @Test
    public void testCatenate() {
        int x[] = new int[]{1, 2, 3, 4, 5};
        int y[] = new int[]{6, 7, 8, 9, 10};
        int z[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(z ,Arrays.catenate(x, y));

        int a[] = new int[]{4, 5, 6};
        int b[] = new int[]{8, 9, 10};
        int c[] = new int[]{4, 5, 6, 8, 9, 10};
        assertArrayEquals(c, Arrays.catenate(a, b));

    }
    @Test
    public void testRemove() {
        int a[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int b[] = new int[]{1, 6, 7, 8};
        assertArrayEquals(b, Arrays.remove(a, 1, 4));

        int x[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int y[] = new int[]{1, 2, 3, 7, 8, 9, 10};
        assertArrayEquals(y, Arrays.remove(x, 3, 3));
    }
    @Test
    public void testNaturalRuns() {
        int[] A = {1, 3, 7, 5, 4, 6, 9, 10},
                B = {};
        int[][] C = new int[3][],
                D = new int[0][];
        C[0] = new int[]{1, 3, 7};
        C[1] = new int[]{5};
        C[2] = new int[]{4, 6, 9, 10};
        assertEquals(Utils.equals(Arrays.naturalRuns(A), C) &&
                Utils.equals(Arrays.naturalRuns(B), D), true);
    }
}