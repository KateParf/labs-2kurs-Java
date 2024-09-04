package io;

import functions.ArrayTabulatedFunction;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class ArrayTabulatedFunctionSerialization
{
    public static void main(String[] args)
    {
        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        // создание файла для записи сериализованных данных
        String filePath = "output/serialized array functions.bin";

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath)))
        {
            // создание табулированной функции
            ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

            // нахождение первой и второй производных функции
            TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator();
            ArrayTabulatedFunction firstDerivative = (ArrayTabulatedFunction) differentialOperator.derive(function);
            ArrayTabulatedFunction secondDerivative = (ArrayTabulatedFunction) differentialOperator.derive(firstDerivative);

            // сериализация функций
            FunctionsIO.serialize(outputStream, function);
            FunctionsIO.serialize(outputStream, firstDerivative);
            FunctionsIO.serialize(outputStream, secondDerivative);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // десериализация функций из файла
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath)))
        {
            ArrayTabulatedFunction deserializedFunction = (ArrayTabulatedFunction) FunctionsIO.deserialize(inputStream);
            ArrayTabulatedFunction deserializedFirstDerivative = (ArrayTabulatedFunction) FunctionsIO.deserialize(inputStream);
            ArrayTabulatedFunction deserializedSecondDerivative = (ArrayTabulatedFunction) FunctionsIO.deserialize(inputStream);

            // вывод значений функций
            System.out.println(deserializedFunction.toString());
            System.out.println(deserializedFirstDerivative.toString());
            System.out.println(deserializedSecondDerivative.toString());
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}