import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {
    private static boolean isBounded(double x,double y, double tolerance) {
        return Math.abs(x-y) < tolerance;
    }

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.

        assertEquals(0, 0); */
        assertEquals(0, CompoundInterest.numYears(2018));
        assertEquals(1, CompoundInterest.numYears(2019));
        assertEquals(2, CompoundInterest.numYears(2020));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertTrue(isBounded(CompoundInterest.futureValue(10, 12, 2018), 10, tolerance));
        assertTrue(isBounded(CompoundInterest.futureValue(10, 0, 2018), 10, tolerance));
        assertTrue(isBounded(CompoundInterest.futureValue(10, -10, 2020),8.1, tolerance));
        assertTrue(isBounded(CompoundInterest.futureValue(10, 12, 2020), 12.544, tolerance));
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertTrue(isBounded(CompoundInterest.futureValueReal(10, 12, 2018,0), 10, tolerance));
        assertTrue(isBounded(CompoundInterest.futureValueReal(10, 0, 2018,0), 10, tolerance));
        assertTrue(isBounded(CompoundInterest.futureValueReal(10, -10, 2020,3),7.62129, tolerance));
        assertTrue(isBounded(CompoundInterest.futureValueReal(10, -10, 2020,-11.1111),10.0, tolerance));
        assertTrue(isBounded(CompoundInterest.futureValueReal(10, 12, 2020,3), 11.8026496, tolerance));
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertTrue(isBounded(CompoundInterest.totalSavings(5000, 2018, 10),5000, tolerance));
        assertTrue(isBounded(CompoundInterest.totalSavings(5000, 2019, -10),9500, tolerance));
        assertTrue(isBounded(CompoundInterest.totalSavings(5000, 2020, 10),16550, tolerance));
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertTrue(isBounded(CompoundInterest.totalSavingsReal(5000, 2018, 10,10),5000, tolerance));
        assertTrue(isBounded(CompoundInterest.totalSavingsReal(5000, 2019, -10,10),8550, tolerance));
        assertTrue(isBounded(CompoundInterest.totalSavingsReal(5000, 2020, 10,10),13405.5, tolerance));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
