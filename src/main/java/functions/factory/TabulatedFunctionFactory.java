package functions.factory;
import functions.*;
public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);
    TabulatedFunction create(MathFunction source, double xFrom, double xTo, int count);

    // реализация метода по умолчанию - в интерфейсе
    // поэтому не делаем реализации в соотв классах
    default TabulatedFunction createStrict(double[] xValues, double[] yValues) {
        return new StrictTabulatedFunction(this.create(xValues, yValues));
    };

    default TabulatedFunction createUnmodifiable(double[] xValues, double[] yValues)
    {
        TabulatedFunction function = create(xValues, yValues);
        return new UnmodifiableTabulatedFunction(function);
    }

    default TabulatedFunction createStrictUnmodifiable (double[] xValues, double[] yValues)
    {
        return new UnmodifiableTabulatedFunction(new StrictTabulatedFunction(this.create(xValues, yValues)));
    }
}