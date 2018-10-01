public class Summer implements IntUnaryFunction {

    public Summer() {
        new_s = 0;
    }

    public int getSummer() {
        return new_s;
    }

    @Override
    public int apply(int i) {
        new_s = new_s + i;
        return new_s;
    }

    private int new_s;
}