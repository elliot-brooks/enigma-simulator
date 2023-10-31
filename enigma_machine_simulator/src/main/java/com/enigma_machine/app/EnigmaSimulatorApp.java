package com.enigma_machine.app;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EnigmaSimulatorApp extends Application {
    @FXML
    public Button clear_input_btn;
    @FXML
    public Button submit_input_btn;
    @FXML
    public Button clear_message_btn;
    @FXML
    public ChoiceBox<String> left_rotor_choice;
    @FXML
    public Spinner<Integer> left_rotor_rotation;
    @FXML
    public Spinner<Integer> left_rotor_ring;
    @FXML
    public ChoiceBox<String> middle_rotor_choice;
    @FXML
    public Spinner<Integer> middle_rotor_rotation;
    @FXML
    public Spinner<Integer> middle_rotor_ring;
    @FXML
    public ChoiceBox<String> right_rotor_choice;
    @FXML
    public Spinner<Integer> right_rotor_rotation;
    @FXML
    public Spinner<Integer> right_rotor_ring;
    @FXML
    public ChoiceBox<String> reflector_choice;
    @FXML
    public TextField plugboard_config;
    @FXML
    public TextField input_text;
    @FXML
    public TextArea message_text;
    @FXML
    public TextArea log_text_area;
    @FXML
    public CheckBox log_toggle_box;

    @Override
    public void start(Stage stage) throws IOException {
        String dir = System.getProperty("user.dir");
        AnchorPane root = FXMLLoader.load(new URL("file:" + dir + "/src/main/resources/GUI.fxml"));
        Scene scene = new Scene(root, 640, 640);
        this.init();
        stage.setScene(scene);
        stage.show();
    }

    public void init() {

    }

    public void initReflectors() {
        // TODO : Init reflector choice box here
    }

    public void initRotors() {
        // TODO : Init choice boxes and spinners
    }

    public void initButtons() {
        // TODO : Init all buttons
    }

    public void submitInputText() {
        // TODO : Event when clicking submit
    }

    public void clearInputText() {
        // TODO : Event when clicking clear
    }

    public void clearMessageText() {
        // TODO : Event when clicking clear
    }

    public static void launchApp() {
        launch();
    }
}
