package com.enigma_machine.app;

import org.xml.sax.SAXException;

import com.enigma_machine.enigma.ComponentCache;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller
 */
public class EnigmaController {
    public ComponentCache cache = new ComponentCache();
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

    @FXML
    public void init() throws SAXException {
        cache.initialise();
        initReflectors();
        initRotors();
        initButtons();
    }

    @FXML
    public void initReflectors() {
        ObservableList<String> obsList = FXCollections.observableArrayList(cache.getReflectorNames());
        FXCollections.sort(obsList);
        reflector_choice.setItems(obsList);
        reflector_choice.getSelectionModel().selectFirst();
    }

    @FXML
    public void initRotors() {
        // TODO : Init choice boxes and spinners
    }

    @FXML
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
}