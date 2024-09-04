package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityFunctionTest {

    private IdentityFunction test;

    @BeforeEach
    void setUp()  {
        test = new IdentityFunction();
    }

    @Test
    void testApply1() {
        assertEquals(test.apply(1), 1);
    }

    @Test
    void testApply2() {
        assertEquals(test.apply(0.5), 0.5);
    }

    @Test
    void testApply3() {
        assertEquals(test.apply(-2), -2);
    }

    @Test
    void testApply4() {
        assertEquals(test.apply(-0.01), -0.01);
    }

    @Test
    public void testToString()
    {
        IdentityFunction func = new IdentityFunction();
        String res = func.toString();
        assertEquals("class Identity Function", res);
    }

    @Test
    public void testEqualsSameObject()
    {
        IdentityFunction func = new IdentityFunction();
        assertTrue(func.equals(func));
    }

    @Test
    public void testEqualsDifferentClass()
    {
        IdentityFunction func = new IdentityFunction();
        assertFalse(func.equals(new Object()));
    }

    @Test
    public void testEqualsSameClass()
    {
        IdentityFunction func1 = new IdentityFunction();
        IdentityFunction func2 = new IdentityFunction();
        assertTrue(func1.equals(func2));
    }

    @Test
    public void testHashCode()
    {
        IdentityFunction func1 = new IdentityFunction();
        int hashCode1 = func1.hashCode();
        IdentityFunction func2 = new IdentityFunction();
        int hashCode2 = func2.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testClone()
    {
        IdentityFunction func1 = new IdentityFunction();
        IdentityFunction func2 = (IdentityFunction) func1.clone();
        assertEquals(func1, func2);
        assertNotSame(func1, func2);
    }
}