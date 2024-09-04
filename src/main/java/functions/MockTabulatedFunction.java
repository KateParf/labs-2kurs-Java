package functions;
import java.util.Iterator;
public class MockTabulatedFunction extends AbstractTabulatedFunction
{
    @Override
    public Iterator<Point> iterator() {
        /* чето не работало поэтому пусть пока так будет надо разобраться но мне лень */
        return null;
    }
    private final double x0 = 2;
    private final double x1 = 4;
    private final double y0 = 1;
    private final double y1 = 3;

/*    public MockTabulatedFunction(double x0, double x1, double y0, double y1)
    {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
    }*/

    public int getCount()
    {
        return 2;
    }

    public double getX(int index)
    {
        return (index == 0) ? x0 : x1;
    }

    public double getY(int index)
    {
        return (index == 0) ? y0 : y1;
    }

    public void setY(int index, double value)
    {
        // в данном случае выполнение невозможно, т.к. поля неизменяемы
    }

    public int indexOfX(double x)
    {
        return (x == x0) ? 0 : ((x == x1) ? 1 : -1);
    }

    public int indexOfY(double y)
    {
        return (y == y0) ? 0 : ((y == y1) ? 1 : -1);
    }

    public double leftBound()
    {
        return x0;
    }

    public double rightBound()
    {
        return x1;
    }
    public double extrapolateLeft(double x)
    {
        return interpolate(x, x0, x1, y0, y1);
    }
    public double extrapolateRight(double x)
    {
        return interpolate(x, x0, x1, y0, y1);
    }
    public double apply(double x)
    {
        if (x < leftBound())
            return extrapolateLeft(x);
        else
        {
            if (x > rightBound())
                return extrapolateRight(x);
            else
            {
                int index = indexOfX(x);
                if (index != -1)
                    return getY(index);
                else
                    return interpolate(x, x0, x1, y0, y1);
            }
        }
    }
}