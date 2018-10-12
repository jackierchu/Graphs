package enigma;

/** Alphabet consisting of encodable characters that
 * provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Jacqueline Chu
 */

public class ExtendAlphabetEC extends Alphabet {

    /** Additional string variable. */
    private String _characters;

    /** A new alphabet that contains chars. Characters cannot be duplicated.
     *  @param chars returns the characters */
    public ExtendAlphabetEC(String chars) {
        _characters = chars;
    }

    /** Returns the size of the alphabet. */
    int size() {
        return _characters.length();
    }

    /** Returns true if C is within alphabet given. */
    boolean contains(char c) {
        for (int i = 0; i < size(); i = i + 1) {
            if (_characters.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    /** Returns the character number within the alphabet given. */
    char toChar(int indexec) {
        if (indexec < 0 || indexec >= size()) {
            throw new EnigmaException("character index is out of the range");
        }
        return _characters.charAt(indexec);
    }

    /** Returns the index of C that must be within the alphabet given. */
    int toInt(char c) {
        for (int x = 0; x < size(); x = x + 1) {
            if (_characters.charAt(x) == c) {
                return x;
            }
        }
        throw new EnigmaException("character is not in the alphabet given");
    }

}
