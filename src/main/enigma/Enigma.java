package main.enigma;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.tools.Constants;
import main.tools.Tools;

public class Enigma {
    /**
     * Important to note that rotor[0] represents the right-most rotor in the enigma
     * machine i.e. the first rotor the current will pass through
     */
    private List<Rotor> rotors;
    private Plugboard plugboard;
    private Reflector reflector;

    private static final int ROTOR_SLOT_1 = 0;
    private static final int ROTOR_SLOT_2 = 1;
    private static final int ROTOR_SLOT_3 = 2;

    private Enigma(List<Rotor> rotors, Plugboard plugobard, Reflector reflector) {
        this.rotors = rotors;
        this.plugboard = plugobard;
        this.reflector = reflector;
    }

    public static Enigma createCustomEnigma(List<Rotor> rotors, Plugboard plugboard, Reflector reflector) {
        Enigma instance = new Enigma(rotors, plugboard, reflector);
        return instance;
    }

    public static Enigma createDefaultEnigma() {
        List<Rotor> defaultRotors = new ArrayList<>();
        Rotor rightRotor = RotorFactory.buildPresetRotor(RotorFactory.III_ROTOR, 0, 0);
        Rotor middleRotor = RotorFactory.buildPresetRotor(RotorFactory.II_ROTOR, 0, 0);
        Rotor leftRotor = RotorFactory.buildPresetRotor(RotorFactory.I_ROTOR, 0, 0);
        defaultRotors.add(rightRotor);
        defaultRotors.add(middleRotor);
        defaultRotors.add(leftRotor);

        Reflector defaultReflector = ReflectorFactory.buildPresetReflector(ReflectorFactory.B_REFLECTOR);
        Plugboard plugboard = new Plugboard();
        Enigma instance = new Enigma(defaultRotors, plugboard, defaultReflector);
        return instance;
    }

    // TODO : Implemenmt a method for parsing a file
    public static Enigma parseEnigmaFromFile(File file) {
        return null;
    }

    public String getCurrentSettings() {
        HashMap<Integer, String> rotorMap = new HashMap<>();
        rotorMap.put(0, "Right Rotor : ");
        rotorMap.put(1, "Middle Rotor : ");
        rotorMap.put(2, "Left Rotor : ");

        StringBuilder sb = new StringBuilder();
        sb.append("Plugboard : ");
        sb.append(plugboard.getPairedCharacters() + "\n");
        sb.append("Reflector : " + reflector.getName() + " (" + reflector.getEncoding() + ")\n");
        for (int rotorSlot = 0; rotorSlot < rotors.size(); rotorSlot++) {
            sb.append(rotorMap.get(rotorSlot) + rotors.get(rotorSlot).getName() + "\n");
            sb.append("    Current Rotation : " + rotors.get(rotorSlot).getRotationPosition() + "\n");
            sb.append("    Ring Setting : " + rotors.get(rotorSlot).getRingSetting() + "\n");
            sb.append("    Encoding : " + rotors.get(rotorSlot).getEncoding() + "\n");
        }

        return sb.toString();
    }

    private void rotate() {
        // Double stepping
        if (rotors.get(ROTOR_SLOT_2).isAtTurnoverPosition()) {
            rotors.get(ROTOR_SLOT_2).rotate();
            rotors.get(ROTOR_SLOT_3).rotate();
        }
        if (rotors.get(ROTOR_SLOT_1).isAtTurnoverPosition()) {
            rotors.get(ROTOR_SLOT_2).rotate();
        }

        rotors.get(ROTOR_SLOT_1).rotate();
    }

    public char encrypt(char character) throws MissingEncodingException {
        if (character == ' ') {
            return character;
        }
        rotate();
        int characterIndex = character - Constants.JAVA_A_VALUE;
        int newChar;
        newChar = plugboard.encrypt(characterIndex);
        for (int i = 0; i < rotors.size(); i++) {
            newChar = rotors.get(i).encrypt(newChar, Direction.FORWARD);
        }
        newChar = reflector.encrypt(newChar);
        for (int i = rotors.size(); i-- > 0;) {
            newChar = rotors.get(i).encrypt(newChar, Direction.BACKWARD);
        }
        newChar = plugboard.encrypt(newChar);
        return Tools.convertIndexToCharacter(newChar);

    }

    public String encrypt(String message) throws MissingEncodingException {
        char[] charArray = message.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            sb.append(encrypt(c));
        }
        return sb.toString();
    }

    public void resetMachine() {
        for (Rotor rotor : rotors) {
            rotor.setRotationPosition(0);
            rotor.setRingSetting(0);
        }
        // TODO : reset plugboard
    }

}
