package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositeFunctionTest {

    @Test
    void apply() {
        MathFunction f = new IdentityFunction();
        MathFunction g = new SqrFunction();

        MathFunction test = new CompositeFunction(f, g);
        assertEquals(test.apply(-0.01), 0.0001);

        test = new CompositeFunction(g, f);
        assertEquals(test.apply(2), 4);
        assertNotEquals(test.apply(-2), -4);
    }
}