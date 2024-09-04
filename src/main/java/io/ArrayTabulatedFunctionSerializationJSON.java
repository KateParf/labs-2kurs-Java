package io;

import functions.ArrayTabulatedFunction;

import java.io.*;

public class ArrayTabulatedFunctionSerializationJSON {
    public static void main(String[] args)
    {
        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/function.json")))
        {
            FunctionsIO.serializeJson(writer, function);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("output/function.json")))
        {
            ArrayTabulatedFunction deserializedFunction = FunctionsIO.deserializeJson(reader);
            System.out.println(deserializedFunction.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
