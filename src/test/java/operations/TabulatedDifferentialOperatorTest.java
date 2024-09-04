package operations;

import concurrent.SynchronizedTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import org.junit.jupiter.api.Test;
import org.junit.Assert;
import static org.junit.jupiter.api.Assertions.*;

class TabulatedDifferentialOperatorTest {

    @Test
    void getFactory() {
        TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator op = new TabulatedDifferentialOperator(factoryLinked);
        assertEquals(factoryLinked, op.getFactory());
    }

    @Test
    void setFactory() {
        TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator op = new TabulatedDifferentialOperator();
        op.setFactory(factoryLinked);
        assertEquals(factoryLinked, op.getFactory());
    }

    @Test
    void constructor() {
        // если без параметра то фактори с массивом
        TabulatedDifferentialOperator op = new TabulatedDifferentialOperator();
        ArrayTabulatedFunctionFactory fac = new ArrayTabulatedFunctionFactory();
        assertInstanceOf(fac.getClass(), op._factory);
    }

    @Test
    void derive() {
        var facArr = new ArrayTabulatedFunctionFactory();
        var facLin = new LinkedListTabulatedFunctionFactory();
        var op = new TabulatedDifferentialOperator();

        // произв от констант = 0
        {
            double[] xValues = {2, 3, 4};
            double[] yValues = {1, 1, 1};
            op.setFactory(facLin);
            var fDev = op.derive(facLin.create(xValues, yValues));
            assertEquals(0, fDev.apply(2)); // для ключ точки
            assertEquals(0, fDev.apply(2.5)); // для промеж точ
        }

        // для прямой y=x  произв = конст = 1
        {
            double[] xValues = {1, 2, 3};
            double[] yValues = {1, 2, 3};
            op.setFactory(facArr);
            var fDev = op.derive(facLin.create(xValues, yValues));
            assertEquals(1, fDev.apply(2)); // для ключ точки
            assertEquals(1, fDev.apply(2.5)); // для промеж точ
        }

        // для прямой y=2*x  произв = конст = 2
        {
            double[] xValues = {1, 2, 3};
            double[] yValues = {2, 4, 6};
            op.setFactory(facArr);
            var fDev = op.derive(facLin.create(xValues, yValues));
            assertEquals(2, fDev.apply(2)); // для ключ точки
            assertEquals(2, fDev.apply(2.5)); // для промеж точ
        }

        // произв от y=x**2 -- y=2x + погрешность(1)!!
        {
            double[] xValues = {0, 1, 2, 3, 4, 5};
            double[] yValues = {0, 1, 4, 9, 16, 25};
            op.setFactory(facLin);
            var fDev = op.derive(facLin.create(xValues, yValues));
            assertEquals(1+0, fDev.apply(0)); // для ключ точки
            assertEquals(1+2, fDev.apply(1)); // для ключ точки
            assertEquals(1+4, fDev.apply(2)); // для ключ точки
            assertEquals(1+6, fDev.apply(3)); // для ключ точки
            assertEquals(1+5, fDev.apply(2.5)); // для промеж точ
        }
    }

    @Test
    public void testDeriveSynchronously()
    {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator(factory);

        double[] xValues = {0.0, 1.0, 2.0, 3.0, 4.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0, 16.0};

        TabulatedFunction function = factory.create(xValues, yValues);
        TabulatedFunction derivative = differentialOperator.deriveSynchronously(function);

        double[] expectedDerivativeValues = {1.0, 3.0, 5.0, 7.0, 7.0};

        for (int i = 0; i < xValues.length; i++)
        {
            Assert.assertEquals(expectedDerivativeValues[i], derivative.getY(i), 0.001);
        }
    }
}