package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static ui.Window.openWindow;

public class MainAppController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onOperationsButtonClick() {
        openWindow(550, 500, "Операции над 2-мя функциями", "OperationsView.fxml");
    }
    @FXML
    protected void onDiffButtonClick() {
        openWindow(590, 500, "Дифференцирование функции", "DiffView.fxml");
    }
    @FXML
    protected void onIntegralButtonClick() {
        openWindow(550, 500, "Интеграл", "IntegralView.fxml");
    }
    @FXML
    protected void onSettingsButtonClick() {
        openWindow(320, 200, "Настройки", "SettingsView.fxml");
    }

}