package ui;

import functions.TabulatedFunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import operations.TabulatedDifferentialOperator;
import java.io.IOException;
import static ui.Window.openFuncWindow;


public class DiffController extends BaseFuncController {

    @FXML
    TableView table1;
    @FXML
    TableView table2;
    private TabulatedFunction func1 = null;
    private TabulatedFunction func2 = null;


    @FXML
    protected void onCreateMathFuncButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func, table1);
        }
    }

    @FXML
    protected void onCreateManualFuncButtonClick(){
        var func = openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func, table1);
        }
    }

    @FXML
    protected void onReadButtonClick() throws IOException, ClassNotFoundException {
        var func = SaveAndRead.read();
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func1, table1);
        }
    }

    // -----сохранение функций

    @FXML
    protected void onSaveButton1Click() throws IOException {
        if (!checkFuncNotNull(func1)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        SaveAndRead.save(func1);
    }
    @FXML
    protected void onSaveButton2Click() throws IOException {
        if (!checkFuncNotNull(func2)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        SaveAndRead.save(func2);
    }


    //---- редактирование

    public void colEdit(TableColumn.CellEditEvent<ui.Point, String> evt) {
        СolEdit(evt, func1);
    }

    public void onAddPointToFuncButtonClick(ActionEvent actionEvent) {
        AddPointToFunc(table1, func1);
    }

    public void onDelPointFromFuncButtonClick(ActionEvent actionEvent) {
        DelPointFromFunc(table1, func1);
    }


    @FXML
    protected void onDiffButtonClick()
    {
        // если функция не задана
        if (!checkFuncNotNull(func1))
        {
            Window.showAlert("Вы не задали исходную функцию");
            return;
        }

        try
        {
            func2 = new TabulatedDifferentialOperator(Settings.factory).derive(func1);
            fillTable(func2, table2);
        }
        catch (Exception ex)
        {
            Window.showAlert(ex.getMessage());
        }
    }
}