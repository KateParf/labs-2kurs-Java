package io;

import functions.*;
import functions.factory.*;
import operations.TabulatedDifferentialOperator;

import java.io.*;
import java.io.IOException;

import static io.FunctionsIO.readTabulatedFunction;

public class TabulatedFunctionFileInputStream {
    public static void main(String[] args){
        // 1 func
        try (BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream("input/binary function.bin")))
        {
            TabulatedFunctionFactory arrF = new ArrayTabulatedFunctionFactory();
            TabulatedFunction function = readTabulatedFunction(bis1, arrF);
            System.out.println(function.toString());
        }
        catch(IOException exc){
            exc.printStackTrace();
        }

        // 2 func
        try {
            System.out.println("Введите размер и значения функции: ");
            BufferedReader bis2 = new BufferedReader(new InputStreamReader(System.in));
            TabulatedFunctionFactory linkedFact = new LinkedListTabulatedFunctionFactory();
            TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(linkedFact);
            TabulatedFunction function = FunctionsIO.readTabulatedFunction(bis2, linkedFact);
            System.out.println(operator.derive(function).toString());
        }
        catch(IOException exc){
            exc.printStackTrace();
        }

    }
}
