package io;
import functions.*;
import java.io.*;

import static io.FunctionsIO.writeTabulatedFunction;

public class TabulatedFunctionFileOutputStream {
    public static void main(String[] args){
        try (
                BufferedOutputStream bos1 = new BufferedOutputStream(new FileOutputStream("output/array function.bin"));
                BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream("output/linked list function.bin"))
        )
        {
            double[] xValues = {2, 4, 6, 8, 10, 12};
            double[] yValues = {1, 3, 5, 7, 9, 11};

            TabulatedFunction arrF = new ArrayTabulatedFunction(xValues, yValues);
            TabulatedFunction linkedF = new LinkedListTabulatedFunction(xValues, yValues);

            writeTabulatedFunction(bos1, arrF);
            writeTabulatedFunction(bos2, linkedF);
        }
        catch(IOException exc){
            exc.printStackTrace();
        }
    }
}
