package com.enigma_machine.enigma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.enigma_machine.tools.Constants;
import com.enigma_machine.tools.Tools;

public class Enigma extends RotorMachineBase{
    
    private Reflector reflector;
    public static final int ROTOR_SLOT_1 = 0;
    public static final int ROTOR_SLOT_2 = 1;
    public static final int ROTOR_SLOT_3 = 2;

    private Enigma(List<Rotor> rotorsIn, Plugboard plugobard, Reflector reflector) {
        super();
        this.rotors = rotorsIn;
        this.plugboard = plugobard;
        this.reflector = reflector;
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

    public static Enigma createCustomEnigma(List<Rotor> rotors, Plugboard plugboard, Reflector reflector) {
        Enigma instance = new Enigma(rotors, plugboard, reflector);
        return instance;
    }

    public void setReflector(Reflector reflector) {
        this.reflector = reflector;
    }

    public Reflector getReflector() {
        return reflector;
    }

    public String getCurrentSettings() {
        HashMap<Integer, String> rotorMap = new HashMap<>();
        rotorMap.put(0, "Right Rotor : ");
        rotorMap.put(1, "Middle Rotor : ");
        rotorMap.put(2, "Left Rotor : ");

        StringBuilder sb = new StringBuilder();
        sb.append("Plugboard : ");
        sb.append("[" + plugboard.getEncoding() + "]\n");
        sb.append("Reflector : " + reflector.getName() + " (" + reflector.getEncoding() + ")\n");
        for (int rotorSlot = 0; rotorSlot < rotors.size(); rotorSlot++) {
            sb.append(rotorMap.get(rotorSlot) + rotors.get(rotorSlot).getName() + "\n");
            sb.append("    Rotation : "
                    + Tools.convertIndexToCharacter(rotors.get(rotorSlot).getRotationPosition()) + "\n");
            sb.append("    Ring Setting : " + Tools.plusOneInteger(rotors.get(rotorSlot).getRingSetting()) + "\n");
            sb.append("    Encoding : " + rotors.get(rotorSlot).getEncoding() + "\n");
        }

        return sb.toString();
    }

    @Override
    public String decode(String message, boolean loggingEnabled) {
        return encode(message, loggingEnabled);
    }

    @Override
    public String encode(String message, boolean loggingEnabled) {
        EnigmaLogger.resetLogger();
        if (loggingEnabled) {
            EnigmaLogger.appendLine("INITIAL SETTINGS");
            EnigmaLogger.appendLine(getCurrentSettings());
            EnigmaLogger.setReflectorName(reflector.getName());
            for (int i = 0; i < rotors.size(); i++ ) {
                EnigmaLogger.addRotorName(rotors.get(i).getName());
            }
        }
        message = message.toUpperCase();
        char[] charArray = message.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            if (!loggingEnabled) {
                sb.append(encrypt(c));
            }
            else {
                sb.append(loggedEncryption(c));
            }
            
        }
        if (loggingEnabled) {
            EnigmaLogger.appendLine("\nFINAL SETTINGS");
            EnigmaLogger.appendLine(getCurrentSettings());
            EnigmaLogger.setRingSetting(getRingSettings());
            EnigmaLogger.setPlaintext(message);
            EnigmaLogger.setCyphertext(sb.toString());
        }
        return sb.toString();
    }

    @Override
    public List<String> getAllCurrentPossiblePaths(boolean isDecoding) {
        return getAllPossiblePaths(getCurrentRotation(), false);
    }

    @Override
    public List<String> getAllPossiblePaths(int[] rotations, boolean isDecoding) {
        isDecoding = false;
        int[] currentRotation = getCurrentRotation();
        this.configureRotorRotations(rotations);
        List<String> possibleEncryptionPaths = new ArrayList<String>();
        for (int index = 0; index < Constants.ALPHABET_LENGTH; index++) {
            int charIndex = index;
            String encryptionPath = "";
            encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
            charIndex = plugboard.encrypt(charIndex);
            encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
            for (int i = 0; i < rotors.size(); i++) {
                charIndex = rotors.get(i).encrypt(charIndex, Direction.FORWARD);
                encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
            }
            charIndex = reflector.encrypt(charIndex);
            encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
            for (int i = rotors.size(); i-- > 0;) {
                charIndex = rotors.get(i).encrypt(charIndex, Direction.BACKWARD);
                encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
            }
            charIndex = plugboard.encrypt(charIndex);
            encryptionPath += Tools.convertIndexToCharacter(charIndex);
            possibleEncryptionPaths.add(encryptionPath);
        }
        this.configureRotorRotations(currentRotation);
        return possibleEncryptionPaths;
    }

    private char loggedEncryption(char character) {
        if (!Character.isLetter(character)) {
            EnigmaLogger.addEncryptionStep(character + " -> " + character);
            EnigmaLogger.addRotation(getCurrentRotation());
            String rotationString = "";
            for (int i = rotors.size(); i-- > 0;) {
                rotationString += Tools.convertIndexToCharacter(rotors.get(i).getRotationPosition()); 
            }
            EnigmaLogger.addRotationString(rotationString);
            return character;
        }
        EnigmaLogger.setLogged(true);
        EnigmaLogger.appendLine("--INPUT CHARACTER [" + character + "]--");
        String encryptionPath = character + " -> ";
        String currentMessageKey = "Current Rotations : ";
        rotate();
        int characterIndex = character - Constants.JAVA_A_VALUE;
        int newChar;
        newChar = plugboard.encrypt(characterIndex);
        encryptionPath += Tools.convertIndexToCharacter(newChar) + " -> ";
        for (int i = 0; i < rotors.size(); i++) {
            newChar = rotors.get(i).encrypt(newChar, Direction.FORWARD);
            encryptionPath += Tools.convertIndexToCharacter(newChar) + " -> ";
        }
        newChar = reflector.encrypt(newChar);
        encryptionPath += Tools.convertIndexToCharacter(newChar) + " -> ";
        String rotationString = "";
        for (int i = rotors.size(); i-- > 0;) {
            rotationString += Tools.convertIndexToCharacter(rotors.get(i).getRotationPosition()); 
            newChar = rotors.get(i).encrypt(newChar, Direction.BACKWARD);
            encryptionPath += Tools.convertIndexToCharacter(newChar) + " -> ";
        }
        EnigmaLogger.addRotationString(rotationString);
        EnigmaLogger.addRotation(getCurrentRotation());
        currentMessageKey += rotationString;
        newChar = plugboard.encrypt(newChar);
        encryptionPath += Tools.convertIndexToCharacter(newChar);
        EnigmaLogger.appendLine(currentMessageKey);
        EnigmaLogger.appendLine(encryptionPath);
        EnigmaLogger.addEncryptionStep(encryptionPath);
        return Tools.convertIndexToCharacter(newChar);
    }

    private char encrypt(char character) {
        if (!Character.isLetter(character)) {
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

}
