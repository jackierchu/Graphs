package enigma;

public class ExtendAlphabetEC extends Alphabet {

    String _chars;

    /** A new alphabet containing CHARS.  Character number #k has index
     *  K (numbering from 0). No character may be duplicated. */
    public ExtendAlphabetEC (String chars) {
        _chars = chars;
    }

    /** Returns the size of the alphabet. */
    int size() {
        return _chars.length();
    }

    /** Returns true if C is in this alphabet. */
    boolean contains(char c) {
        for (int i = 0; i < size(); i++) {
            if (_chars.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        if (index < 0 || index >= size()) {
            throw new EnigmaException("character index out of range");
        }
        return _chars.charAt(index);
    }

    /** Returns the index of character C, which must be in the alphabet. */
    int toInt(char c) {
        for (int i = 0; i < size(); i++) {
            if (_chars.charAt(i) == c) {
                return i;
            }
        }
        throw new EnigmaException("character not in alphabet");
    }

}
