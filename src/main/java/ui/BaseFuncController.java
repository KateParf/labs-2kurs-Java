package ui;

import functions.Insertable;
import functions.Point;
import functions.Removable;
import functions.TabulatedFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Optional;
import static operations.TabulatedFunctionOperationService.asPoints;


public class BaseFuncController {

    //----
    protected void fillTable(TabulatedFunction func, TableView table){
            ObservableList<ui.Point> points = FXCollections.observableArrayList();
            Point[] funcPoints = asPoints(func);
            int cnt = funcPoints.length;
            for (int i = 0; i < cnt; i++) {
                points.add(new ui.Point(funcPoints[i].x, funcPoints[i].y));
            }
            table.setItems(points);
    }

    protected boolean checkFuncNotNull(TabulatedFunction fu){
        return (fu != null);
    }

    //-- редактирование

    protected void СolEdit(TableColumn.CellEditEvent<ui.Point, String> evt, TabulatedFunction func) {
        String val = evt.getNewValue();
        var tblView = evt.getTableView();
        var idx = evt.getTablePosition().getRow();
        ui.Point point = (ui.Point) tblView.getItems().get(idx);

        boolean res = point.setYstr(val);
        if (! res)
            tblView.refresh();
        else
            func.setY(idx, point.getY());
        tblView.requestFocus();
    }

    protected void AddPointToFunc(TableView table, TabulatedFunction func) {
        if (!checkFuncNotNull(func)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var tblView = table;

        // получить тек строку
        table.requestFocus();
        var idx = table.getFocusModel().getFocusedCell().getRow();

        // Хнов = Хтек + Хтек+1 / 2  или просто Хтек +1 если послед
        double Xnew = 0;

        //--- показать стд окошо ввода 1 числа, заполнить по умол
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Добавить новое значение X");
        dialog.setHeaderText("Добавьте новое значение X. \nЗначение Y отредактируйте в таблице самостоятельно.");
        dialog.setContentText("Введите X:");
        Image image = new Image((Window.class.getResource("img/manual.png").toExternalForm()));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
        dialog.setGraphic(imageView);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try {
                Xnew = Double.parseDouble(result.get());
            }
            catch (Exception ex){
                Window.showAlert("Ввели не число");
                return;
            }
        }
        else return;
        // Унов = упред
        double Ynew = func.getY(idx);

        // добавляем в табл
        ((Insertable)func).insert(Xnew, Ynew);

        //// добавить в итемсы после тек
        tblView.getItems().add(idx+1, new Point(Xnew, Ynew));
        // пересчет итемсы (врем)
        fillTable(func, table);

        table.getSelectionModel().select(idx+1);
        table.getFocusModel().focus(idx+1);
    }

    public void DelPointFromFunc(TableView table, TabulatedFunction func) {
        if (!checkFuncNotNull(func)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        // не удалять когда 2
        if (func.getCount() <= 2) {
            Window.showAlert("Значений в таблице не должно быть меньше 2х");
            return;
        }

        var tblView = table;
        // получить тек строку
        table.requestFocus();
        var idx = table.getFocusModel().getFocusedCell().getRow();

        // удалить из итемсов
        tblView.getItems().remove(idx);
        // удалить из табл
        ((Removable)func).remove(idx);
    }

}