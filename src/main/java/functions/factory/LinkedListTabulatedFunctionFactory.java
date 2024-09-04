package functions.factory;
import functions.*;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory{

    public TabulatedFunction create(double[] xValues, double[] yValues) {
        var funk = new LinkedListTabulatedFunction(xValues, yValues);
        return funk;
    }

    public TabulatedFunction create(MathFunction source, double xFrom, double xTo, int count) {
        var funk = new LinkedListTabulatedFunction(source, xFrom, xTo, count);
        return funk;
    }
}
