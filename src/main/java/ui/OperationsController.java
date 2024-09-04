package ui;

import functions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import operations.TabulatedFunctionOperationService;
import java.io.*;
import static ui.Window.openFuncWindow;


public class OperationsController extends BaseFuncController {

    private TabulatedFunction func1 = null;
    private TabulatedFunction func2 = null;
    private TabulatedFunction func3 = null;
    @FXML
    TableView table1;
    @FXML
    TableView table2;
    @FXML
    TableView table3;

    //----

    @FXML
    protected void onCreateMathFunc1ButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func, table1);
        }
    }

    @FXML
    protected void onCreateMathFunc2ButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        if (checkFuncNotNull(func)) {
            func2 = func;
            fillTable(func, table2);
        }
    }

    @FXML
    protected void onCreateManualFunc1ButtonClick(){
        var func = openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func, table1);
        }
    }

    @FXML
    protected void onCreateManualFunc2ButtonClick(){
        var func = openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
        if (checkFuncNotNull(func)) {
            func2 = func;
            fillTable(func, table2);
        }
    }

    // -----операции

    private interface BiOperation
    {
        TabulatedFunction apply(TabulatedFunction f1, TabulatedFunction f2);
    }

    private void doOperation(BiOperation operation) {
        //если функции не заданы
        if (!checkFuncNotNull(func1)) {
            Window.showAlert("Вы не задали функцию 1");
            return;
        }
        if (!checkFuncNotNull(func2)) {
            Window.showAlert("Вы не задали функцию 2");
            return;
        }

        try {
            func3 = operation.apply(func1, func2);
            fillTable(func3, table3);
        } catch (Exception ex) {
            Window.showAlert(ex.getMessage());
        }
    }

    @FXML
    protected void onPlusButtonClick(){
        doOperation((f1, f2) -> new TabulatedFunctionOperationService(Settings.factory).add(f1, f2));
    }

    @FXML
    protected void onMinusButtonClick() {
        doOperation((f1, f2) -> new TabulatedFunctionOperationService(Settings.factory).subtract(f1, f2));
    }

    @FXML
    protected void onMultButtonClick(){
        doOperation((f1, f2) -> new TabulatedFunctionOperationService(Settings.factory).multiply(f1, f2));
    }

    @FXML
    protected void onDivButtonClick(){
        doOperation((f1, f2) -> new TabulatedFunctionOperationService(Settings.factory).division(f1, f2));
    }

    // -----чтение функций
    @FXML
    protected void onReadButton1Click() throws IOException, ClassNotFoundException {
        var func = SaveAndRead.read();
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func1, table1);
        }
    }

    @FXML
    protected void onReadButton2Click() throws IOException, ClassNotFoundException {
        var func = SaveAndRead.read();
        if (checkFuncNotNull(func)) {
            func2 = func;
            fillTable(func2, table2);
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
    @FXML
    protected void onSaveButton3Click() throws IOException {
        if (!checkFuncNotNull(func3)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        SaveAndRead.save(func3);
    }

    //---- редактирование

    public void colEdit(TableColumn.CellEditEvent<ui.Point, String> evt) {
        var tblView = evt.getTableView();
        СolEdit(evt, ((tblView == table1) ? func1 : func2));
    }

    //...
    public void onAddPointToFunc1ButtonClick(ActionEvent actionEvent) {
        AddPointToFunc(table1, func1);
    }

    public void onDelPointFromFunc1ButtonClick(ActionEvent actionEvent) {
        DelPointFromFunc(table1, func1);
    }

    public void onAddPointToFunc2ButtonClick(ActionEvent actionEvent) {
        AddPointToFunc(table2, func2);
    }

    public void onDelPointFromFunc2ButtonClick(ActionEvent actionEvent) {
        DelPointFromFunc(table2, func2);
    }
}