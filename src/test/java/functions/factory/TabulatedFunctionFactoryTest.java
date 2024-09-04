package functions.factory;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.UnmodifiableTabulatedFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TabulatedFunctionFactoryTest
{
    double[] xValues = {1, 2, 3};
    double[] yValues = {2, 4, 6};

    @Test
    void CreateFunctionFactoryTest() {
        TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction funkLinked = factoryLinked.create(new double[]{1, 2}, new double[]{1, 2});
        assertTrue(funkLinked instanceof LinkedListTabulatedFunction);

        TabulatedFunctionFactory factoryArr = new ArrayTabulatedFunctionFactory();
        TabulatedFunction funkArr = factoryArr.create(new double[]{1, 2}, new double[]{1, 2});
        assertTrue(funkArr instanceof ArrayTabulatedFunction);
    }

    @Test
    void StrictFactoryTest(){
        TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction funkLinked = factoryLinked.createStrict(new double[]{1, 2}, new double[]{1, 2});

        TabulatedFunctionFactory factoryArr = new ArrayTabulatedFunctionFactory();
        TabulatedFunction funkArr = factoryArr.createStrict(new double[]{1, 2}, new double[]{1, 2});


        var res1 = assertThrows(UnsupportedOperationException.class, () -> {
            var y = funkLinked.apply(5);
        });
        var res2 = assertThrows(UnsupportedOperationException.class, () -> {
            var y =  funkArr.apply(3);
        });
    }

    @Test
    public void testCreateUnmodifiableArray()
    {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();

        TabulatedFunction function = factory.createUnmodifiable(xValues, yValues);

        // Проверяем, что созданная функция обернута в UnmodifiableTabulatedFunction
        assertTrue(function instanceof UnmodifiableTabulatedFunction);

        // Проверяем, что функция возвращает ожидаемые значения
        assertEquals(2, function.getY(0));
        assertEquals(4, function.getY(1));
        assertEquals(6, function.getY(2));

        // Пытаемся изменить значения функции, должно быть выброшено исключение UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> function.setY(1, 3));
    }

    @Test
    public void testCreateUnmodifiableLinkedList()
    {
        TabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();

        TabulatedFunction function = factory.createUnmodifiable(xValues, yValues);

        // Проверяем, что созданная функция обернута в UnmodifiableTabulatedFunction
        assertTrue(function instanceof UnmodifiableTabulatedFunction);

        // Проверяем, что функция возвращает ожидаемые значения
        assertEquals(2, function.getY(0));
        assertEquals(4, function.getY(1));
        assertEquals(6, function.getY(2));

        // Пытаемся изменить значения функции, должно быть выброшено исключение UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> function.setY(1, 3));
    }

    @Test
    public void testCreateStrictUnmodifiable()
    {
        TabulatedFunctionFactory factory1 = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionFactory factory2 = new ArrayTabulatedFunctionFactory();

        TabulatedFunction strictUnmodifiableFunction1 = factory1.createStrictUnmodifiable(xValues, yValues);
        TabulatedFunction strictUnmodifiableFunction2 = factory2.createStrictUnmodifiable(xValues, yValues);

        assertThrows(UnsupportedOperationException.class, ()->strictUnmodifiableFunction1.apply(7));
        assertThrows(UnsupportedOperationException.class, ()->strictUnmodifiableFunction1.setY(0,3));
        assertThrows(UnsupportedOperationException.class, ()->strictUnmodifiableFunction2.apply(7));
        assertThrows(UnsupportedOperationException.class, ()->strictUnmodifiableFunction2.setY(0, 3));
    }
}