package com.enigma_machine.app;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EnigmaSimulatorApp extends Application {

    @Override
    public void start(Stage stage) throws IOException, SAXException {
        stage.setResizable(false);
        String dir = System.getProperty("user.dir");
        File fxmlFile = new File(dir + "/src/main/resources/GUI.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
        EnigmaController enigmaController = new EnigmaController();
        loader.setController(enigmaController);
        AnchorPane root = loader.load();
        enigmaController.init();
        Scene scene = new Scene(root, 820, 850);
        stage.setScene(scene);
        stage.setTitle("E N I G M A  S I M U L A T O R");
        stage.show();
    }

    public static void launchApp() {
        launch();
    }
}
