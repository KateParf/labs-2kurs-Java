package operations;

import functions.MathFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SteppingDifferentialOperatorTest
{
    @Test
    public void testLeftSteppingDifferentialOperator()
    {
        LeftSteppingDifferentialOperator leftOperator = new LeftSteppingDifferentialOperator(0.01);
        MathFunction sqrFunc = new SqrFunction();
        MathFunction derivedFunc = leftOperator.derive(sqrFunc);

        double derivedValue = derivedFunc.apply(2);
        double expectedValue = 2 * 2; // Разностная производная x^2 равна 2x

        assertEquals(expectedValue, derivedValue, 0.1);
    }

    @Test
    public void testRightSteppingDifferentialOperator()
    {
        RightSteppingDifferentialOperator rightOperator = new RightSteppingDifferentialOperator(0.01);
        MathFunction sqrFunc = new SqrFunction();
        MathFunction derivedFunc = rightOperator.derive(sqrFunc);

        double derivedValue = derivedFunc.apply(2);
        double expectedValue = 2 * 2; // Разностная производная x^2 равна 2x

        assertEquals(expectedValue, derivedValue, 0.1);
    }

    @Test
    public void testMiddleSteppingDifferentialOperator()
    {
        MiddleSteppingDifferentialOperator middleOperator = new MiddleSteppingDifferentialOperator(0.01);
        MathFunction sqrFunc = new SqrFunction();
        MathFunction derivedFunc = middleOperator.derive(sqrFunc);

        double derivedValue = derivedFunc.apply(2);
        double expectedValue = 2 * 2; // Разностная производная x^2 равна 2x

        assertEquals(expectedValue, derivedValue, 0.1);
    }
}