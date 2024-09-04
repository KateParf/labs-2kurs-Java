package ui;


import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;


public class CreateManualController {

    ObservableList<Point> points;

    @FXML
    TextField pointsCnt;

    @FXML
    TableView table;

    @FXML
    public Button createButton;

    @FXML
    protected void onApplyCntButtonClick(){
       int cnt = 0;
       try {
            cnt = Integer.parseInt(pointsCnt.getText());
            if (cnt < 2) {
                //если ввели отрицательное число
                Window.showAlert("Ввели отрицательное число. Необходимо ввести число >= 2");
                return;
            }
       }
       catch(NumberFormatException e){
           //если ввели не число
           Window.showAlert("Ввели не число. Необходимо ввести число >= 2");
           return;
       }

       var points = table.getItems();
       if (points ==  null)
           points = FXCollections.observableArrayList();
       var oldSize = points.size();

       // удаляем с конца если стало меньше
       if ( cnt < oldSize)
           points.remove(cnt, oldSize);

       // заполняем конец дефолтом если стало больше
       for (int i = oldSize; i < cnt; i++)
           points.add(new Point(i,i));

       table.setItems(points);

       // восстанавливаем старыми значениями
    }

    @FXML
    protected void onCreateButtonClick(){
        Stage stage = (Stage) createButton.getScene().getWindow();

        // создаем и запоминаем ф-ю
        var func = createFunc();
        stage.setUserData(func);

        // закрываем модальное окошко
        stage.close();
    }

    private TabulatedFunction createFunc() {
        var points = table.getItems();
        // sort x
        points.sort((p1, p2) -> (int)Math.signum(((Point)p1).getX() - ((Point)p2).getX()) );

        double[] arrX = points.stream().mapToDouble(p -> ((Point)p).getX()).toArray();
        double[] arrY = points.stream().mapToDouble(p -> ((Point)p).getY()).toArray();

        TabulatedFunctionFactory fact = Settings.factory;
        TabulatedFunction func = fact.create(arrX, arrY);
        return func;
    }

    public void colEdit(TableColumn.CellEditEvent<Point, String> evt) {
        String val = evt.getNewValue();
        var tblView = evt.getTableView();
        Point point = (Point) tblView.getItems().get(
                evt.getTablePosition().getRow());
        boolean res;
        if (evt.getTablePosition().getColumn() == 0)
            res = point.setXstr(val);
        else
            res = point.setYstr(val);
        if (! res)
            tblView.refresh();
        tblView.requestFocus();
    }

}