package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

public class ConstantFunctionTest
{
    ConstantFunction test;
    @BeforeEach
    void testCreate()
    {
        test = new ConstantFunction(100);
    }

    @ParameterizedTest
    @CsvSource
            (value = {
                    "1, 100",
                    "0.5, 100",
                    "-2, 100",
                    "100, 100"
            })

    void check(double orig, double calc)
    {
        assertEquals(test.apply(orig), calc);
    }
}