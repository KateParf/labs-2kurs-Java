package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WrapperFunctionsTest
{
    double[] xValues = {0, 1, 2};
    double[] yValues = {0, 1, 4};
    @Test
    public void testStrictWrappedWithUnmodifiable()
    {
        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedFunction strictArrayFunction = new StrictTabulatedFunction(arrayFunction);
        TabulatedFunction unmodifiableStrictArrayFunction = new UnmodifiableTabulatedFunction(strictArrayFunction);

        // Проверка запрета модификации значений
        Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableStrictArrayFunction.setY(1, 2));

        // Проверка запрета интерполяции
        Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableStrictArrayFunction.apply(0.5));
    }

    @Test
    public void testUnmodifiableWrappedWithStrict()
    {
        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedFunction unmodifiableArrayFunction = new UnmodifiableTabulatedFunction(arrayFunction);
        TabulatedFunction strictUnmodifiableArrayFunction = new StrictTabulatedFunction(unmodifiableArrayFunction);

        // Проверка запрета модификации значений
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictUnmodifiableArrayFunction.setY(2, 3));

        // Проверка запрета интерполяции
        Assertions.assertThrows(UnsupportedOperationException.class, () -> strictUnmodifiableArrayFunction.apply(1.5));
    }
}