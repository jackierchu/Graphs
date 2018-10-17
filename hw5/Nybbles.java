/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author
 */
public class Nybbles {

    /** Maximum positive value of a Nybble. */
    public static final int MAX_VALUE = 7;

    /** Return an array of size N. */
    public Nybbles(int N) {
        // DON'T CHANGE THIS.
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k >= _n || k < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            int sub = k % 8;
            int first = k / 8;

            int subElement = _data[first] >>> sub * 4;
            subElement = subElement & 0b1111;

            if(subElement >= 8) {
                return subElement - 16;
            }
            else {
                return subElement;
            }
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k >= _n || k < 0) {
            throw new IndexOutOfBoundsException();
        } else if (val > MAX_VALUE || val < -MAX_VALUE - 1) {
            throw new IllegalArgumentException();
        } else {
            int sub = k % 8;
            int first = k / 8;
            _data[first] = reset(sub, _data[first]);
            int newValue = val;
            if(newValue < 0)
                newValue = newValue + 16;
            _data[first] = set(sub, _data[first], newValue);
        }
    }
    public int set(int position, int want, int value){
        int newValue = value & 0b1111;
        newValue <<= position * 4;
        return want = newValue | want;
    }

    public int reset(int position, int want){
        int unstore = ~(0b1111 << position * 4);
        return want = unstore & want;
    }

    // DON'T CHANGE OR ADD TO THESE.
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}
