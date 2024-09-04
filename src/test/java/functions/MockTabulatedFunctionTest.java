package functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MockTabulatedFunctionTest
{
    MockTabulatedFunction res = new MockTabulatedFunction();

    @Test
    public void testInterpolate()
    {
        assertEquals(res.interpolate(3, 2, 4, 1, 3), 2, 0.001);
    }
    @Test
    public void testApply()
    {
        assertEquals(res.apply(2), 1, 0.001);
        assertEquals(res.apply(1), 0, 0.001);
        assertEquals(res.apply(5), 4, 0.001);
        assertEquals(res.apply(3), 2, 0.001);
    }
}