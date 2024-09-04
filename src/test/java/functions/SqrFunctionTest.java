package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

public class SqrFunctionTest
{
    SqrFunction test;
    @BeforeEach
    void testCreate()
    {
        test = new SqrFunction();
    }

    @ParameterizedTest
    @CsvSource
            (value = {
                "1, 1",
                "0.5, 0.25",
                "-2, 4",
                "10, 100"
            })

    void check(double orig, double calc)
    {
        assertEquals(test.apply(orig), calc);
    }
}