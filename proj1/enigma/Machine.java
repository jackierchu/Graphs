package enigma;

import java.util.ArrayList;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Jacqueline Chu
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = allRotors.toArray();
        _rotors = new Rotor[numRotors];

    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        for (int x = 0; x < rotors.length; x = x + 1) {
            for (int y = 0; y < _allRotors.length; y = y + 1) {
                if ((rotors[x]).equals((((Rotor) _allRotors[y]).name()))) {
                    _rotors[x] = (Rotor) _allRotors[y];
                }
            }
        }
        if (_rotors.length != rotors.length) {
            throw new EnigmaException("Rotor incorrect length and name");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 upper-case letters. The first letter refers to the
     *  leftmost rotor setting (not counting the reflector). */
    void setRotors(String setting) {
        if (setting.length() != _numRotors - 1) {
            throw new EnigmaException("Initial position string "
                    + "is the wrong length, change");
        }
        for (int x = 1; x < _numRotors; x++) {
            if (!_alphabet.contains(setting.charAt(x - 1))) {
                throw new EnigmaException("Initial position "
                        + "string is not in the alphabet, change");
            }
            _rotors[x].set(setting.charAt(x - 1));
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing

     *  the machine. */
    int convert(int c) {
        int insertion = c % _alphabet.size();
        iterateForward();

        if (_plugboard != null) {
            insertion = _plugboard.permute(insertion);
        }
        for (int i = _rotors.length - 1; i >= 0; i = i - 1) {
            Rotor increment = _rotors[i];
            insertion = increment.convertForward(insertion);
        }
        for (int i = 1; i < _rotors.length; i += 1) {
            Rotor decrement = _rotors[i];
            insertion = decrement.convertBackward(insertion);
        }
        if (_plugboard != null) {
            insertion = _plugboard.permute(insertion);
        }
        return insertion;
    }

    /** Implemented helper function called IterateForward to help
     * with convert. */

    void iterateForward() {
        ArrayList<Rotor> newRotors = new ArrayList<>();
        for (int x = numRotors() - numPawls(); x < numRotors(); x = x + 1) {
            Rotor currentOne = _rotors[x];
            Rotor left = _rotors[x - 1];
            if (x == (numRotors() - 1)) {
                newRotors.add(currentOne);
            } else if (newRotors.contains(left) || _rotors[x + 1].atNotch()) {
                if (!newRotors.contains(currentOne)) {
                    newRotors.add(currentOne);
                }
                if (_rotors[x].atNotch()) {
                    if (!newRotors.contains(left)) {
                        newRotors.add(left);
                    }
                }
            }
        }
        for (int x = 0; x < newRotors.size(); x++) {
            Rotor newer = newRotors.get(x);
            newer.advance();
        }
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String results = "";
        for (int x = 0; x < msg.length(); x++) {
            char conv = _alphabet.
                    toChar(convert(_alphabet.toInt(msg.charAt(x))));
            results += conv;
        }
        return results;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;
    /** Added instance variable. */
    private int _numRotors;
    /** Added instance variable. */
    private int _pawls;
    /** Added instance variable. */
    private Rotor[] _rotors;
    /** Added instance variable. */
    private Permutation _plugboard;
    /** Added instance variable. */
    private Object[] _allRotors;

    /** Getter method for Rotors.
     * @return returns the rotors*/
    public Rotor[] getRotors() {
        return _rotors;
    }
}
