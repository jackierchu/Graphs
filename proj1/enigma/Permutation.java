package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Jacqueline Chu
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */

    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        String temp = cycles.trim();
        temp = temp.replace("(", "");
        temp = temp.replace(")", "");
        _cycles = temp.split(" ");
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        String[] newCycles = new String[_cycles.length + 1];
        for (int x = 0; x < _cycles.length; x = x + 1) {
            newCycles[x] = _cycles[x];
        }
        newCycles[_cycles.length + 1] = cycle;
        _cycles = newCycles;
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int a = p % size();
        if (a < 0) {
            a = a + size();
        }
        return a;
    }

    /** Returns the size of the alphabet I permute. FIXED */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char a = _alphabet.toChar(wrap(p));
        for (int x = 0; x < _cycles.length; x = x + 1) {
            for (int y = 0; y < _cycles[x].length(); y = y + 1) {
                if (_cycles[x].charAt(y) == a) {
                    char newCharacter = _cycles[x].charAt((y + 1)
                            % _cycles[x].length());
                    return _alphabet.toInt(newCharacter);
                }
            }
        }
        return p;
    }

    /** Helper method for mod.
     * @param x is the p.
     * @param size is the size.
     * @return returns the r. */
    int mod(int x, int size) {
        int i = x % size;
        if (i < 0) {
            i += size;
        }
        return i;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char a = _alphabet.toChar(wrap(c));
        char newCharacters;
        for (int x = 0; x < _cycles.length; x = x + 1) {
            for (int j = 0; j < _cycles[x].length(); j = j + 1) {
                if (_cycles[x].charAt(j) == a) {
                    newCharacters = _cycles[x].charAt(mod(j - 1,
                            _cycles[x].length()));
                    return _alphabet.toInt(newCharacters);
                }
            }
        }
        return c;
    }


    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int indexing = _alphabet.toInt(p);
        return _alphabet.toChar(permute(indexing));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    int invert(char c) {
        int indexing = _alphabet.toInt(c);
        return _alphabet.toChar(invert(indexing));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        int counter = 0;
        for (int x = 0; x < _cycles.length; x = x + 1) {
            counter = counter + _cycles[x].length();
        }
        return (counter == _alphabet.size());
    }

    /** Alphabet of permutation. */
    private Alphabet _alphabet;

    /** In an array of strings, each string is like a cycle
     * for which each letter would be converted. */
    private String[] _cycles;
}
