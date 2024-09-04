package operations;

import exceptions.InconsistentFunctionsException;
import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import static org.junit.jupiter.api.Assertions.*;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import org.junit.jupiter.api.Test;
import functions.Point;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TabulatedFunctionOperationServiceTest
{
    double[] xValues = {0, 1, 2};
    double[] yValues = {0, 1, 4};

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
    void asPoints()
    {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        Point[] points = TabulatedFunctionOperationService.asPoints(function);
        int i = 0;
        for(Point point: points)
        {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i]);
            ++i;
        }
    }
    @Test
    void multiplyTest() {
        var op = new TabulatedFunctionOperationService();

        double[] xValues1 = {2, 3, 4};
        double[] yValues1 = {2, 2, 2};
        double[] yValues2 = {11, 22, 33};

        // с линкед лист
        var func1 = new LinkedListTabulatedFunction(xValues1, yValues1);
        var func2 = new LinkedListTabulatedFunction(xValues1, yValues2);
        var factLink = new LinkedListTabulatedFunctionFactory();
        op.setFactory(factLink);

        var res = assertDoesNotThrow( () -> op.multiply(func1, func2) );
        for(int i = 0; i < res.getCount(); i++){
            assertEquals(yValues1[i] * yValues2[i], res.getY(i));
        }

        // с аррай
        var funcA1 = new LinkedListTabulatedFunction(xValues1, yValues1); //!! !!!
        var funcA2 = new LinkedListTabulatedFunction(xValues1, yValues2); //!!
        var factArr = new ArrayTabulatedFunctionFactory();
        op.setFactory(factArr);

        var resA = assertDoesNotThrow( () -> op.multiply(funcA1, funcA2) );
        for(int i = 0; i < resA.getCount(); i++){
            assertEquals(yValues1[i] * yValues2[i], res.getY(i));
        }

        // проверка исключений
        double[] xValues3 = {12, 13, 14}; // разные Иксы
        var func3 = new LinkedListTabulatedFunction(xValues3, yValues1);
//        var ex3 = assertThrows(TabulatedFunctionOperationService.InconsistentFunctionsException.class,
//                () -> op.multiply(func1, func3) );
//        assertEquals(true, ex3.getMessage().contains("Разные значения"));
        assertThrows(InconsistentFunctionsException.class, () -> op.multiply(func1, func3));

        double[] xValues4 = {21, 23, 24, 25}; // разное колво Иксов
        double[] yValues4 = {21, 23, 24, 25};
        var func4 = new LinkedListTabulatedFunction(xValues4, yValues4);
//        var ex4 = assertThrows(TabulatedFunctionOperationService.InconsistentFunctionsException.class,
//                () -> op.multiply(func1, func4) );
//        assertEquals(true, ex4.getMessage().contains("Разное кол-во"));
        assertThrows(InconsistentFunctionsException.class, () -> op.multiply(func1, func4));
    }

    @Test
    void divisionTest() {

        double[] xValues1 = {2, 3, 4};
        double[] yValues1 = {2, 2, 2};

        double[] xValues2 = {2, 3, 4};
        double[] yValues2 = {1, 2, 3};

        var func1 = new LinkedListTabulatedFunction(xValues1, yValues1);
        var func2 = new LinkedListTabulatedFunction(xValues2, yValues2);

        var fact = new LinkedListTabulatedFunctionFactory();
        var op = new TabulatedFunctionOperationService(fact);

        var res = assertDoesNotThrow(() -> op.division(func1, func2));
        for (int i = 0; i < res.getCount(); i++) {
            assertEquals(yValues1[i] / yValues2[i], res.getY(i));
        }
    }
    @Test
    public void testAdd()
    {
        TabulatedFunctionFactory factoryA = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionFactory factoryB = new ArrayTabulatedFunctionFactory();
        TabulatedFunction a = factoryA.create(xValues, yValues);
        TabulatedFunction b = factoryB.create(xValues, yValues);

        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction result = service.add(a, b);

        assertEquals(0, result.getY(0));
        assertEquals(2, result.getY(1));
        assertEquals(8, result.getY(2));
        assertEquals(1, result.getX(1));
    }

    @Test
    public void testSubtract()
    {
        TabulatedFunctionFactory factoryA = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory factoryB = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction a = factoryA.create(xValues, yValues);
        TabulatedFunction b = factoryB.create(xValues, yValues);

        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction result = service.subtract(a, b);

        assertEquals(0, result.getY(0));
        assertEquals(0, result.getY(1));
        assertEquals(0, result.getY(2));
    }

    @Test
    public void testInconsistentFunctions()
    {
        TabulatedFunctionFactory factoryA = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionFactory factoryB = new ArrayTabulatedFunctionFactory();
        TabulatedFunction a = factoryA.create(xValues, yValues);
        TabulatedFunction b = factoryB.create(new double[]{0, 1, 2, 4}, new double[]{0, 1, 4, 16});

        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();

        assertThrows(InconsistentFunctionsException.class, () -> service.add(a, b));
        assertThrows(InconsistentFunctionsException.class, () -> service.subtract(a, b));
    }
}