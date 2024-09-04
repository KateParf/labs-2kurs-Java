package functions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import exceptions.InterpolationException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import java.io.Serializable;
import java.io.Serial;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable, Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    //public Iterator<Point> iterator() throws UnsupportedOperationException
    //{
        //throw new UnsupportedOperationException();
    //}
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    protected double[] xValues;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    protected double[] yValues;
    protected int count;


    @Override
    public Iterator<Point> iterator()
    {
        return new Iterator<Point>()
        {
            private int i = 0;
            @Override
            public boolean hasNext()
            {
                return i < count;
            }
            @Override
            public Point next()
            {
                if (!hasNext())
                {
                    throw new NoSuchElementException("Нет следующего элемента");
                }
                double x = xValues[i];
                double y = yValues[i];
                i++;
                return new Point(x, y);
            }
        };
    }

    @JsonCreator
    public ArrayTabulatedFunction(@JsonProperty(value = "xValues") double[] xValues, @JsonProperty(value = "yValues") double[] yValues)
    {
        super();
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.count = xValues.length;
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count)
    {
        if (xFrom > xTo)
        {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }

        this.count = count;
        this.xValues = new double[count];
        this.yValues = new double[count];

        if (xFrom == xTo)
        {
            Arrays.fill(xValues, xFrom);
            Arrays.fill(yValues, source.apply(xFrom));
        }
        else
        {
            double step = (xTo - xFrom) / (count - 1);
            for (int i = 0; i < count; i++)
            {
                xValues[i] = xFrom + i * step;
                yValues[i] = source.apply(xValues[i]);
            }
        }
    }
    public int getCount()
    {
        return this.count;
    }
    public double getX(int index)
    {
        if (index < 0 || index >= count)
            throw new IllegalArgumentException("index is out of range");
        return xValues[index];
    }
    public double getY(int index)
    {
        if (index < 0 || index >= count)
            throw new IllegalArgumentException("index is out of range");
        return yValues[index];
    }
    public void setY(int index, double value)
    {
        if (index < 0 || index >= count)
            throw new IllegalArgumentException("index is out of range");
        yValues[index] = value;
    }
    public int indexOfX(double x)
    {
        for (int i = 0; i < this.count; i++)
        {
            if (xValues[i] == x)
                return i;
        }
        return -1;
    }
    public int indexOfY(double y)
    {
        for (int i = 0; i < this.count; i++)
        {
            if (yValues[i] == y)
                return i;
        }
        return -1;
    }
    public double leftBound()
    {
        return xValues[0];
    }
    public double rightBound()
    {
        return xValues[count-1];
    }
    public int floorIndexOfX(double x)
    {
        if (x < leftBound())
            throw new IllegalArgumentException("x value is less than left bound");

        double maxx = Double.MIN_VALUE;
        int ind = 0;
        for (int i = 0; i < count; i++)
        {
            if (xValues[i] == x)
                return i;
            else
            {
                if ((xValues[i] < x) && (xValues[i] > maxx))
                {
                    maxx = xValues[i];
                    ind = i;
                }
            }
        }
        if ((ind == count-1) && (rightBound() != x))
            return ind+1;
        else
            return ind;
    }

    public double extrapolateLeft(double x)
    {
        if (count == 1)
            return yValues[0];
        else
            return interpolate(x, 0);
    }

    public double extrapolateRight(double x)
    {
        if (count == 1)
            return yValues[0];
        else
            return interpolate(x, count - 2);
    }

    public double interpolate(double x, int floorIndex)
    {
        if (x < xValues[floorIndex] || x > xValues[floorIndex + 1])
        {
            throw new InterpolationException("Значение x находится вне интервала интерполирования");
        }
        if (count == 1)
        {
            return yValues[0];
        }
        double leftX = xValues[floorIndex];
        double leftY = yValues[floorIndex];
        double rightX = xValues[floorIndex + 1];
        double rightY = yValues[floorIndex + 1];
        return interpolate(x, leftX, rightX, leftY, rightY);
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
                {
                    index = floorIndexOfX(x);
                    return interpolate(x, index);
                }
            }
        }
    }
    public void insert(double x, double y)
    {
        if (count == 0)
        {
            xValues = new double[] {x};
            yValues = new double[] {y};
            count++;
        }
        else
            if (x < xValues[0]) // нужно добавить элементы слева
            {
                double[] newXValues = new double[count + 1];
                double[] newYValues = new double[count + 1];
                newXValues[0] = x;
                newYValues[0] = y;
                System.arraycopy(xValues, 0, newXValues, 1, count);
                System.arraycopy(yValues, 0, newYValues, 1, count);
                xValues = newXValues;
                yValues = newYValues;
                count++;
            }
            else
                if (x > xValues[count - 1]) // нужно добавить элементы справа
                {
                    double[] newXValues = Arrays.copyOf(xValues, count + 1);
                    double[] newYValues = Arrays.copyOf(yValues, count + 1);
                    newXValues[count] = x;
                    newYValues[count] = y;
                    xValues = newXValues;
                    yValues = newYValues;
                    count++;
                }
                else // нужно добавить элементы в середину
                {
                    int ind = floorIndexOfX(x);
                    if (x == xValues[ind]) // если такой x нашелся
                        yValues[ind] = y; // то переписываем y
                    else
                    {
                        double[] newXValues = new double[count + 1];
                        double[] newYValues = new double[count + 1];
                        // копируем элементы до ind
                        System.arraycopy(xValues, 0, newXValues, 0, ind + 1);
                        System.arraycopy(yValues, 0, newYValues, 0, ind + 1);
                        // вставляем на нужное место новые элементы
                        newXValues[ind + 1] = x;
                        newYValues[ind + 1] = y;
                        // копируем элементы после ind
                        System.arraycopy(xValues, ind + 1, newXValues, ind + 2, count - ind - 1);
                        System.arraycopy(yValues, ind + 1, newYValues, ind + 2, count - ind - 1);
                        xValues = newXValues;
                        yValues = newYValues;
                        count++;
                    }
                }
    }
    public void remove(int index)
    {
        double[] newXValues = new double[count - 1];
        double[] newYValues = new double[count - 1];
        // копируем элементы до index
        System.arraycopy(xValues, 0, newXValues, 0, index);
        System.arraycopy(yValues, 0, newYValues, 0, index);
        // копируем элементы после index
        System.arraycopy(xValues, index + 1, newXValues, index, count - index - 1);
        System.arraycopy(yValues, index + 1, newYValues, index, count - index - 1);
        xValues = newXValues;
        yValues = newYValues;
        count--;
    }

    /*
//    @Override
//    public String toString()
//    {
//        StringBuilder str = new StringBuilder();
//        str.append("{");
//        for (int i = 0; i < xValues.length; i++)
//        {
//            str.append("(").append(xValues[i]).append(", ").append(yValues[i]).append(")");
//            if (i < xValues.length - 1)
//                str.append(", ");
//        }
//        str.append("}");
//        return str.toString();
//    }
    */

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ArrayTabulatedFunction that = (ArrayTabulatedFunction) o;
        return Arrays.equals(xValues, that.xValues) && Arrays.equals(yValues, that.yValues);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(Arrays.hashCode(xValues), Arrays.hashCode(yValues));
    }

    @Override
    public Object clone()
    {
        double[] clonedXValues = Arrays.copyOf(xValues, xValues.length);
        double[] clonedYValues = Arrays.copyOf(yValues, yValues.length);
        return new ArrayTabulatedFunction(clonedXValues, clonedYValues);
    }
}