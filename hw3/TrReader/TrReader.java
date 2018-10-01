import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Jacqueline Chu
 */
public class TrReader extends Reader {
    /** The source that I translate. */
    private final Reader _subReader;
    /** The characters from which and to which I translate.
     *  _from[i] => _to[i] for each i.  _from and _to must have equal
     *  lengths. */
    private final String _from, _to;
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    public TrReader(Reader str, String from, String to) {
        _subReader = str;
        _from = from;
        _to = to;
    }
    /** Close the Reader supplied to my constructor. */
    public void close() throws IOException {
        _subReader.close();
    }

    /** Return the translation of IN according to my _FROM and _TO. */
    private char convertChar(char in) {
        int k = _from.indexOf(in);
        if (k == -1) {
            return in;
        } else {
            return _to.charAt(k);
        }
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int actualRead = _subReader.read(cbuf, off, len);
        for (int i = off; i < off + actualRead; i += 1) {
            cbuf[i] = convertChar(cbuf[i]);
        }
        return actualRead;
    }
    // FILL IN
    // NOTE: Until you fill in the right methods, the compiler will
    //       reject this file, saying that you must declare TrReader
    //     abstract.  Don't do that; define the right methods instead!
}


