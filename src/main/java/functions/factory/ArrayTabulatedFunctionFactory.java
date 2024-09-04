package functions.factory;
import functions.*;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {

    public TabulatedFunction create(double[] xValues, double[] yValues) {
        var funk = new ArrayTabulatedFunction(xValues, yValues);
        return funk;
    }

    public TabulatedFunction create(MathFunction source, double xFrom, double xTo, int count) {
        var funk = new ArrayTabulatedFunction(source, xFrom, xTo, count);
        return funk;
    }
}
