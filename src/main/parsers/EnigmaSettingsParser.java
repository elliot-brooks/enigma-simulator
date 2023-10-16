package main.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.enigma.Plugboard;
import main.enigma.Reflector;
import main.enigma.Rotor;

public class EnigmaSettingsParser {
    private static final String ENIGMA_TAG = "enigma";
    private static final String ROTOR_TAG = "rotor";
    private static final String REFLECTOR_TAG = "reflector";
    private static final String PLUGBOARD_TAG = "plugboard";
    private static final String NAME_TAG = "name";
    private static final String ENCODING_TAG = "encoding";
    private static final String RING_SETTING_TAG = "ring_setting";
    private static final String START_POSITION_TAG = "start_position";

    private File file;

    private Plugboard plugboard;
    private List<Rotor> rotors;
    private Reflector reflector;

    public EnigmaSettingsParser(File file) {
        this.file = file;
    }

    // TODO: parse settings.enigma here and assign values to attributes
    public void parse() {
        return;
    }

    public Plugboard getPlugboard() {
        return plugboard;
    }

    public List<Rotor> getRotors() {
        return rotors;
    }

    public Reflector getReflector() {
        return reflector;
    }

}
