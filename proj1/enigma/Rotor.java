package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author Jacqueline Chu
 */
class Rotor {
    private int _setting;

    /** A rotor named NAME whose permutation is given by PERM. FIXED */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _setting = 0;
    }

    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. FIXED */
    int setting() {
        return _setting;
    }

    /** Set setting() to POSN. FIXED  */
    void set(int posn) {
        _setting = posn;
    }

    /** Set setting() to character CPOSN. FIXED */
    void set(char cposn) {
        _setting = alphabet().toInt(cposn);
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. FIXED */
    int convertForward(int p) {
        int cIn = _permutation.wrap(p + _setting);
        int cOut = _permutation.permute(cIn);
        int result = _permutation.wrap(cOut - _setting);
        return result;
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. FIXED */
    int convertBackward(int e) {
        int cIn = _permutation.wrap(e + _setting);
        int cOut = _permutation.invert(cIn);
        int result = _permutation.wrap(cOut - _setting);
        return result;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED

}
