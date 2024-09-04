package operations;

import functions.MathFunction;

public class LeftSteppingDifferentialOperator extends SteppingDifferentialOperator
{
    public LeftSteppingDifferentialOperator(double step)
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
                double fMinus = function.apply(x - getStep());
                double f = function.apply(x);
                return (f - fMinus) / getStep();
            }
        };
    }
}