package functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class andThenMethodTest
{
    @Test
    void apply()
    {
        MathFunction f = x -> x * 2;
        MathFunction g = x -> x + 3;
        MathFunction h = x -> x - 5;
        MathFunction composite = f.andThen(g).andThen(h);
        assertEquals(composite.apply(10), 18);
        assertEquals(composite.apply(20), 38);
    }
    @Test
    void apply2()
    {
        MathFunction f = x -> x * 3;
        MathFunction g = x -> x - 3;
        MathFunction h = x -> x + 6;
        MathFunction composite = f.andThen(g).andThen(h);
        assertEquals(composite.apply(10), 33);
    }
    @Test
    void apply3()
    {
        MathFunction f = x -> x * 0.5;
        MathFunction g = x -> Math.sin(x);
        MathFunction composite = g.andThen(f);
        assertEquals(composite.apply(0), 0);
    }
}