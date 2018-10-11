package enigma;

/** Alphabet consisting of encodable characters that provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Jacqueline Chu
 */

public class ExtendAlphabetEC extends Alphabet {

    /** Additional string variable. */
    String _chars;

    /** A new alphabet that contains chars.  Character number #j has index
     *  J that is numbered from 0. Characters cannot be duplicated. */
    public ExtendAlphabetEC(String chars) {
        _chars = chars;
    }

    /** Returns size of the alphabet. */
    int size() {
        return _chars.length();
    }

    /** Returns true if character C is in this alphabet. */
    boolean contains(char c) {
        for (int i = 0; i < size(); i++) {
            if (_chars.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    /** Returns the character number INDEX within the alphabet */
    char toChar(int index) {
        if (index < 0 || index >= size()) {
            throw new EnigmaException("character index is out of range");
        }
        return _chars.charAt(index);
    }

    /** Returns the index of character C that must be within the alphabet. */
    int toInt(char c) {
        for (int i = 0; i < size(); i++) {
            if (_chars.charAt(i) == c) {
                return i;
            }
        }
        throw new EnigmaException("character is not in the alphabet");
    }

}
