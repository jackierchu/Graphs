package enigma;

import org.junit.Test;
import ucb.junit.textui;

import static junit.framework.TestCase.assertEquals;

/** The suite of all JUnit tests for the enigma package.
 *  @author
 */
public class UnitTest {

    /**
     * Run the JUnit tests in this package. Add xxxTest.class entries to
     * the arguments of runClasses to run other JUnit tests.
     */

    public static void main(String[] ignored) {
        textui.runClasses(PermutationTest.class, MovingRotorTest.class, MachineTest.class);
    }
}