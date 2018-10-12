package enigma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;
import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Machine class.
 *  @author Jacqueline Chu
 */

public class MachineTest {
    /** Testing time limit.*/
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Reflector reflector = new Reflector("B",
            new Permutation("(AZ) (BY) (CK) (DQ) "
                    + "(UF) (GY) (WH) (IJ) (OL) (PM) (RX) (SZ) (TV)", UPPER));
    private FixedRotor fixedrotors = new FixedRotor("BETA",
            new Permutation("(BLIVFCOJWUNMQTZSKPA) (XH)", UPPER));
    private MovingRotor movingrotor1 = new MovingRotor("I",
            new Permutation("(ALPHXTEU) (BKN) "
                    + "(CMOD) (FG) (IV) (JZ) (W)", UPPER), "Q");
    private MovingRotor movingrotor2 = new MovingRotor("II",
            new Permutation("(AIYVMW) (COLKUPH) "
                    + "(EXZ) (SQJ) (GT) (NR) (B) (F)", UPPER), "E");
    private MovingRotor movingrotor3 = new MovingRotor("III",
            new Permutation("(DHPBZJT) (CLVOYFAIRWUKXSG) "
                    + "(N)", UPPER), "V");
    private MovingRotor movingrotor4 = new MovingRotor("IV",
            new Permutation("(EPLIHYAOXMRCFBSWTGJNQ) (VD) "
                    + "(UK)", UPPER), "J");

    ArrayList<Rotor> rotorsArray = new ArrayList<>();
    private Machine newMachine;
    private String[] insertion = {"B", "BETA", "III", "IV", "I"};

    /** Set the rotor to the one with given NAME and permutation as
     * *  specified by the NAME entry in ROTORS, with given NOTCHES. */
    private void setMachine(Alphabet alpha, int numrotors,
                            int pawls, Collection<Rotor> allrotors) {
        newMachine = new Machine(alpha, numrotors, pawls, allrotors);
    }

    /* ***** TESTS ***** */
    @Test
    public void testSetRotors() {
        rotorsArray.add(reflector);
        rotorsArray.add(fixedrotors);
        rotorsArray.add(movingrotor1);
        rotorsArray.add(movingrotor2);
        rotorsArray.add(movingrotor3);
        rotorsArray.add(movingrotor4);
        setMachine(UPPER, 5, 3, rotorsArray);
        newMachine.insertRotors(insertion);
        newMachine.setRotors("AXLE");
        assertEquals("Incorrect setting at the Rotor 4",
                4, newMachine.getRotors()[4].setting());
        assertEquals("Incorrect setting at the Rotor 3",
                11, newMachine.getRotors()[3].setting());
        assertEquals("Incorrect setting at the Rotor 1",
                0, newMachine.getRotors()[1].setting());
        assertEquals("Incorrect setting at the Rotor 2",
                23, newMachine.getRotors()[2].setting());
    }

    @Test
    public void testInsertRotors() {
        rotorsArray.add(reflector);
        rotorsArray.add(fixedrotors);
        rotorsArray.add(movingrotor1);
        rotorsArray.add(movingrotor2);
        rotorsArray.add(movingrotor3);
        rotorsArray.add(movingrotor4);
        setMachine(UPPER, 5, 3, rotorsArray);
        newMachine.insertRotors(insertion);
        assertEquals("Incorrect rotor at 4", rotorsArray.get(2),
                newMachine.getRotors()[4]);
        assertEquals("Incorrect rotor at 0", rotorsArray.get(0),
                newMachine.getRotors()[0]);
}

    @Test
    public void testConvert() {
        rotorsArray.add(reflector);
        rotorsArray.add(fixedrotors);
        rotorsArray.add(movingrotor1);
        rotorsArray.add(movingrotor2);
        rotorsArray.add(movingrotor3);
        rotorsArray.add(movingrotor4);
        setMachine(UPPER, 5, 3, rotorsArray);
        newMachine.insertRotors(insertion);
        newMachine.setRotors("AXLE");
        newMachine.setPlugboard(new Permutation("(HQ) "
                + "(XE) (IP) (RT) (YB)", UPPER));
        assertEquals("Wrong conversion", "QVPQ",
                newMachine.convert("FROM"));
    }

    @Test
    public void testDoubleStep() {
        Alphabet ac = new CharacterRange('A', 'D');
        Rotor one = new Reflector("R1",
                new Permutation("(AC) (BD)", ac));
        Rotor two = new MovingRotor("R2",
                new Permutation("(ABCD)", ac), "C");
        Rotor three = new MovingRotor("R3",
                new Permutation("(ABCD)", ac), "C");
        Rotor four = new MovingRotor("R4",
                new Permutation("(ABCD)", ac), "C");
        String setting = "AAA";
        Rotor[] machineRotors = {one, two, three, four};
        String[] rotors2 = {"R1", "R2", "R3", "R4"};
        Machine mach = new Machine(ac, 4, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors2);
        mach.setRotors(setting);

        assertEquals("AAAA", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AAAB", getSetting(ac, machineRotors));

        mach.convert('a');
        assertEquals("AAAC", getSetting(ac, machineRotors));

        mach.convert('a');
        assertEquals("AABD", getSetting(ac, machineRotors));

        mach.convert('a');
        assertEquals("AABA", getSetting(ac, machineRotors));

        mach.convert('a');
        assertEquals("AABB", getSetting(ac, machineRotors));

        mach.convert('a');
        assertEquals("AABC", getSetting(ac, machineRotors));

        mach.convert('a');
        assertEquals("AACD", getSetting(ac, machineRotors));

    }

    /** Helper method to get the String representation of the current
     * Rotor settings */
    private String getSetting(Alphabet alph, Rotor[] machineRotors) {
        String currSetting = "";
        for (Rotor r : machineRotors) {
            currSetting += alph.toChar(r.setting());
        }
        return currSetting;
    }
}

