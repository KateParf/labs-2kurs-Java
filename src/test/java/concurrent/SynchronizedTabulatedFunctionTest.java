package concurrent;

import functions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTabulatedFunctionTest {

    double[] xValues = {1, 2, 3};
    double[] yValues = {1, 2, 3};

    TabulatedFunction funkLinked = new LinkedListTabulatedFunction(xValues,yValues);
    TabulatedFunction funkArr = new ArrayTabulatedFunction(xValues, yValues);
    SynchronizedTabulatedFunction syncFunkLinked = new SynchronizedTabulatedFunction(funkLinked);
    SynchronizedTabulatedFunction syncFunkArr = new SynchronizedTabulatedFunction(funkArr);

    @Test
    void apply() {
        assertEquals(syncFunkLinked.apply(1), funkLinked.apply(1));
        assertEquals(syncFunkArr.apply(1), funkArr.apply(1));
    }

    @Test
    void getCount() {
        assertEquals( funkLinked.getCount(), syncFunkLinked.getCount());
        assertEquals(funkArr.getCount(), syncFunkArr.getCount());
    }

    @Test
    void getX() {
        assertEquals(funkLinked.getX(0), syncFunkLinked.getX(0));
        assertEquals(funkArr.getX(0), syncFunkArr.getX(0));
    }

    @Test
    void getY() {
        assertEquals(funkLinked.getY(0), syncFunkLinked.getY(0));
        assertEquals(funkArr.getY(0), syncFunkArr.getY(0));
    }

    @Test
    void setY() {

        assertEquals(funkLinked.getY(0), syncFunkLinked.getY(0));
        assertEquals(funkArr.getY(0), syncFunkArr.getY(0));
    }

    @Test
    void indexOfX() {
        assertEquals(funkLinked.indexOfX(1), syncFunkLinked.indexOfX(1));
        assertEquals(funkArr.indexOfX(1), syncFunkArr.indexOfX(1));
    }

    @Test
    void indexOfY() {
        assertEquals(funkLinked.indexOfY(1), syncFunkLinked.indexOfY(1));
        assertEquals(funkArr.indexOfY(1), syncFunkArr.indexOfY(1));
    }

    @Test
    void leftBound() {
        assertEquals(funkLinked.leftBound(), syncFunkLinked.leftBound());
        assertEquals(funkArr.leftBound(), syncFunkArr.leftBound());
    }

    @Test
    void rightBound() {
        assertEquals(funkLinked.rightBound(), syncFunkLinked.rightBound());
        assertEquals(funkArr.rightBound(), syncFunkArr.rightBound());
    }

    @Test
    void iteratorLinkedTestWhile() {
        Iterator<Point> syncIterator = syncFunkLinked.iterator();
        var idx = 0;
        while (syncIterator.hasNext()) {
            Point point = syncIterator.next();
            assertEquals(syncFunkLinked.getX(idx), point.x);
            assertEquals(syncFunkLinked.getY(idx), point.y);
            assertEquals(funkLinked.getX(idx), point.x);
            assertEquals(funkLinked.getY(idx), point.y);
            idx++;
        }
    }

    @Test
    void iteratorArrTestForEach() {
        var idx = 0;
        for (Point point : syncFunkArr) {
            assertEquals(syncFunkArr.getX(idx), point.x);
            assertEquals(syncFunkArr.getY(idx), point.y);
            assertEquals(funkArr.getX(idx), point.x);
            assertEquals(funkArr.getY(idx), point.y);
            idx++;
        }
    }

    @Test
    public void testIntegerOperation()
    {
        SynchronizedTabulatedFunction.Operation<Integer> intOperation = lambda ->
        {
            // реализация операции для Integer с использованием лямбда-выражения
            return 42; // например просто вернем константу 42 в качестве результата
        };
        assertEquals(42, syncFunkArr.doSynchronously(intOperation));
    }

    @Test
    public void testStringOperation()
    {
        SynchronizedTabulatedFunction.Operation<String> stringOperation = lambda ->
        {
            // реализация операции для String с использованием лямбда-выражения
            return "Hello, World!";
        };
        assertEquals("Hello, World!", syncFunkArr.doSynchronously(stringOperation));
    }

    @Test
    public void testVoidOperation()
    {
        SynchronizedTabulatedFunction.Operation<Void> voidOperation = lambda ->
        {
            // реализация операции для Void с использованием лямбда-выражения
            return null; // ничего не возвращаем
        };
        assertNull(syncFunkArr.doSynchronously(voidOperation));
    }
}