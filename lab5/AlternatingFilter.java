import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets through every other VALUE element of
 *  its input sequence, starting with the first.
 *  @author Jacqueline Chu
 */
class AlternatingFilter<Value> extends Filter<Value> {

    /** A filter of values from INPUT that lets through every other
     *  value. */
    AlternatingFilter(Iterator<Value> input) {
        super(input);
        allow = true;
    }

    @Override
    protected boolean keep() {
        if (allow == true) {
            allow = false;
            return true;
        } else {
            allow = true;
            return false;
        }
    }
    boolean allow;
}
