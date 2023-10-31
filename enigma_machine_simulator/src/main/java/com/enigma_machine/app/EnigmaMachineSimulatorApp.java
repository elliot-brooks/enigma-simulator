package com.enigma_machine.app;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EnigmaMachineSimulatorApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String dir = System.getProperty("user.dir");
        AnchorPane root = FXMLLoader.load(new URL("file:" + dir + "/src/main/resources/GUI.fxml"));
        Scene scene = new Scene(root, 640, 640);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
