package functions;

@SimpleFunction(name="Константа", order=1)
public class ConstantFunction implements MathFunction
{
    private final double constVal;
    public ConstantFunction (double constVal)
    {
        this.constVal = constVal;
    }
    public double getConst()
    {
        return constVal;
    }
    public double apply(double x)
    {
        return constVal;
    }
}