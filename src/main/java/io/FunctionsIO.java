package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import functions.*;
import functions.factory.*;

import java.io.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class FunctionsIO
{
    private FunctionsIO()
    {
        throw new UnsupportedOperationException("Этот класс не может быть создан");
    }

    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream sout = new DataOutputStream(outputStream);
        // записать число значений таблицы count
        sout.writeInt(function.getCount());
        // записать в поток все x и y
        for (Point point : function) {
            sout.writeDouble(point.x);
            sout.writeDouble(point.y);
        }
        sout.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream sin = new DataInputStream(inputStream);
        // считать число значений таблицы count
        int cnt = sin.readInt();
        // записать все x и y
        double[] xValues = new double[cnt];
        double[] yValues = new double[cnt];
        for (int i = 0; i < cnt; i++){
            xValues[i] = sin.readDouble();
            yValues[i] = sin.readDouble();
        }
        return factory.create(xValues, yValues);
    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) throws IOException
    {
        PrintWriter printWriter = new PrintWriter(writer);
        int pointCount = function.getCount();
        printWriter.println(pointCount);
        for (Point point : function)
        {
            printWriter.printf("%f %f\n", point.x, point.y);
        }
        printWriter.flush(); // пробрасываем данные из буфера
    }

    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException
    {
        try
        {
            int count = Integer.parseInt(reader.readLine());
            double[] xValues = new double[count];
            double[] yValues = new double[count];

            // получаем форматтер для чисел с плавающей точкой с учётом русской локализации
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.forLanguageTag("ru"));

            for (int i = 0; i < count; i++)
            {
                String line = reader.readLine();
                String[] values = line.split(" ");
                xValues[i] = numberFormat.parse(values[0]).doubleValue();
                yValues[i] = numberFormat.parse(values[1]).doubleValue();
            }
            return factory.create(xValues, yValues);
        }
        catch (ParseException e)
        {
            throw new IOException(e);
        }
    }

    public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException
    {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeObject(function);
        objectOutputStream.flush();
    }

    public static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException
    {
        ObjectInputStream objectStream = new ObjectInputStream(stream);
        return (TabulatedFunction) objectStream.readObject();
    }

    public static void serializeXml(BufferedWriter writer, ArrayTabulatedFunction function) throws IOException
    {
        XStream xStream = new XStream();
        String xml = xStream.toXML(function);
        writer.write(xml);
        writer.flush();
    }

    public static ArrayTabulatedFunction deserializeXml(BufferedReader reader)
    {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY); // добавляем разрешение на любой тип
        return (ArrayTabulatedFunction) xStream.fromXML(reader);
    }

    public static void serializeJson(BufferedWriter writer, ArrayTabulatedFunction function)  throws IOException
    {
        ObjectMapper objMap = new ObjectMapper();
        writer.write(objMap.writeValueAsString(function));
        writer.flush();
    }

    public static ArrayTabulatedFunction deserializeJson(BufferedReader reader) throws IOException {
        ObjectMapper objMap = new ObjectMapper();
        ArrayTabulatedFunction res = objMap.readerFor(ArrayTabulatedFunction.class).readValue(reader);
        return res;
    }
}