package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

public class UnitFunctionTest
{
    UnitFunction test;
    @BeforeEach
    void testCreate()
    {
        test = new UnitFunction();
    }

    @ParameterizedTest
    @CsvSource
            (value = {
                    "1, 1",
                    "0.5, 1",
                    "-2, 1",
                    "0, 1"
            })

    void check(double orig, double calc)
    {
        assertEquals(test.apply(orig), calc);
    }
}