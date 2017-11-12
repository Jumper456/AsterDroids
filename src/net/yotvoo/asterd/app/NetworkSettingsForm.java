package net.yotvoo.asterd.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.yotvoo.lib.network.ConnectionSettings;

public class NetworkSettingsForm {

    private Stage stage;
    private boolean save = false;
    private ConnectionSettings conSett;
    private TextField serverNameText;
    private TextField serverAddressText;
    private TextField serverPortText;
    private TextField userNameText;
    private PasswordField userPasswordField;
    private TextArea notesTextArea;



    public NetworkSettingsForm(ConnectionSettings conSett) {
        this.conSett = conSett;
        stage = new Stage();

        serverNameText = new TextField();
        serverNameText.setPromptText("Nazwa serwera");
        Label serverNameLabel = new Label("Nazwa serwera");

        serverAddressText = new TextField();
        serverAddressText.setPromptText("Adres servera");
        Label serverAddressLabel = new Label("Adres serwera (IP lub DNS)");

        serverPortText = new TextField();
        serverPortText.setPromptText("Nr portu");
        Label serverPortLabel = new Label("Numer portu serwera");

        userNameText = new TextField();
        userNameText.setPromptText("Nazwa użytkownika");
        Label userNameLabel = new Label("Nazwa użytkownika");

        userPasswordField = new PasswordField();
        userPasswordField.setPromptText("Hasło");
        Label userPasswordLabel = new Label("Hasło użytkownika");

        notesTextArea = new TextArea();
        notesTextArea.setPromptText("Uwagi");
        Label notesLabel = new Label("Uwagi");

        Button saveSettingsButton = new Button("Zapisz");
        saveSettingsButton.setOnAction(event -> {System.out.println("kliknięto Zapisz");
            save = true;
            stage.close();});

        Button cancelSettingsButton = new Button("Anuluj");
        cancelSettingsButton.setOnAction(event -> {System.out.println("kliknięto Anuluj");
            save = false;
            stage.close();});

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,10,20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(serverNameText, 0, 0);
        gridPane.add(serverNameLabel, 1, 0);
        gridPane.add(serverAddressText, 0, 1);
        gridPane.add(serverAddressLabel, 1, 1);
        gridPane.add(serverPortText, 0, 2);
        gridPane.add(serverPortLabel, 1, 2);
        gridPane.add(userNameText, 0, 3);
        gridPane.add(userNameLabel, 1, 3);
        gridPane.add(userPasswordField, 0, 4);
        gridPane.add(userPasswordLabel, 1, 4);
        gridPane.add(notesTextArea, 0, 5);
        gridPane.add(notesLabel, 1, 5);

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(10,10,0,10));
        flowPane.setHgap(10);
        flowPane.setVgap(0);
        gridPane.add(flowPane, 0,6);
        flowPane.getChildren().addAll(saveSettingsButton, cancelSettingsButton);


        if (conSett != null) {
            serverNameText.setText(conSett.getName());
            serverAddressText.setText(conSett.getAddress());
            serverPortText.setText(conSett.getPort());
            userNameText.setText(conSett.getUserName());
            userPasswordField.setText(conSett.getUserPassword());
            notesTextArea.setText(conSett.getNotes());
        }

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.setTitle("AsterDroids - Parametry połączenia");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

    }

    public ConnectionSettings showAndWait(){

        stage.showAndWait();

        System.out.println("Zakończone wyświetalnie helpStage");

        if (save) {
            if (conSett == null) {
                conSett = new ConnectionSettings();
                conSett.setId(1);
            };

            conSett.setName(serverNameText.getText());
            conSett.setAddress(serverAddressText.getText());
            conSett.setPort(serverPortText.getText());
            conSett.setUserName(userNameText.getText());
            conSett.setUserPassword(userPasswordField.getText());
            conSett.setNotes(notesTextArea.getText());
        };
        return conSett;
    }

}
