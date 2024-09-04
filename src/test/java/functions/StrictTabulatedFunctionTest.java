package functions;

import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class StrictTabulatedFunctionTest {

    double xValues[] = {1, 2, 3};
    double yValues[] = {1, 2, 3};

    TabulatedFunction funkLinked = new LinkedListTabulatedFunction(xValues,yValues);
    TabulatedFunction funkArr = new ArrayTabulatedFunction(xValues, yValues);
    StrictTabulatedFunction strictFuncLinked = new StrictTabulatedFunction(funkLinked);
    StrictTabulatedFunction strictFuncArr = new StrictTabulatedFunction(funkArr);

    @Test
    void apply() {
        assertThrows(UnsupportedOperationException.class, () -> {
            strictFuncLinked.apply(0);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            strictFuncArr.apply(4);
        });

        assertDoesNotThrow(() -> strictFuncLinked.apply(1));
        assertDoesNotThrow(() -> strictFuncArr.apply(1));

        assertEquals(strictFuncLinked.apply(1), 1);
        assertEquals(strictFuncArr.apply(1), 1);
    }

    @Test
    void getCount() {
        assertEquals( 3, strictFuncLinked.getCount());
        assertEquals(3, strictFuncArr.getCount());
    }

    @Test
    void getX() {

        assertThrows(IllegalArgumentException.class, () -> {
            strictFuncLinked.getX(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            strictFuncArr.getX(-1);
        });

        assertDoesNotThrow(() -> strictFuncLinked.getX(0));
        assertDoesNotThrow(() -> strictFuncArr.getX(0));

        assertEquals(1, strictFuncLinked.getX(0));
        assertEquals( 1, strictFuncArr.getX(0));
    }

    @Test
    void getY() {
        assertThrows(IllegalArgumentException.class, () -> {
            strictFuncLinked.getY(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            strictFuncArr.getY(-1);
        });

        assertDoesNotThrow(() -> strictFuncLinked.getY(0));
        assertDoesNotThrow(() -> strictFuncArr.getY(0));

        assertEquals(1, strictFuncLinked.getY(0));
        assertEquals( 1, strictFuncArr.getY(0));
    }

    @Test
    void setY() {
        assertThrows(IllegalArgumentException.class, () -> {
            strictFuncLinked.setY(-1, 2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            strictFuncArr.setY(-1, 2);
        });

        assertDoesNotThrow(() -> strictFuncLinked.setY(0, 0));
        assertDoesNotThrow(() -> strictFuncArr.setY(0, 0));

        assertEquals(0, strictFuncLinked.getY(0));
        assertEquals( 0, strictFuncArr.getY(0));

        assertDoesNotThrow(() -> strictFuncLinked.setY(0, 1));
        assertDoesNotThrow(() -> strictFuncArr.setY(0, 1));

        assertEquals(1, strictFuncLinked.getY(0));
        assertEquals( 1, strictFuncArr.getY(0));
    }

    @Test
    void indexOfX() {
        assertEquals(0, strictFuncLinked.indexOfX(1));
        assertEquals(0, strictFuncArr.indexOfX(1));
    }

    @Test
    void indexOfY() {
        assertEquals(0, strictFuncLinked.indexOfY(1));
        assertEquals(0, strictFuncArr.indexOfY(1));
    }

    @Test
    void leftBound() {
        assertEquals(1, strictFuncLinked.leftBound());
        assertEquals(1, strictFuncArr.leftBound());
    }

    @Test
    void rightBound() {
        assertEquals(3, strictFuncLinked.rightBound());
        assertEquals(3, strictFuncArr.rightBound());
    }

    @Test
    void iteratorLinkedTestWhile() {
        var func = new LinkedListTabulatedFunction(new double[]{1, 2}, new double[]{2, 3});
        StrictTabulatedFunction strictFuncLinked = new StrictTabulatedFunction(func);

        Iterator<Point> iterator = strictFuncLinked.iterator();
        var idx = 0;

        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(strictFuncLinked.getX(idx), point.x);
            assertEquals(strictFuncLinked.getY(idx), point.y);
            idx++;
        }
    }
    @Test
    void iteratorLinkedTestForEach() {
        var func = new LinkedListTabulatedFunction(new double[]{1, 2, 3}, new double[]{2, 3, 4});
        StrictTabulatedFunction strictFuncLinked = new StrictTabulatedFunction(func);
        var idx = 0;
        for (Point point : strictFuncLinked) {
            assertEquals(strictFuncLinked.getX(idx), point.x);
            assertEquals(strictFuncLinked.getY(idx), point.y);
            idx++;
        }
    }
}