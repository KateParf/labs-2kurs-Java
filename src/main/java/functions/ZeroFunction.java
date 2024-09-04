package functions;

@SimpleFunction(name="Нулевая функция", order=6)
public class ZeroFunction extends ConstantFunction
{
    public ZeroFunction()
    {
        super(0);
    }
}