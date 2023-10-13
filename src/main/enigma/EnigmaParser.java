package main.enigma;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EnigmaParser {
    private static final String PLUGBOARD_TAG = "<PLUGBOARD>";
    private static final String RIGHT_ROTOR_TAG = "<RIGHT ROTOR>";
    private static final String MIDDLE_ROTOR_TAG = "<MIDDLE ROTOR>";
    private static final String LEFT_ROTOR_TAG = "<LEFT ROTOR>";
    private static final String REFLECTOR_TAG = "<REFLECTOR>";
    private static final String PRESET_TAG = "<PRESET>";
    private static final String CUSTOM_TAG = "<CUSTOM>";

    private File file;

    private Plugboard plugboard;
    private List<Rotor> rotors;
    private Reflector reflector;

    public EnigmaParser(File file) {
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
