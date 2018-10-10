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

    private Reflector reflect = new Reflector("B",
            new Permutation("(AE) (BN) (CK) (DQ) "
                    + "(FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", UPPER));
    private FixedRotor fixedrotor = new FixedRotor("BETA",
            new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", UPPER));
    private MovingRotor movingrotor1 = new MovingRotor("I",
            new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", UPPER), "Q");
    private MovingRotor movingrotor2 = new MovingRotor("II",
            new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", UPPER), "E");
    private MovingRotor movingrotor3 = new MovingRotor("III",
            new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", UPPER), "V");
    private MovingRotor movingrotor4 = new MovingRotor("IV",
            new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", UPPER), "J");

    ArrayList<Rotor> rotors = new ArrayList<>();
    private Machine machine;
    private String[] insert = {"B", "BETA", "III", "IV", "I"};

    /** Set the rotor to the one with given NAME and permutation as
     * *  specified by the NAME entry in ROTORS, with given NOTCHES. */
    private void setMachine(Alphabet alpha, int numrotors,
                            int pawls, Collection<Rotor> allrotors) {
        machine = new Machine(alpha, numrotors, pawls, allrotors);
    }

    /* ***** TESTS ***** */
    @Test
    public void testInsertRotors() {
        rotors.add(reflect);
        rotors.add(fixedrotor);
        rotors.add(movingrotor1);
        rotors.add(movingrotor2);
        rotors.add(movingrotor3);
        rotors.add(movingrotor4);
        setMachine(UPPER, 5, 3, rotors);
        machine.insertRotors(insert);
        assertEquals("Incorrect rotor at 0", rotors.get(0), machine._rotors[0]);
        assertEquals("Incorrect rotor at 4", rotors.get(2), machine._rotors[4]);
    }

    @Test
    public void testSetRotors() {
        rotors.add(reflect);
        rotors.add(fixedrotor);
        rotors.add(movingrotor1);
        rotors.add(movingrotor2);
        rotors.add(movingrotor3);
        rotors.add(movingrotor4);
        setMachine(UPPER, 5, 3, rotors);
        machine.insertRotors(insert);
        machine.setRotors("AXLE");
        assertEquals("Incorrect setting at Rotor 1", 0, machine._rotors[1].setting());
        assertEquals("Incorrect setting at Rotor 2", 23, machine._rotors[2].setting());
        assertEquals("Incorrect setting at Rotor 3", 11, machine._rotors[3].setting());
        assertEquals("Incorrect setting at Rotor 4", 4, machine._rotors[4].setting());
    }

    @Test
    public void testConvert() {
        rotors.add(reflect);
        rotors.add(fixedrotor);
        rotors.add(movingrotor1);
        rotors.add(movingrotor2);
        rotors.add(movingrotor3);
        rotors.add(movingrotor4);
        setMachine(UPPER, 5, 3, rotors);
        machine.insertRotors(insert);
        machine.setRotors("AXLE");
        machine.setPlugboard(new Permutation("(HQ) (EX) (IP) (TR) (BY)", UPPER));
        assertEquals("Wrong convert", "QVPQ", machine.convert("FROM"));
        setMachine(UPPER, 5, 3, rotors);
        machine.insertRotors(insert);
        machine.setRotors("AXLE");
        machine.setPlugboard(new Permutation("(HQ) (EX) (IP) (TR) (BY)", UPPER));
        assertEquals("Wrong convert", "FROM", machine.convert("QVPQ"));
    }

    @Test
    public void testDoubleStep() {
        Alphabet ac = new CharacterRange('A', 'D');
        Rotor one = new Reflector("R1", new Permutation("(AC) (BD)", ac));
        Rotor two = new MovingRotor("R2", new Permutation("(ABCD)", ac), "C");
        Rotor three = new MovingRotor("R3", new Permutation("(ABCD)", ac), "C");
        Rotor four = new MovingRotor("R4", new Permutation("(ABCD)", ac), "C");
        String setting = "AAA";
        Rotor[] machineRotors = {one, two, three, four};
        String[] rotors = {"R1", "R2", "R3", "R4"};
        Machine mach = new Machine(ac, 4, 3, new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);

        assertEquals("AAAA", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AAAB", getSetting(ac, machineRotors));
    }

    /** Helper method to get the String representation of the current Rotor settings */
    private String getSetting(Alphabet alph, Rotor[] machineRotors) {
        String currSetting = "";
        for (Rotor r : machineRotors) {
            currSetting += alph.toChar(r.setting());
        }
        return currSetting;
    }
}
