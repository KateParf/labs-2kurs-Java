package functions;

public interface TabulatedFunction extends MathFunction, Iterable<Point>{
    //Метод получения количества табулированных значений
    int getCount();

    //Метод, получающий значение аргумента x по номеру индекса
    double getX(int index);

    //Метод, получающий значение y по номеру индекса
    double getY(int index);

    //Метод, задающий значение y по номеру индекса
    void setY(int index, double value);

    //Метод, возвращающий индекс аргумента x
    int indexOfX(double x);

    //Метод, возвращающий индекс первого вхождения значения y. Если такого y в таблице нет, то необходимо вернуть -1
    int indexOfY(double y);

    //Метод, возвращающий самый левый x
    double leftBound();

    //Метод, возвращающий самый правый x
    double rightBound();

}
