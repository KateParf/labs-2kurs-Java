package concurrent;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import functions.Point;
import functions.TabulatedFunction;
import operations.TabulatedFunctionOperationService;

import java.util.*;

public class SynchronizedTabulatedFunction implements TabulatedFunction {
    private final TabulatedFunction func;
    private final Object mutex;     // Object on which to synchronize

    public SynchronizedTabulatedFunction(TabulatedFunction func) {
        this.func = func;
        mutex = this;
    }

    @Override
    public double apply(double x) {
        synchronized (mutex) {
            return func.apply(x);
        }
    }

    @Override
    public int getCount() {
        synchronized (mutex) {
            return func.getCount();
        }
    }

    @Override
    public double getX(int index) {
        synchronized (mutex) {
            return func.getX(index);
        }
    }

    @Override
    public double getY(int index) {
        synchronized (mutex) {
            return func.getY(index);
        }
    }

    @Override
    public void setY(int index, double value) {
        synchronized (mutex) {
            func.setY(index, value);
        }
    }

    @Override
    public int indexOfX(double x) {
        synchronized (mutex) {
            return func.indexOfX(x);
        }
    }

    @Override
    public int indexOfY(double y) {
        synchronized (mutex) {
            return func.indexOfY(y);
        }
    }

    @Override
    public double leftBound() {
        synchronized (mutex) {
            return func.leftBound();
        }
    }

    @Override
    public double rightBound() {
        synchronized (mutex) {
            return func.rightBound();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        Point[] points;
        synchronized (mutex) {
            points = TabulatedFunctionOperationService.asPoints(func);
        }
        return new ArrayIterator<Point>(points);
    }

    public interface Operation<T>
    {
        T apply(SynchronizedTabulatedFunction function);
    }

    public <T> T doSynchronously(Operation<T> operation)
    {
        synchronized (this)
        {
            return operation.apply(this);
        }
    }
}