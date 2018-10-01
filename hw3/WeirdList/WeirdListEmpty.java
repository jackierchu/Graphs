public class WeirdListEmpty extends WeirdList {
    public int length() {
        return 0;
    }

    public WeirdList map(IntUnaryFunction func) {
        return new WeirdListEmpty();
    }

    public WeirdListEmpty() {
        super(0, null);
    }
}