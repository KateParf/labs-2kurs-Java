package functions;

import java.util.Iterator;

public class StrictTabulatedFunction implements TabulatedFunction{
    TabulatedFunction function;

    public StrictTabulatedFunction(TabulatedFunction function) {
        this.function = function;
    }
    public int getCount() {
        return function.getCount();
    }

    public double getX(int index){
        return function.getX(index);
    }

    public double getY(int index){
        return function.getY(index);
    }

    public void setY(int index, double value){
        function.setY(index, value);
    }

    public int indexOfX(double x){
        return function.indexOfX(x);
    }

    public int indexOfY(double y){
        return function.indexOfY(y);
    }

    public double leftBound(){
        return function.leftBound();
    }

    public double rightBound(){
        return function.rightBound();
    }

    @Override
    public double apply(double x) {
        int idx = indexOfX(x);
        if (idx == -1)
            throw new UnsupportedOperationException();
        else
            return getY(idx);
    }

    @Override
    public Iterator<Point> iterator() {
        return function.iterator();
    }
}
