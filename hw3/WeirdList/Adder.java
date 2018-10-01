public class Adder implements IntUnaryFunction {
        public Adder(int n) {
            new_n = n;
        }

        @Override
        public int apply(int x) {
            return x + new_n;
        }

        private int new_n;
}
