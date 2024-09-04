package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnmodifiableTabulatedFunctionTest
{
    @Test
    public void testSetYArrayTabulatedFunction()
    {
        double[] xValues = {0, 1, 2};
        double[] yValues = {0, 1, 4};
        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedFunction unmodifiableArrayFunction = new UnmodifiableTabulatedFunction(arrayFunction);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableArrayFunction.setY(1, 2));
    }

    @Test
    public void testSetYThrowsUnsupportedOperationExceptionForLinkedListTabulatedFunction()
    {
        double[] xValues = {0, 1, 2};
        double[] yValues = {0, 1, 4};
        TabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);
        TabulatedFunction unmodifiableLinkedListFunction = new UnmodifiableTabulatedFunction(linkedListFunction);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableLinkedListFunction.setY(2, 3));
    }
}