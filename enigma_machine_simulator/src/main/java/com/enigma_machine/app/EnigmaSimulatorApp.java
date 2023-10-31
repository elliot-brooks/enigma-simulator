package com.enigma_machine.app;

import java.io.IOException;
import java.net.URL;

import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EnigmaSimulatorApp extends Application {

    @Override
    public void start(Stage stage) throws IOException, SAXException {

        String dir = System.getProperty("user.dir");
        FXMLLoader loader = new FXMLLoader(new URL("file:" + dir + "/src/main/resources/GUI.fxml"));
        EnigmaController enigmaController = new EnigmaController();

        loader.setController(enigmaController);
        AnchorPane root = loader.load();
        enigmaController.init();
        Scene scene = new Scene(root, 640, 640);
        stage.setScene(scene);
        stage.show();
    }

    public static void launchApp() {
        launch();
    }
}
