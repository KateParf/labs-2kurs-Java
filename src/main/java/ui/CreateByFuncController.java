package ui;

import functions.*;
import functions.factory.TabulatedFunctionFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CreateByFuncController implements Initializable  {
    @FXML
    TextField pointsCnt;

    @FXML
    TextField Xstart;

    @FXML
    TextField Xend;

    @FXML
    ComboBox funcType;
    @FXML
    public Button createButton;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        var funcItems = FXCollections.observableArrayList();

        // рефлексей получаем список классов простых функций
        Reflections reflections = new Reflections("functions");
        Iterable<Class<? extends Object>> allClasses =
                reflections.getTypesAnnotatedWith(SimpleFunction.class);

        // заполняем список
        for (var x: allClasses) {
            Class cl = (Class) x;
            SimpleFunction annotation = (SimpleFunction)cl.getAnnotation(SimpleFunction.class);
            funcItems.add( new Item(annotation.name(), annotation.order(), cl) );
        }

        funcItems.sort((p1, p2) -> (int)Math.signum(((Item)p1).order - ((Item)p2).order));
        
        funcType.setItems(funcItems);
        funcType.getSelectionModel().select(0);
    }

    public class Item {
        public String name;

        public int order;

        public Class cls;

        public Item(String name, int order, Class cls) {
            this.name = name;
            this.order = order;
            this.cls = cls;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @FXML
    protected void onCreateButtonClick(ActionEvent event) {
        int cnt = 0;
        try {
            cnt = Integer.parseInt(pointsCnt.getText());
            if (cnt < 2) {
                //если ввели отрицательное число
                Window.showAlert("Ввели отрицательное количество значений. Необходимо ввести число >= 2");
                return;
            }
        } catch (NumberFormatException e) {
            //если ввели не число
            Window.showAlert("Ввели не число в количестве значений. Необходимо ввести число >= 2");
            return;
        }

        // парсим введенный интервал
        double XstartD = 0;
        double XendD = 0;
        try {
            XstartD = Double.parseDouble(Xstart.getText());
            XendD = Double.parseDouble(Xend.getText());
        } catch (NumberFormatException e) {
            //если ввели не число
            Window.showAlert("Ввели не число в значени X");
            return;
        }

        Stage stage = (Stage) createButton.getScene().getWindow();

        // создаем и запоминаем ф-ю
        var func = createFunc(cnt, XstartD, XendD);
        stage.setUserData(func);

        // закрываем модальное окошко
        stage.close();
    }


    private TabulatedFunction createFunc(int cnt, double Xstart, double Xend) {
        TabulatedFunctionFactory fact = Settings.factory;

        var itm = (Item) funcType.getSelectionModel().getSelectedItem();
        Class cls = itm.cls;
        String clsName = cls.getName();

        // todo создавать ф-ю по классу
        MathFunction source = null;
        switch (clsName) {
            case "functions.IdentityFunction":
                source = new IdentityFunction();
                break;
            case "functions.SqrFunction":
                source = new SqrFunction();
                break;
            case "functions.ConstantFunction":
                source = new ConstantFunction(Xstart);
                break;
            case "functions.UnitFunction":
                source = new UnitFunction();
                break;
            case "functions.ZeroFunction":
                source = new ZeroFunction();
                break;
            case "functions.AtanFunction":
                source = new AtanFunction();
                break;
        }

        TabulatedFunction func = fact.create(source, Xstart, Xend, cnt);

        return func;
    }

}