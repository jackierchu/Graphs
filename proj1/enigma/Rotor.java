package enigma;

import static enigma.EnigmaException.*;

/**
 * Superclass that represents a rotor in the enigma machine.
 *
 * @author Jacqueline Chu
 */
class Rotor {

    /**
     * A rotor named NAME whose permutation is given by PERM.
     */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _setting = 0;
    }

    /**
     * Return my name.
     */
    String name() {
        return _name;
    }

    /**
     * Return my alphabet.
     */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /**
     * Return my permutation.
     */
    Permutation permutation() {
        return _permutation;
    }

    /**
     * Return the size of my alphabet.
     */
    int size() {
        return _permutation.size();
    }

    /**
     * Return true iff I have a ratchet and can move.
     */
    boolean rotates() {
        return false;
    }

    /**
     * Return true iff I reflect.
     */
    boolean reflecting() {
        return false;
    }

    /**
     * Return my current setting.
     */
    int setting() {
        return _setting;
    }

    /**
     * Set setting() to POSN.
     */
    void set(int posn) {
        if (alphabet().contains(alphabet().toChar(posn))) {
            _setting = posn;
        }
    }

    /** Create mod function.
     * @param a integer
     * @param size size
     * @return x function
     */

    private int mod(int a, int size) {
        int x = a % size;
        if (x < 0) {
            x += size;
        }
        return x;
    }

    /**
     * Set setting() to character CPOSN.
     */
    void set(char cposn) {
        _setting = alphabet().toInt(cposn);
    }

    /**
     * Return the conversion of P (an integer in the range 0..size()-1)
     * according to my permutation.
     */
    int convertForward(int p) {
        int permuteNum = p + _setting % size();
        int resultingOne = _permutation.permute(permuteNum);
        return mod(resultingOne - _setting, size());
    }

    /**
     * Return the conversion of E (an integer in the range 0..size()-1)
     * according to the inverse of my permutation.
     */
    int convertBackward(int e) {
        int invertNum = e + _setting % size();
        int resultingTwo = _permutation.invert(invertNum);
        return mod(resultingTwo - _setting, size());
    }

    /**
     * Returns true iff I am positioned to allow the rotor to my left
     * to advance.
     */
    boolean atNotch() {
        return false;
    }

    /**
     * Advance me one position, if possible. By default, does nothing.
     */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /**
     * String name.
     */
    private final String _name;

    /**
     * The permutation implemented by this rotor in 0 position.
     */
    private Permutation _permutation;

    /**
     * Integer variable.
     */
    private int _setting;

}
