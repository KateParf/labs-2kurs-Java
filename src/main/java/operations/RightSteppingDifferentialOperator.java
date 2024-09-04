package operations;

import functions.MathFunction;

public class RightSteppingDifferentialOperator extends SteppingDifferentialOperator
{
    public RightSteppingDifferentialOperator(double step)
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
                double f = function.apply(x);
                return (fPlus - f) / getStep();
            }
        };
    }
}