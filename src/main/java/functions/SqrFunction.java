package functions;

@SimpleFunction(name="Квадратная функция", order=3)
public class SqrFunction implements MathFunction // квадрат x
{
    public double apply(double x)
    {
        return Math.pow(x,2);
    }
}