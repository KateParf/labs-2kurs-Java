package functions;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.*;

class AtanFunctionTest {

    private AtanFunction test;

    @BeforeEach
    void setUp()  {
        test = new AtanFunction();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, 1.5707963267948966",
            "0, 0"}, ignoreLeadingAndTrailingWhitespace = true)
    void testApply(double funk, double cor) {
        assertEquals(test.apply(funk), cor);
    }
}