package functions;

import exceptions.DifferentLengthOfArraysException;
import exceptions.ArrayIsNotSortedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractTabulatedFunctionTest
{
    @Test
    void checkLengthIsTheSame1()
    {
        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};
        Assertions.assertDoesNotThrow(() -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
    }
    @Test
    void checkLengthIsTheSame2()
    {
        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9};
        Assertions.assertThrows(DifferentLengthOfArraysException.class, () -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
    }

    @Test
    void checkSorted1()
    {
        double[] xValues = {2, 4, 6, 8, 10, 12};
        Assertions.assertDoesNotThrow(() -> AbstractTabulatedFunction.checkSorted(xValues));
    }
    @Test
    void checkSorted2()
    {
        double[] xValues = {2, 4, 6, 8, 12, 10};
        Assertions.assertThrows(ArrayIsNotSortedException.class, () -> AbstractTabulatedFunction.checkSorted(xValues));
    }

    @Test
    void toStr()
    {
        double[] xValues = {0, 0.5, 1.0};
        double[] yValues = {0, 0.25, 1.0};
        var funk = new LinkedListTabulatedFunction(xValues, yValues);
        assertEquals("LinkedListTabulatedFunction size = 3\n[0.0; 0.0]\n[0.5; 0.25]\n[1.0; 1.0]", funk.toString());
    }
}