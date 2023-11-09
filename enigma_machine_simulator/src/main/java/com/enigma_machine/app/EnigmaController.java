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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;
import javafx.scene.paint.Color;

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
    public CheckBox visualisation_check_box;
    @FXML
    public Canvas visualisation_canvas;
    public Enigma enigmaModel;
    

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
    }

    @FXML
    private void drawVisualisation(boolean clear) {
        final int DOT_SIZE = 5;
        final int Y_OFFSET = 8;        
        final int BOX_WIDTH = 50;
        final int PLUGBOARD_BOX_X = 700;
        final int RIGHT_ROTOR_X = 500;
        final int MIDDLE_ROTOR_X = 375;
        final int LEFT_ROTOR_X = 250;
        final int REFLECTOR_X = 50;
        
        GraphicsContext gc = visualisation_canvas.getGraphicsContext2D();
        // 800
        double width = gc.getCanvas().getWidth();
        // 200
        double height = gc.getCanvas().getHeight();
        if (clear) {
            gc.clearRect(0, 0, width, height);
            return;
        }
        // Draw Rectangles
        gc.setFill(Color.rgb(204, 204, 255));
        gc.fillRect(PLUGBOARD_BOX_X, Y_OFFSET, BOX_WIDTH, 182);
        gc.fillRect(RIGHT_ROTOR_X, Y_OFFSET, BOX_WIDTH, 182);
        gc.fillRect(MIDDLE_ROTOR_X, Y_OFFSET, BOX_WIDTH, 182);
        gc.fillRect(LEFT_ROTOR_X, Y_OFFSET, BOX_WIDTH, 182);
        gc.fillRect(REFLECTOR_X, Y_OFFSET, BOX_WIDTH, 182);
        gc.setFill(Color.BLACK);
        for (int i = 0; i < 26; i++) {
            // PLUGBOARD
            gc.fillOval(PLUGBOARD_BOX_X - DOT_SIZE/2, Y_OFFSET + (i * 7) + DOT_SIZE/2, DOT_SIZE, DOT_SIZE);
            gc.fillOval(PLUGBOARD_BOX_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (i * 7) + DOT_SIZE/2, DOT_SIZE, DOT_SIZE);
            // ROTORS
            gc.fillOval(RIGHT_ROTOR_X - DOT_SIZE/2, Y_OFFSET + (i * 7) + DOT_SIZE/2, DOT_SIZE, DOT_SIZE);
            gc.fillOval(RIGHT_ROTOR_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (i * 7) + DOT_SIZE/2, DOT_SIZE, DOT_SIZE);
            gc.fillOval(MIDDLE_ROTOR_X - DOT_SIZE/2, Y_OFFSET + (i * 7) + DOT_SIZE/2, DOT_SIZE, DOT_SIZE);
            gc.fillOval(MIDDLE_ROTOR_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (i * 7) + DOT_SIZE/2, DOT_SIZE, DOT_SIZE);
            gc.fillOval(LEFT_ROTOR_X - DOT_SIZE/2, Y_OFFSET + (i * 7) + DOT_SIZE/2, DOT_SIZE, DOT_SIZE);
            gc.fillOval(LEFT_ROTOR_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (i * 7) + DOT_SIZE/2, DOT_SIZE, DOT_SIZE);

            // REFLECTOR
            gc.fillOval(REFLECTOR_X + BOX_WIDTH - DOT_SIZE/2, Y_OFFSET + (i * 7) + DOT_SIZE/2, DOT_SIZE, DOT_SIZE);
        }
        
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
        boolean logging = log_toggle_box.isSelected();
        updateModel();
        String cypherText = enigmaModel.encrypt(input_text.getText(), logging);
        message_text.setText(cypherText);
        log_text_area.setText(EnigmaLogger.getLog());
        drawVisualisation(!visualisation_check_box.isSelected());

    }

    public void clearInputText() {
        input_text.clear();
    }

    public void clearMessageText() {
        message_text.clear();
    }
}