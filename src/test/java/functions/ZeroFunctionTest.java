package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ZeroFunctionTest
{
    ZeroFunction test;
    @BeforeEach
    void testCreate()
    {
        test = new ZeroFunction();
    }

    @ParameterizedTest
    @CsvSource
            (value = {
                    "1, 0",
                    "0.5, 0",
                    "-2, 0",
                    "0, 0"
            })

    void check(double orig, double calc)
    {
        assertEquals(test.apply(orig), calc);
    }
}