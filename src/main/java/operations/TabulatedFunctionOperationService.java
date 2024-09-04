package operations;

import exceptions.InconsistentFunctionsException;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class TabulatedFunctionOperationService
{
    private TabulatedFunctionFactory factory;
    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory)
    {
        this.factory = factory;
    }

    public TabulatedFunctionOperationService()
    {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedFunctionFactory getFactory()
    {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory)
    {
        this.factory = factory;
    }

    public static Point[] asPoints(TabulatedFunction tabulatedFunction)
    {
        Point[] points = new Point[tabulatedFunction.getCount()];
        int i = 0;
        for (Point point : tabulatedFunction)
        {
            points[i] = point;
            i++;
        }
        return points;
    }

    // Приватный интерфейс BiOperation
    private interface BiOperation
    {
        double apply(double u, double v);
    }

    // Метод doOperation
    private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation)
    {
        int sizeA = a.getCount();
        int sizeB = b.getCount();
        if (sizeA != sizeB)
        {
            throw new InconsistentFunctionsException("Количество записей в первой функции не равно количеству записей во второй");
        }
        Point[] pointsA = asPoints(a);
        Point[] pointsB = asPoints(b);
        double[] xValues = new double[sizeA];
        double[] yValues = new double[sizeA];

        for (int i = 0; i < sizeA; i++)
        {

            if (pointsA[i].x != pointsB[i].x)
            {
                throw new InconsistentFunctionsException("Значения x в функциях не совпадают");
            }

            xValues[i] = pointsA[i].x;
            yValues[i] = operation.apply(pointsA[i].y, pointsB[i].y);

        }
        TabulatedFunction function = factory.create(xValues, yValues);
        return function;
    }

    // Методы сложения и вычитания
    public TabulatedFunction add(TabulatedFunction a, TabulatedFunction b)
    {
        return doOperation(a, b,  (u, v) -> u + v);
    }

    public TabulatedFunction subtract(TabulatedFunction a, TabulatedFunction b)
    {
        return doOperation(a, b, (u, v) -> u - v);
    }

    // Методы умножения и деления
    public TabulatedFunction multiply(TabulatedFunction a, TabulatedFunction b)
            throws InconsistentFunctionsException {
        return doOperation(a, b,  (y1, y2) -> y1 * y2 );
    }

    public TabulatedFunction division(TabulatedFunction a, TabulatedFunction b)
            throws InconsistentFunctionsException {
        return doOperation(a, b,  (y1, y2) -> y1 / y2 );
    }
}