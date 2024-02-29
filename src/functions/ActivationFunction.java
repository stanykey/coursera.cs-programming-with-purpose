public class ActivationFunction {
    // Returns the Heaviside function of x.
    public static double heaviside(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }

        if (x < 0) {
            return 0;
        }

        return (x > 0) ? 1 : 0.5;
    }

    // Returns the sigmoid function of x.
    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    // Returns the hyperbolic tangent of x.
    public static double tanh(double x) {
        if (Math.abs(x) >= 20.0) {
            return (x > 0) ? 1.0 : -1.0;
        }

        final double exp1 = Math.exp(x);
        final double exp2 = Math.exp(-x);
        return (exp1 - exp2) / (exp1 + exp2);
    }

    // Returns the softsign function of x.
    public static double softsign(double x) {
        if (Double.isInfinite(x)) {
            return (x > 0) ? 1.0 : -1.0;
        }
        return x / (1 + Math.abs(x));
    }

    // Returns the square nonlinearity function of x.
    public static double sqnl(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }

        if (x <= -2) {
            return -1;
        }

        if (x < 0) {
            return x + ((x * x) / 4);
        }

        if (x < 2) {
            return x - ((x * x) / 4);
        }

        return 1.0;
    }

    // Takes a double command-line argument x and prints each activation
    // function, evaluated, in the format (and order) given below.
    public static void main(String[] args) {
        final double x = Double.parseDouble(args[0]);

        System.out.println("heaviside(" + x + ") = " + heaviside(x));
        System.out.println("  sigmoid(" + x + ") = " + sigmoid(x));
        System.out.println("     tanh(" + x + ") = " + tanh(x));
        System.out.println(" softsign(" + x + ") = " + softsign(x));
        System.out.println("     sqnl(" + x + ") = " + sqnl(x));
    }
}
