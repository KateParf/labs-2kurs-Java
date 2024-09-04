package ui;

import functions.TabulatedFunction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Window {
    public static void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);


        Image image = new Image((Window.class.getResource("img/germ.png").toExternalForm()));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
        alert.setGraphic(imageView);

        alert.showAndWait();
    }

    public static Stage createWindow(int width, int height, String title, String viewRecource) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource(viewRecource));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            // стили
            scene.getStylesheets().add(Window.class.getResource("style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.NONE);
            //stage.initOwner(primaryStage);

            return stage;
        }
        catch (Exception ex) {
            showAlert("Ошибка создания окна");
            System.out.println(ex);
        }
        return null;
    }

    // показываем не модальное окно
    public static void openWindow(int width, int height, String title, String viewRecource) {
        Stage wnd = createWindow(width, height, title, viewRecource);

        if (wnd != null) {
            wnd.initModality(Modality.APPLICATION_MODAL);
            wnd.show();
        }
        else showAlert("Ошибка открытия окна");
    }

    // открывает модаль окно создания ф-ции, получает и возвращает ф-ю и закрывает окно
    public static TabulatedFunction openFuncWindow(int width, int height, String title, String viewRecource) {
        var wnd = createWindow(width, height, title, viewRecource);
        if (wnd != null) {
            TabulatedFunction func = null;

            wnd.initModality(Modality.APPLICATION_MODAL);
            wnd.setUserData(func);
            wnd.showAndWait();

            // получаем созданную ф-ю из окошка
            func = (TabulatedFunction)wnd.getUserData();
            return func;
        }
        else showAlert("Ошибка открытия окна");
        return null;
    }

}
