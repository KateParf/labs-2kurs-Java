package operations;

import concurrent.SynchronizedTabulatedFunction;

import functions.*;
import functions.factory.*;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction>
{
    TabulatedFunctionFactory _factory;

    public TabulatedFunctionFactory getFactory() {
        return _factory;
    }

    public void setFactory(TabulatedFunctionFactory _factory) {
        this._factory = _factory;
    }

    //----------

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this._factory = factory;
    }

    public TabulatedDifferentialOperator() {
        this._factory = new ArrayTabulatedFunctionFactory();
    }

    //----------

    public TabulatedFunction derive(TabulatedFunction function) {
        Point[] points = TabulatedFunctionOperationService.asPoints(function);
        double[] xValues = new double[points.length];
        double[] yValues = new double[points.length];

        for (int i = 0; i < points.length - 1; i++) {
            xValues[i] = points[i].x;
            yValues[i] = (points[i+1].y - points[i].y) / (points[i+1].x - points[i].x);
        }
        xValues[points.length - 1] = points[points.length - 1].x;
        yValues[points.length - 1] = yValues[points.length - 2];

        var der = this._factory.create(xValues, yValues);
        return der;
    }

    public TabulatedFunction deriveSynchronously(TabulatedFunction function)
    {
        SynchronizedTabulatedFunction synchronizedFunction;

        if (function instanceof SynchronizedTabulatedFunction)
        {
            synchronizedFunction = (SynchronizedTabulatedFunction) function;
        }
        else
        {
            synchronizedFunction = new SynchronizedTabulatedFunction(function);
        }
        return synchronizedFunction.doSynchronously(this::derive);
    }
}