package functions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

public abstract class AbstractTabulatedFunction implements TabulatedFunction
{
    //Метод получения количества табулированных значений
    public abstract int getCount();

    //Метод, получающий значение аргумента x по номеру индекса
    public abstract double getX(int index);

    //Метод, получающий значение y по номеру индекса
    public abstract double getY(int index);

    //Метод, задающий значение y по номеру индекса
    public abstract void setY(int index, double value);

    //Метод, возвращающий индекс аргумента x
    public abstract int indexOfX(double x);

    //Метод, возвращающий индекс первого вхождения значения y. Если такого y в таблице нет, то необходимо вернуть -1
    public abstract int indexOfY(double y);

    //Метод, возвращающий самый левый x
    public abstract double leftBound();

    //Метод, возвращающий самый правый x
    public abstract double rightBound();

    protected int floorIndexOfX(double x) {
        return 0;
    }

    protected double extrapolateLeft(double x) {
        return 0;
    }

    protected double extrapolateRight(double x) {
        return 0;
    }

    protected double interpolate(double x, int floorIndex) {
        return 0;
    }

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY)
    {
        return leftY + (x - leftX) * (rightY - leftY) / (rightX - leftX);
    }

    public double apply(double x)
    {
        return 0;
    }

    public static void checkLengthIsTheSame(double[] xValues, double[] yValues) throws DifferentLengthOfArraysException
    {
        if (xValues.length != yValues.length)
        {
            throw new DifferentLengthOfArraysException("Длины массивов x и y не равны");
        }
    }

    public static void checkSorted(double[] xValues) throws ArrayIsNotSortedException
    {
        for (int i = 1; i < xValues.length; i++)
        {
            if (xValues[i] < xValues[i - 1])
            {
                throw new ArrayIsNotSortedException("Массив x не отсортирован");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(getClass().getSimpleName()).append(" size = ").append(getCount()).append("\n");
        for(Point point : this){
            str.append("[").append(+ point.x).append("; ").append(point.y).append("]").append("\n");
        }
        str.deleteCharAt(str.length()-1);
        return str.toString();
    }
}