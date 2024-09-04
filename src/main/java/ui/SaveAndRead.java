package ui;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import io.FunctionsIO;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import static io.FunctionsIO.*;

public class SaveAndRead {

    //сериализация
    public static void save(TabulatedFunction func) throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select directory for save");
        fileChooser.setInitialFileName("func");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("bin", "*.bin"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        // для аррая
        if (Settings.factory.getClass() == ArrayTabulatedFunctionFactory.class) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON", "*.json"),
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
        }

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            var filetype = fileChooser.getSelectedExtensionFilter().getDescription();

            if (filetype == "JSON") {
                serializeJson(new BufferedWriter(new FileWriter(file)), (ArrayTabulatedFunction) func);
            }
            else if (filetype == "XML") {
                serializeXml(new BufferedWriter(new FileWriter(file)), (ArrayTabulatedFunction) func);
            }
            else if (filetype == "txt") {
                writeTabulatedFunction(new BufferedWriter(new FileWriter(file)), func);
            }
            else if (filetype == "bin") {
                serialize(new BufferedOutputStream(new FileOutputStream(file)), func);
            }
        }
    }

    //десериализация
    public static TabulatedFunction read() throws IOException, ClassNotFoundException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select directory for read");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("bin", "*.bin"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        // для аррая
        if (Settings.factory.getClass() == ArrayTabulatedFunctionFactory.class) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON", "*.json"),
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
        }

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            var filetype = fileChooser.getSelectedExtensionFilter().getDescription();

            if (filetype == "JSON")
                return deserializeJson(new BufferedReader(new FileReader(file)));
            if (filetype == "XML")
                return deserializeXml(new BufferedReader(new FileReader(file)));
            if (filetype == "txt")
                return readTabulatedFunction(new BufferedReader(new FileReader(file)), Settings.factory);
            if (file != null && filetype == "bin")
                return deserialize(new BufferedInputStream(new FileInputStream(file)));
        }
        return null;
    }
}
