package operations;

import functions.MathFunction;

public class MiddleSteppingDifferentialOperator extends SteppingDifferentialOperator
{
    public MiddleSteppingDifferentialOperator(double step)
    {
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction function)
    {
        return new MathFunction()
        {
            @Override
            public double apply(double x)
            {
                double fPlus = function.apply(x + getStep());
                double fMinus = function.apply(x - getStep());
                return (fPlus - fMinus) / (2 * getStep());
            }
        };
    }
}