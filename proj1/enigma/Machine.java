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
        for (int i = 0; i < rotors.length; i++) {
            for (int j = 0; j < _allRotors.length; j++) {
                if ((rotors[i]).equals((((Rotor) _allRotors[j]).name()))) {
                    _rotors[i] = (Rotor) _allRotors[j];
                }
            }
        }
        if (_rotors.length != rotors.length) {
            throw new EnigmaException("Rotor has the incorrect name");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 upper-case letters. The first letter refers to the
     *  leftmost rotor setting (not counting the reflector). */
    void setRotors(String setting) {
        if (setting.length() != _numRotors - 1) {
            throw new EnigmaException("Initial position string "
                    + "is wrong length");
        }
        for (int i = 1; i < _numRotors; i++) {
            if (!_alphabet.contains(setting.charAt(i - 1))) {
                throw new EnigmaException("Initial position "
                        + "string is not in the alphabet");
            }
            _rotors[i].set(setting.charAt(i - 1));
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
        int input = c % _alphabet.size();
        iterateForward();

        if (_plugboard != null) {
            input = _plugboard.permute(input);
        }
        for (int pos = _rotors.length - 1; pos >= 0; pos--) {
            Rotor forward = _rotors[pos];
            input = forward.convertForward(input);
        }
        for (int pos = 1; pos < _rotors.length; pos++) {
            Rotor backward = _rotors[pos];
            input = backward.convertBackward(input);
        }

        if (_plugboard != null) {
            input = _plugboard.permute(input);
        }

        return input;
    }

    /** Implemented helper function called IterateForward to help
     * with convert. */

    void iterateForward() {
        ArrayList<Rotor> moving = new ArrayList<>();
        for (int i = numRotors() - numPawls(); i < numRotors(); i++) {
            Rotor currentRotor = _rotors[i];
            Rotor leftRotor = _rotors[i - 1];

            if (i == (numRotors() - 1)) {
                moving.add(currentRotor);
            } else if (_rotors[i + 1].atNotch() || moving.contains(leftRotor)) {
                if (!moving.contains(currentRotor)) {
                    moving.add(currentRotor);
                }
                if (_rotors[i].atNotch()) {
                    if (!moving.contains(leftRotor)) {
                        moving.add(leftRotor);
                    }
                }
            }
        }
        for (Rotor r: moving) {
            r.advance();
        }
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String result = "";
        for (int i = 0; i < msg.length(); i++) {
            char converted = _alphabet.
                    toChar(convert(_alphabet.toInt(msg.charAt(i))));
            result += converted;
        }
        return result;
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

    public Rotor[] getRotors() {
        return _rotors;
    }
}
