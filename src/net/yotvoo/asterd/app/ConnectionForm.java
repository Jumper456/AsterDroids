package net.yotvoo.asterd.app;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnectionForm {

    public  static void showConnectionDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Połączenie");
        alert.setHeaderText("Popraw i zapisz parametry połączenia sieciowego");
        alert.setContentText("Tu będą parametry :)");
        alert.showAndWait();
        //Platform.runLater(alert::show);
    }

    public static void showConnectionStage(){
        Stage stage = new Stage();
        stage.setTitle("Connection");
        stage.showAndWait();
    }
}
