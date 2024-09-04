package operations;

import functions.MathFunction;

public abstract class SteppingDifferentialOperator implements DifferentialOperator<MathFunction>
{
    private double step;
    public SteppingDifferentialOperator(double step)
    {
        if (Double.isNaN(step) || step <= 0 || step == Double.POSITIVE_INFINITY)
        {
            throw new IllegalArgumentException("Недопустимое значение");
        }
        this.step = step;
    }

    public double getStep()
    {
        return step;
    }

    public void setStep(double step)
    {
        this.step = step;
    }
}