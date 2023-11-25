package com.enigma_machine.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.xml.sax.SAXException;

import com.enigma_machine.enigma.ComponentCache;
import com.enigma_machine.enigma.Enigma;
import com.enigma_machine.enigma.Plugboard;
import com.enigma_machine.enigma.Reflector;
import com.enigma_machine.enigma.Rotor;
import com.enigma_machine.logger.EnigmaLogger;
import com.enigma_machine.tools.Constants;
import com.enigma_machine.tools.Tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;

public class EnigmaController {
    public ComponentCache cache = new ComponentCache();
    @FXML
    public Button clear_input_btn;
    @FXML
    public Button submit_text_btn;
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
    public TitledPane log_title_pane;
    @FXML
    public Canvas visualisation_canvas;
    @FXML
    public Button next_visualisation_button;
    @FXML
    public Button previous_visualisation_button;
    @FXML
    public Label encryption_step_label;
    @FXML
    public Label current_rotation_label;
    @FXML
    public CheckBox verbose_logging_toggle;
    public Enigma enigmaModel;
    public EnigmaVisualiser visualiser;
    public int visualiserIndex = 0;
    

    @FXML
    public void init() throws SAXException {
        cache.initialise();
        initReflectors();
        initRotors();
        initButtons();
        visualiser = new EnigmaVisualiser(visualisation_canvas);
    }

    public void incrementIndex() {
        if (visualiserIndex < EnigmaLogger.getPlaintext().length() - 1) {
            visualiserIndex++;
        }
    }

    public void decrementIndex() {
        if (visualiserIndex > 0) {
            visualiserIndex--;
        }
    }

    @FXML
    public void initReflectors() {
        ObservableList<String> obsList = FXCollections.observableArrayList(cache.getReflectorNames());
        reflector_choice.setItems(obsList);
        reflector_choice.getSelectionModel().selectFirst();
    }

    @FXML
    public void initRotors() {
        ObservableList<String> obsList = FXCollections.observableArrayList(cache.getRotorNames());
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
        submit_text_btn.setOnAction(ActionEvent -> {
            submitInputText();
        });

        clear_input_btn.setOnAction(ActionEvent -> {
            clearInputText();
        });

        clear_message_btn.setOnAction(ActionEvent -> {
            clearMessageText();

        });

        next_visualisation_button.setOnAction(ActionEvent -> {
            incrementIndex();
            if (EnigmaLogger.hasLogged()) {
                updateVisualiser();
            }
        });

        previous_visualisation_button.setOnAction(ActionEvent -> {
            decrementIndex();
            if (EnigmaLogger.hasLogged()) {
                updateVisualiser();
            }
        });

    }

    private void updateVisualiser() {
        boolean verbose = verbose_logging_toggle.isSelected();
        visualiser.clearVisualisation();
        if (!(EnigmaLogger.getEncryptionStep(visualiserIndex).length() == 6)) {
            if (verbose) {
                visualiser.drawWiringDiagram(visualiserIndex, enigmaModel.getAllPossiblePaths(EnigmaLogger.getRotation(visualiserIndex)), enigmaModel.getReflector().getWiring());
            }
            else {
                visualiser.drawWiringDiagram(visualiserIndex, null, enigmaModel.getReflector().getWiring());
            }
        }
        encryption_step_label.setText(EnigmaLogger.getEncryptionStep(visualiserIndex));
        current_rotation_label.setText(EnigmaLogger.getRotationString(visualiserIndex));
    }

    private void updateModel() {
        // Build Rotors
        List<Rotor> rotors = new ArrayList<>();
        Rotor leftRotor = cache.getRotor(left_rotor_choice.getValue());
        leftRotor.setRingSetting(Tools.minusOneInteger(left_rotor_ring.getValue()));
        leftRotor.setRotationPosition(Tools.convertCharToIndex(left_rotor_rotation.getValue()));

        Rotor middleRotor = cache.getRotor(middle_rotor_choice.getValue());
        middleRotor.setRingSetting(Tools.minusOneInteger(middle_rotor_ring.getValue()));
        middleRotor.setRotationPosition(Tools.convertCharToIndex(middle_rotor_rotation.getValue()));

        Rotor rightRotor = cache.getRotor(right_rotor_choice.getValue());
        rightRotor.setRingSetting(Tools.minusOneInteger(right_rotor_ring.getValue()));
        rightRotor.setRotationPosition(Tools.convertCharToIndex(right_rotor_rotation.getValue()));
        
        rotors.add(rightRotor);
        rotors.add(middleRotor);
        rotors.add(leftRotor);
        // Build Reflector
        Reflector reflector = cache.getReflector(reflector_choice.getValue());
        // Build Plugobard
        Plugboard plugboard = new Plugboard();
        List<String> plugboardPairings = new ArrayList<>();
        for (String string : plugboard_config.getText().split(" ")) {
            plugboardPairings.add(string);
        }
        // Build enigma
        enigmaModel = Enigma.createCustomEnigma(rotors, plugboard, reflector);
        enigmaModel.addCables(plugboardPairings);
    }

    public void submitInputText() {
        if (input_text.getText().isEmpty()) {
            clearLogging();
            return;
        }
        boolean logging = log_toggle_box.isSelected();
        updateModel();
        String cypherText = enigmaModel.encrypt(input_text.getText(), logging);
        visualiserIndex = 0;
        message_text.setText(cypherText);
        if (logging) {
            updateVisualiser();
            log_text_area.setText(EnigmaLogger.getLog());
        } else {
            clearLogging();
        }
        
    }

    public void clearLogging() {
        visualiser.clearVisualisation();
        EnigmaLogger.resetLogger();
        log_text_area.setText("");
        encryption_step_label.setText("");
        current_rotation_label.setText("");
    }

    public void clearInputText() {
        input_text.clear();
        clearLogging();
    }

    public void clearMessageText() {
        message_text.clear();
        clearLogging();
    }
}