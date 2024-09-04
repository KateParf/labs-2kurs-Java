package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import static io.FunctionsIO.readTabulatedFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;

public class FunctionsIOTest
{

    @Test
    public void testWriteAndReadTabulatedFunction() throws IOException
    {
        double[] xValues = {0, 0.5, 1.0};
        double[] yValues = {0, 0.25, 1.0};

        File tempFile = new File("temp/writeTest.txt");

        // запись
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        FunctionsIO.writeTabulatedFunction(writer, function);
        writer.close();

        // чтение
        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction result = FunctionsIO.readTabulatedFunction(reader, factory);

        for (int i = 0; i < result.getCount(); i++)
        {
            assertEquals(xValues[i], result.getX(i));
            assertEquals(yValues[i], result.getY(i));
        }

        // запись
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("temp/linkedListFunction.bin"));
        TabulatedFunction linkedF = new LinkedListTabulatedFunction(xValues, yValues);
        FunctionsIO.writeTabulatedFunction(bos, linkedF);

        // чтение
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("temp/linkedListFunction.bin"));

        TabulatedFunctionFactory inputF = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction func = readTabulatedFunction(bis, inputF);
        assertEquals("LinkedListTabulatedFunction size = 3\n[0.0; 0.0]\n[0.5; 0.25]\n[1.0; 1.0]", func.toString());

    }


    @Test
    public void testSerializeAndDeserialize() throws IOException, ClassNotFoundException
    {
        // проверка для ф-ции аррай
        File tempFile_arr = new File("temp/serialize.bin");

        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        ArrayTabulatedFunction function_arr = new ArrayTabulatedFunction(xValues, yValues);

        FunctionsIO.serialize(new BufferedOutputStream(new FileOutputStream(tempFile_arr)), function_arr);
        TabulatedFunction function_arr_des = FunctionsIO.deserialize(new BufferedInputStream(new FileInputStream(tempFile_arr)));

        for (int i = 0; i < function_arr_des.getCount(); i++)
        {
            assertEquals(xValues[i], function_arr_des.getX(i));
            assertEquals(yValues[i], function_arr_des.getY(i));
        }

        //--
        // проверка для ф-ции линкедлист

        File tempFile_link = new File("temp/serialize2.bin");
        LinkedListTabulatedFunction function_Link = new LinkedListTabulatedFunction(xValues, yValues);

        FunctionsIO.serialize(new BufferedOutputStream(new FileOutputStream(tempFile_link)), function_Link);
        TabulatedFunction function_Link_des = FunctionsIO.deserialize(new BufferedInputStream(new FileInputStream(tempFile_link)));

        for (int i = 0; i < function_Link_des.getCount(); i++)
        {
            assertEquals(xValues[i], function_Link_des.getX(i));
            assertEquals(yValues[i], function_Link_des.getY(i));
        }

    }

    @Test
    public void testSerializeXmlAndDeserializeXml() throws IOException
    {
        File tempFile3 = new File("temp/serialize.xml");

        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        // сериализация
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile3));
        FunctionsIO.serializeXml(writer, function);
        writer.close();

        // десериализация
        BufferedReader reader = new BufferedReader(new FileReader(tempFile3));
        TabulatedFunction function3 = FunctionsIO.deserializeXml(reader);

        for (int i = 0; i < function3.getCount(); i++)
        {
            assertEquals(xValues[i], function3.getX(i));
            assertEquals(yValues[i], function3.getY(i));
        }
    }

    @Test
    void testSerializeAndDeserializeJSON() throws IOException {
        File tempFile4 = new File("temp/serialize.json");

        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        // сериализация
        ArrayTabulatedFunction serFunction = new ArrayTabulatedFunction(xValues, yValues);
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile4));
        FunctionsIO.serializeJson(writer, serFunction);
        writer.close();

        // десериализация
        BufferedReader reader = new BufferedReader(new FileReader(tempFile4));
        TabulatedFunction deserFunction = FunctionsIO.deserializeJson(reader);

        for (int i = 0; i < deserFunction.getCount(); i++)
        {
            assertEquals(xValues[i], deserFunction.getX(i));
            assertEquals(yValues[i], deserFunction.getY(i));
        }
    }

    @AfterAll
    public static void cleanup(){
        File file = new File("temp/");
        String[] myFiles;
        myFiles = file.list();
        for (int i = 0; i < myFiles.length; i++) {
            File myFile = new File(file, myFiles[i]);
            myFile.delete();
        }
        System.out.println("delete all files in temp");
    }

}