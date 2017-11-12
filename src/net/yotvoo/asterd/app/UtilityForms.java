package net.yotvoo.asterd.app;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.yotvoo.lib.network.ConnectionSettings;

public class UtilityForms {

    private static void showAndWaitModalStage(Parent root, String title){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    };

    public  static void showConnectionDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Połączenie");
        alert.setHeaderText("Popraw i zapisz parametry połączenia sieciowego");
        alert.setContentText("Tu będą parametry :)");
        alert.show();
        //Platform.runLater(alert::showAndWait);
    }


    public static void showHelpStage() {
            TextArea textArea = new TextArea();
            textArea.setPrefWidth(800);
            textArea.setPrefHeight(600);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setText("Instrukcja obsługi programu AsterDroids\n\n");
            textArea.appendText("Jest to prosta gra wzorowana na klasycznej grze Asteroids. ");
            textArea.appendText("Latasz stateczkiem, strzelasz do asteroidów, za każde trafienie dostajesz punkt. ");
            textArea.appendText("Trafione asteroidy rozpadają się na mniejsze. Zderzenie z asteroidą to koniec gry. ");
            textArea.appendText("Celem gry jest zdobycie jak największej ilości punktów (trafień).\n\n");
            textArea.appendText("Aby wystartować grę naciśnij klawisz F5 na klawiaturze.\n\n");
            textArea.appendText("Aby zakończyć grę naciśnij klawisz ESC na klawiaturze.\n\n");
            textArea.appendText("Sterowanie statkiem odbywa się klawiszami strzałek na klawiaturze.\n");
            textArea.appendText("Strzałki w lewo i prawo obracają statek a strzałka w górę przyspiesza.\n");
            textArea.appendText("Statek da się zahamować tylko odwracając się tyłem do kierunku lotu i przyspieszając.\n\n");
            textArea.appendText("Strzelanie - SPACJA\n\n");

            GridPane gridPane = new GridPane();
            gridPane.add(textArea, 0, 0);
            showAndWaitModalStage(gridPane,"AsterDroids - Pomoc");
    }
}
