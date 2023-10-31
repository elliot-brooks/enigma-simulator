package com.enigma_machine.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.xml.sax.SAXException;

import com.enigma_machine.enigma.ComponentCache;
import com.enigma_machine.tools.Constants;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;

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
    public Spinner<Character> left_rotor_rotation;
    @FXML
    public Spinner<Integer> left_rotor_ring;
    @FXML
    public ChoiceBox<String> middle_rotor_choice;
    @FXML
    public Spinner<Character> middle_rotor_rotation;
    @FXML
    public Spinner<Integer> middle_rotor_ring;
    @FXML
    public ChoiceBox<String> right_rotor_choice;
    @FXML
    public Spinner<Character> right_rotor_rotation;
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
        ObservableList<String> obsList = FXCollections.observableArrayList(cache.getRotorNames());
        FXCollections.sort(obsList);
        // Set items
        left_rotor_choice.setItems(obsList);
        middle_rotor_choice.setItems(obsList);
        right_rotor_choice.setItems(obsList);
        // Pick first for each
        left_rotor_choice.getSelectionModel().selectFirst();
        middle_rotor_choice.getSelectionModel().select(1);
        right_rotor_choice.getSelectionModel().select(2);
        // Set up rotation spinners
        List<Character> alphabet = Constants.ALPHABET.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());
        ObservableList<Character> obsListAlphabet = FXCollections.observableArrayList(alphabet);
        ListSpinnerValueFactory<Character> left_rotor_values = new ListSpinnerValueFactory<>(obsListAlphabet);
        ListSpinnerValueFactory<Character> middle_rotor_values = new ListSpinnerValueFactory<>(obsListAlphabet);
        ListSpinnerValueFactory<Character> right_rotor_values = new ListSpinnerValueFactory<>(obsListAlphabet);

        left_rotor_rotation.setValueFactory(left_rotor_values);
        middle_rotor_rotation.setValueFactory(middle_rotor_values);
        right_rotor_rotation.setValueFactory(right_rotor_values);
        // Set up ring setting spinners
        IntegerSpinnerValueFactory left_rotor_ring_values = new IntegerSpinnerValueFactory(1, 26);
        IntegerSpinnerValueFactory middle_rotor_ring_values = new IntegerSpinnerValueFactory(1, 26);
        IntegerSpinnerValueFactory right_rotor_ring_values = new IntegerSpinnerValueFactory(1, 26);

        left_rotor_ring.setValueFactory(left_rotor_ring_values);
        middle_rotor_ring.setValueFactory(middle_rotor_ring_values);
        right_rotor_ring.setValueFactory(right_rotor_ring_values);

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