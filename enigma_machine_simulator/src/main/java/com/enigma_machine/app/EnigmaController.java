package com.enigma_machine.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.xml.sax.SAXException;

import com.enigma_machine.enigma.EnigmaPlus;
import com.enigma_machine.enigma.EnigmaPlusLogger;
import com.enigma_machine.enigma.Enigma;
import com.enigma_machine.enigma.EnigmaLogger;
import com.enigma_machine.enigma.Plugboard;
import com.enigma_machine.enigma.Reflector;
import com.enigma_machine.enigma.Rotor;
import com.enigma_machine.enigma.tools.Constants;
import com.enigma_machine.enigma.tools.Tools;
import com.enigma_machine.parsers.ComponentCache;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    @FXML
    public CheckBox step_check_box;
    @FXML
    public Slider speed_slider;
    @FXML
    public TabPane log_tab_pane;
    @FXML
    public TabPane enigma_tab_pane;
    @FXML
    public ChoiceBox<String> enhanced_left_rotor_choice;
    @FXML
    public Spinner<Character> enhanced_left_rotor_rotation;
    @FXML
    public Spinner<Integer> enhanced_left_rotor_ring;
    @FXML
    public ChoiceBox<String> enhanced_middle_rotor_choice;
    @FXML
    public Spinner<Character> enhanced_middle_rotor_rotation;
    @FXML
    public Spinner<Integer> enhanced_middle_rotor_ring;
    @FXML
    public ChoiceBox<String> enhanced_right_rotor_choice;
    @FXML
    public Spinner<Character> enhanced_right_rotor_rotation;
    @FXML
    public Spinner<Integer> enhanced_right_rotor_ring;
    @FXML
    public RadioButton encode_radio_button;
    @FXML
    public RadioButton decode_radio_button;
    @FXML
    public Tab classic_enigma_tab;
    @FXML
    public Tab enhanced_enigma_tab;
    @FXML
    public Label current_permutation_label;

    public Enigma enigmaModel;
    public EnigmaPlus enhancedEnigmaModel;
    public boolean threadInterrupt = false;
    public EnigmaVisualiser visualiser;
    public EnigmaPlusVisualiser enhancedVisualiser;
    public int visualiserIndex = 0;

    @FXML
    public void init() throws SAXException {
        cache.initialise();
        initReflectors();
        initRotors();
        initButtons();
        current_permutation_label.setText("~7.91e+12");
        visualiser = new EnigmaVisualiser(visualisation_canvas);
        enhancedVisualiser = new EnigmaPlusVisualiser(visualisation_canvas);
    }

    public void incrementIndex() {
        if (visualiserIndex < EnigmaLogger.getPlaintext().length() - 1 || visualiserIndex < EnigmaPlusLogger.getPlaintext().length() - 1) {
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
        enhanced_left_rotor_choice.setItems(obsList);
        enhanced_middle_rotor_choice.setItems(obsList);
        enhanced_right_rotor_choice.setItems(obsList);
        // Pick first for each
        left_rotor_choice.getSelectionModel().selectFirst();
        middle_rotor_choice.getSelectionModel().select(1);
        right_rotor_choice.getSelectionModel().select(2);
        enhanced_left_rotor_choice.getSelectionModel().selectFirst();
        enhanced_middle_rotor_choice.getSelectionModel().select(1);
        enhanced_right_rotor_choice.getSelectionModel().select(2);
        // Set up rotation spinners
        List<Character> alphabet = Constants.ALPHABET.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());
        ObservableList<Character> obsListAlphabet = FXCollections.observableArrayList(alphabet);
        ListSpinnerValueFactory<Character> left_rotor_values = new ListSpinnerValueFactory<>(obsListAlphabet);
        ListSpinnerValueFactory<Character> middle_rotor_values = new ListSpinnerValueFactory<>(obsListAlphabet);
        ListSpinnerValueFactory<Character> right_rotor_values = new ListSpinnerValueFactory<>(obsListAlphabet);
        ListSpinnerValueFactory<Character> enhanced_left_rotor_values = new ListSpinnerValueFactory<>(obsListAlphabet);
        ListSpinnerValueFactory<Character> enhanced_middle_rotor_values = new ListSpinnerValueFactory<>(obsListAlphabet);
        ListSpinnerValueFactory<Character> enhanced_right_rotor_values = new ListSpinnerValueFactory<>(obsListAlphabet);

        left_rotor_rotation.setValueFactory(left_rotor_values);
        middle_rotor_rotation.setValueFactory(middle_rotor_values);
        right_rotor_rotation.setValueFactory(right_rotor_values);

        enhanced_left_rotor_rotation.setValueFactory(enhanced_left_rotor_values);
        enhanced_middle_rotor_rotation.setValueFactory(enhanced_middle_rotor_values);
        enhanced_right_rotor_rotation.setValueFactory(enhanced_right_rotor_values);
        // Set up ring setting spinners
        IntegerSpinnerValueFactory left_rotor_ring_values = new IntegerSpinnerValueFactory(1, 26);
        IntegerSpinnerValueFactory middle_rotor_ring_values = new IntegerSpinnerValueFactory(1, 26);
        IntegerSpinnerValueFactory right_rotor_ring_values = new IntegerSpinnerValueFactory(1, 26);
        IntegerSpinnerValueFactory enhanced_left_rotor_ring_values = new IntegerSpinnerValueFactory(1, 26);
        IntegerSpinnerValueFactory enhanced_middle_rotor_ring_values = new IntegerSpinnerValueFactory(1, 26);
        IntegerSpinnerValueFactory enhanced_right_rotor_ring_values = new IntegerSpinnerValueFactory(1, 26);


        left_rotor_ring.setValueFactory(left_rotor_ring_values);
        middle_rotor_ring.setValueFactory(middle_rotor_ring_values);
        right_rotor_ring.setValueFactory(right_rotor_ring_values);

        enhanced_left_rotor_ring.setValueFactory(enhanced_left_rotor_ring_values);
        enhanced_middle_rotor_ring.setValueFactory(enhanced_middle_rotor_ring_values);
        enhanced_right_rotor_ring.setValueFactory(enhanced_right_rotor_ring_values);

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
            if (EnigmaLogger.hasLogged() || EnigmaPlusLogger.hasLogged()) {
                updateVisualiser();
            }
            
        });

        previous_visualisation_button.setOnAction(ActionEvent -> {
            decrementIndex();
            if (EnigmaLogger.hasLogged() || EnigmaPlusLogger.hasLogged()) {
                updateVisualiser();
            }
            
        });

        verbose_logging_toggle.setOnAction(ActionEvent -> {
            if (EnigmaLogger.hasLogged() || EnigmaPlusLogger.hasLogged()) {
                updateVisualiser();
            }
            
        });

        log_toggle_box.setOnAction(ActionEvent -> {
            if (!log_toggle_box.isSelected()) {
                step_check_box.setSelected(false);
            }
            step_check_box.setDisable(!log_toggle_box.isSelected());
            speed_slider.setDisable(!log_toggle_box.isSelected());
        });

        enhanced_enigma_tab.setOnSelectionChanged(ActionEvent -> {
            clearMessageText();
            clearLogging();
            current_permutation_label.setText("~4.03e+26");
        });

        classic_enigma_tab.setOnSelectionChanged(ActionEvent -> {
            clearMessageText();
            clearLogging();
            current_permutation_label.setText("~7.91e+12");
        });

    }

    private void updateVisualiser() {
        boolean verbose = verbose_logging_toggle.isSelected();
        visualiser.clearVisualisation();
        if (enigma_tab_pane.getSelectionModel().getSelectedIndex() == 0) {
            if (!(EnigmaLogger.getEncryptionStep(visualiserIndex).length() == 6)) {
                if (verbose) {
                    visualiser.drawWiringDiagram(visualiserIndex, enigmaModel.getAllPossiblePaths(EnigmaLogger.getRotation(visualiserIndex), false), enigmaModel.getReflector().getWiring());
                }
                else {
                    visualiser.drawWiringDiagram(visualiserIndex, null, enigmaModel.getReflector().getWiring());
                }
            }
            encryption_step_label.setText(EnigmaLogger.getEncryptionStep(visualiserIndex));
            current_rotation_label.setText(EnigmaLogger.getRotationString(visualiserIndex));
        }
        else {
            if (!(EnigmaPlusLogger.getEncryptionStep(visualiserIndex).length() == 6)) {
                boolean isDecoding = decode_radio_button.isSelected();
                if (verbose) {
                    enhancedVisualiser.drawEnhancedWiringDiagram(visualiserIndex, enhancedEnigmaModel.getAllPossiblePaths(EnigmaPlusLogger.getRotation(visualiserIndex), isDecoding), isDecoding);
                }
                else {
                    enhancedVisualiser.drawEnhancedWiringDiagram(visualiserIndex, null, isDecoding);
                }
            }
            encryption_step_label.setText(EnigmaPlusLogger.getEncryptionStep(visualiserIndex));
            current_rotation_label.setText(EnigmaPlusLogger.getRotationString(visualiserIndex));
        }
    }

    private void updateModel() {
        // Build Rotors
        List<Rotor> rotors = new ArrayList<>();
        Rotor leftRotor = new Rotor(cache.getRotor(left_rotor_choice.getValue()));
        leftRotor.setRingSetting(Tools.minusOneInteger(left_rotor_ring.getValue()));
        leftRotor.setRotationPosition(Tools.convertCharToIndex(left_rotor_rotation.getValue()));

        Rotor middleRotor = new Rotor(cache.getRotor(middle_rotor_choice.getValue()));
        middleRotor.setRingSetting(Tools.minusOneInteger(middle_rotor_ring.getValue()));
        middleRotor.setRotationPosition(Tools.convertCharToIndex(middle_rotor_rotation.getValue()));

        Rotor rightRotor = new Rotor(cache.getRotor(right_rotor_choice.getValue()));
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

        // Update enhanced Model
        List<Rotor> enhancedRotors = new ArrayList<>();
        Rotor enhancedLeftRotor = new Rotor(cache.getRotor(enhanced_left_rotor_choice.getValue()));
        enhancedLeftRotor.setRingSetting(Tools.minusOneInteger(enhanced_left_rotor_ring.getValue()));
        enhancedLeftRotor.setRotationPosition(Tools.convertCharToIndex(enhanced_left_rotor_rotation.getValue()));

        Rotor enhancedMiddleRotor = new Rotor(cache.getRotor(enhanced_middle_rotor_choice.getValue()));
        enhancedMiddleRotor.setRingSetting(Tools.minusOneInteger(enhanced_middle_rotor_ring.getValue()));
        enhancedMiddleRotor.setRotationPosition(Tools.convertCharToIndex(enhanced_middle_rotor_rotation.getValue()));

        Rotor enhancedRightRotor = new Rotor(cache.getRotor(enhanced_right_rotor_choice.getValue()));
        enhancedRightRotor.setRingSetting(Tools.minusOneInteger(enhanced_right_rotor_ring.getValue()));
        enhancedRightRotor.setRotationPosition(Tools.convertCharToIndex(enhanced_right_rotor_rotation.getValue()));

        enhancedRotors.add(enhancedRightRotor);
        enhancedRotors.add(enhancedMiddleRotor);
        enhancedRotors.add(enhancedLeftRotor);
        // Build Plugobard
        Plugboard enhancedPlugboard = new Plugboard();
        List<String> enhancedPlugboardPairings = new ArrayList<>();
        for (String string : plugboard_config.getText().split(" ")) {
            enhancedPlugboardPairings.add(string);
        }

        enhancedEnigmaModel = new EnigmaPlus(enhancedRotors, enhancedPlugboard);
        enhancedEnigmaModel.addCables(enhancedPlugboardPairings);
    }

    private void stepViewEnigmaPlus() {
        // Update rotations to match the model
        enhanced_right_rotor_rotation.getValueFactory().setValue(EnigmaPlusLogger.getRotationString(visualiserIndex).charAt(2));
        enhanced_middle_rotor_rotation.getValueFactory().setValue(EnigmaPlusLogger.getRotationString(visualiserIndex).charAt(1));
        enhanced_left_rotor_rotation.getValueFactory().setValue(EnigmaPlusLogger.getRotationString(visualiserIndex).charAt(0));
    }

    private void stepViewEnigma() {
        right_rotor_rotation.getValueFactory().setValue(EnigmaLogger.getRotationString(visualiserIndex).charAt(2));
        middle_rotor_rotation.getValueFactory().setValue(EnigmaLogger.getRotationString(visualiserIndex).charAt(1));
        left_rotor_rotation.getValueFactory().setValue(EnigmaLogger.getRotationString(visualiserIndex).charAt(0));
    }

    public void submitInputText() {
        if (input_text.getText().isEmpty()) {
            return;
        }
        clearLogging();
        boolean logging = log_toggle_box.isSelected();
        updateModel();

        if (enigma_tab_pane.getSelectionModel().getSelectedIndex() == 0) {
            String cypherText = enigmaModel.encode(input_text.getText(), logging);
            visualiserIndex = 0;
            if (step_check_box.isSelected()) {
                log_tab_pane.getSelectionModel().select(1);
                stepThroughEnigma();
                log_text_area.setText(EnigmaLogger.getLog());
            }
            else {
                message_text.setText(cypherText);
                if (logging) {
                    updateVisualiser();
                    log_text_area.setText(EnigmaLogger.getLog());
                } else {
                    clearLogging();
                }
            }
        }
        else {
            String cypherText = "";
            if (encode_radio_button.isSelected()) {
                cypherText = enhancedEnigmaModel.encode(input_text.getText(), logging);
            }
            if (decode_radio_button.isSelected()) {
                cypherText = enhancedEnigmaModel.decode(input_text.getText(), logging);
            }
            visualiserIndex = 0;
            if (step_check_box.isSelected()) {
                log_tab_pane.getSelectionModel().select(1);
                stepThroughEnigmaPlus();
                log_text_area.setText(EnigmaPlusLogger.getLog());
            }
            else {
                message_text.setText(cypherText);
                if (logging) {
                    updateVisualiser();
                } else {
                    clearLogging();
            }
            }
            
        }
    }

    private void disableButtons() {
        submit_text_btn.setDisable(true);
        clear_input_btn.setDisable(true);
        clear_message_btn.setDisable(true);
        next_visualisation_button.setDisable(true);
        previous_visualisation_button.setDisable(true);
    }

    private void enableButtons() {
        submit_text_btn.setDisable(false);
        clear_input_btn.setDisable(false);
        clear_message_btn.setDisable(false);
        next_visualisation_button.setDisable(false);
        previous_visualisation_button.setDisable(false);
    }

    private void stepThroughEnigma() {
        Thread thread = new Thread(() -> {
            try {
                disableButtons();
                for (int i = 0; i < EnigmaLogger.getPlaintext().length(); i++) {
                    if (threadInterrupt) {
                        break;
                    }
                    String cypherSubstring = EnigmaLogger.getCyphertext().substring(0, i + 1);
                    Platform.runLater(() -> updateVisualiser());
                    Platform.runLater(() -> message_text.setText(cypherSubstring));
                    Platform.runLater(() -> stepViewEnigma());
                    long sleepDuration = (long) (speed_slider.getValue() * 1000l); 
                    Thread.sleep(sleepDuration);
                    incrementIndex();
                }
            } catch (Exception exc) {
                System.out.println("THREAD ERROR");
            }
            finally {
                enableButtons();
            }
        });
        thread.start();
    }

    private void stepThroughEnigmaPlus() {
        Thread thread = new Thread(() -> {
            try {
                disableButtons();
                for (int i = 0; i < EnigmaPlusLogger.getPlaintext().length(); i++) {
                    if (threadInterrupt) {
                        break;
                    }
                    String cypherSubstring = EnigmaPlusLogger.getCyphertext().substring(0, i + 1);
                    Platform.runLater(() -> updateVisualiser());
                    Platform.runLater(() -> message_text.setText(cypherSubstring));
                    Platform.runLater(() -> stepViewEnigmaPlus());
                    long sleepDuration = (long) (speed_slider.getValue() * 1000l); 
                    Thread.sleep(sleepDuration);
                    incrementIndex();
                }
            } catch (Exception exc) {
                System.out.println("THREAD ERROR");
            }
            finally {
                enableButtons();
            }
        });
        thread.start();
    }

    public void clearLogging() {
        visualiser.clearVisualisation();
        EnigmaLogger.resetLogger();
        EnigmaPlusLogger.resetLogger();
        log_text_area.setText("");
        encryption_step_label.setText("");
        current_rotation_label.setText("");
        visualiserIndex = 0;
    }

    public void clearInputText() {
        input_text.clear();
        message_text.clear();
        clearLogging();
    }

    public void clearMessageText() {
        message_text.clear();
        clearLogging();
    }
}