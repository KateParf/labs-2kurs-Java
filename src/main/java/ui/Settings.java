package ui;


import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class Settings {

    // по умолчанию фабрика массивов но через настройки можно заменить
    public static TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();

}