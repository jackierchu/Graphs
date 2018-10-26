// REPLACE THIS STUB WITH THE CORRECT SOLUTION.
// The current contents of this file are merely to allow things to compile
// out of the box.
/** A set of String values.
 *  @author Jacqueline Chu
 */
import java.util.LinkedList;
import java.util.List;

class ECHashStringSet implements StringSet {

    public ECHashStringSet() {
        _cache = new LinkedList[(int)(1/LOAD_FACTOR_MIN)];
        _size = 0;
    }

    @Override
    public void put(String string) {
        if(string == null){
            return;
        }

        if(load() > LOAD_FACTOR_MAX) {
            change_size();
        }

        int index = storingHash(string.hashCode());

        if(_cache[index] == null) {
            _cache[index] = new LinkedList<String>();
        }
        _cache[index].add(string);
        _size += 1;
    }

    @Override
    public boolean contains(String string) {
        if(string != null) {
            int value = storingHash(string.hashCode());
            if(_cache[value] != null) {
                return _cache[value].contains(string);
            }
        }
        return false;
    }

    @Override
    public List<String> asList() {
        if(_cache.length == 0){
            return null;
        }

        List<String> result = new LinkedList<String>();
        for(int i = 0; i < _cache.length; i++) {
            if (_cache[i] != null) {
                for (String item : _cache[i]) {
                    result.add(item);
                }
            }
        }

        return result;
    }

    public int size() {
        return _size;
    }

    private void change_size() {
        LinkedList<String>[] old = _cache;
        _cache = new LinkedList[2 * old.length];
        _size = 0;
        for(int i = 0; i < old.length; i++) {
            if (old[i] != null) {
                for (String item : old[i]) {
                    this.put(item);
                }
            }
        }
    }

    private double load(){
        return ((double)_size)/((double)_cache.length);
    }

    private int storingHash(int hash) {
        int last = hash & 1;
        int  temphash = (hash >>> 1) | last;
        return temphash % _cache.length;
    }

    /** Linked list to cache the strings*/
    private LinkedList<String>[] _cache;
    /** Private instance variable for size */
    private int _size;
    /** Maximum load value */
    public static double LOAD_FACTOR_MAX = 5;
    /** Minimum load value */
    public static double LOAD_FACTOR_MIN = 0.2;

}
