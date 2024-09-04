package functions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleFunction {
    // русское имя
    String name();

    // порядковый номер
    int order();
}
