package operations;

import functions.*;

public interface DifferentialOperator<T extends MathFunction> {
    T derive(T function);
}