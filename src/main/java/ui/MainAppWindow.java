package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainAppWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource("MainAppView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 330);
        // стили
        scene.getStylesheets().add(Window.class.getResource("style.css").toExternalForm());
        stage.setTitle("Математические функции");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}