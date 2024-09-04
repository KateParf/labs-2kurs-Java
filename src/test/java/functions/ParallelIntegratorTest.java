package functions;

import concurrent.SynchronizedTabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelIntegratorTest {
    @Test
    void integrateTest() {
        long startTime;
        long endTime;

        // ф-я конст, интеграл = 9-1 * 1 = 8
        // для первичной проверки работы
        TabulatedFunction arrayFunc = new ArrayTabulatedFunction(new double[]{1, 3, 5, 7, 9}, new double[]{1, 1, 1, 1, 1});
        ParallelIntegrator integralOperator1 = new ParallelIntegrator();

        var prec1 = 0.1;
        var f = assertDoesNotThrow(()-> integralOperator1.integrate(arrayFunc, prec1));
        assertEquals(Math.abs(8 - f) <= prec1, true);

        //--
        // проверяем несколько потоков
        ParallelIntegrator integralOperator5 = new ParallelIntegrator(5);

        var prec2 = 1;
        f = assertDoesNotThrow(()-> integralOperator5.integrate(arrayFunc, prec2));
        assertEquals(Math.abs(8 - f) <= prec2, true);

        var prec3 = 0.001;
        f = assertDoesNotThrow(()-> integralOperator5.integrate(arrayFunc, prec3));
        assertEquals(Math.abs(8 - f) <= prec3, true);

        //--
        // более сложная ф-я для интегрирования
        // интеграл = (9-1) * (10+2)/2 = 48
        TabulatedFunction arrayFunc2 = new ArrayTabulatedFunction(new double[]{1, 3, 5, 7, 9}, new double[]{2, 4, 6, 8, 10});
        var prec4 = 0.01;
        f = assertDoesNotThrow(()-> integralOperator5.integrate(arrayFunc2, prec4));
        assertEquals(Math.abs(48 - f) <= prec4, true);

        //--
        // еще более сложная ф-я для интегрирования
        // синус на периоде = 0
        MathFunction sinF = new MathFunction() {
            public double apply(double x) {
                return Math.sin(x);
            }
        };
        TabulatedFunction arrayFuncSin = new ArrayTabulatedFunction(sinF, 0, 2*3.14159, 100);
        var prec5 = 0.000001;
        startTime = System.currentTimeMillis();
        f = assertDoesNotThrow(()-> integralOperator5.integrate(arrayFuncSin, prec5));
        endTime = System.currentTimeMillis();
        var time1 = endTime - startTime;
        System.out.println(time1);
        assertEquals(Math.abs(f) <= prec5, true);

        // а теперь сравним с 1 потоком
        startTime = System.currentTimeMillis();
        f = assertDoesNotThrow(()-> integralOperator1.integrate(arrayFuncSin, prec5));
        endTime = System.currentTimeMillis();
        var time2 = endTime - startTime;
        System.out.println(time2);
        assertEquals(Math.abs(f) <= prec5, true);

        assertEquals(true,time1 < time2);

    }
}