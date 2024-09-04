package functions;

@SimpleFunction(name="Линейная функция", order=2)
public class IdentityFunction implements MathFunction
{
    public double apply(double x)
    {
        return x;
    }

    @Override
    public String toString()
    {
        return "class Identity Function";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }

    @Override
    public Object clone()
    {
        return new IdentityFunction();
    }
}