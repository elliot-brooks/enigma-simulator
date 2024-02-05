package com.enigma_machine.enigma;

import java.util.ArrayList;
import java.util.List;

import com.enigma_machine.tools.Constants;
import com.enigma_machine.tools.Tools;

public class EnhancedEnigma extends RotorMachineBase{
    
    public EnhancedEnigma(List<Rotor> rotorsIn, Plugboard plugboard) {
        super();
        this.rotors = rotorsIn;
        this.plugboard = plugboard;
    }

    public static EnhancedEnigma createDefaultEnhancedEnigma() {
        List<Rotor> defaultRotors = new ArrayList<>();
        Rotor rightRotor = RotorFactory.buildPresetRotor(RotorFactory.III_ROTOR, 0, 0);
        Rotor middleRotor = RotorFactory.buildPresetRotor(RotorFactory.II_ROTOR, 0, 0);
        Rotor leftRotor = RotorFactory.buildPresetRotor(RotorFactory.I_ROTOR, 0, 0);
        defaultRotors.add(rightRotor);
        defaultRotors.add(middleRotor);
        defaultRotors.add(leftRotor);

        Plugboard plugboard = new Plugboard();
        EnhancedEnigma instance = new EnhancedEnigma(defaultRotors, plugboard);
        return instance;
    }

    @Override
    public String encode(String message, boolean loggingEnabled) {
        message = message.toUpperCase();
        char[] charArray = message.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rotors.size(); i++ ) {
            EnhancedEnigmaLogger.addRotorName(rotors.get(i).getName());
        }
        for (char c : charArray) {
            if (!loggingEnabled) {
                sb.append(encrypt(c));
            }
            else {
                sb.append(loggedEncryption(c));
            }
            
        }

        if (loggingEnabled) {
            EnhancedEnigmaLogger.setPlaintext(message);
            EnhancedEnigmaLogger.setCyphertext(sb.toString());
            for (int i = 0; i < rotors.size(); i++ ) {
                EnhancedEnigmaLogger.addRotorName(rotors.get(i).getName());
            }
        }
        return sb.toString();
    }

    @Override
    public String decode(String message, boolean loggingEnabled) {
        message = message.toUpperCase();
        char[] charArray = message.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            if (!loggingEnabled) {
                sb.append(decode(c));
            }
            else {
                sb.append(loggedDecoding(c));
            }
            
        }
        if (loggingEnabled) {
            EnhancedEnigmaLogger.setPlaintext(message);
            EnhancedEnigmaLogger.setCyphertext(sb.toString());
            for (int i = 0; i < rotors.size(); i++ ) {
                EnhancedEnigmaLogger.addRotorName(rotors.get(i).getName());
            }
        }
        return sb.toString();
    }

    @Override
    public List<String> getAllPossiblePaths(int[] rotations, boolean isDecoding) {
        int[] currentRotation = getCurrentRotation();
        this.configureRotorRotations(rotations);
        List<String> possibleEncryptionPaths = new ArrayList<String>();
        
        for (int index = 0; index < Constants.ALPHABET_LENGTH; index++) {
            int charIndex = index;
            String encryptionPath = "";
            encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
            if (isDecoding) {
                for (int i = rotors.size(); i-- > 0;) {
                    charIndex = rotors.get(i).encrypt(charIndex, Direction.BACKWARD);
                    encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
                }
                charIndex = plugboard.encrypt(charIndex);
                encryptionPath += Tools.convertIndexToCharacter(charIndex);
            }
            else {
                charIndex = plugboard.encrypt(charIndex);
                encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
                for (int i = 0; i < rotors.size() - 1; i++) {
                    charIndex = rotors.get(i).encrypt(charIndex, Direction.FORWARD);
                    encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
                }
                charIndex = rotors.get(2).encrypt(charIndex, Direction.FORWARD);
                encryptionPath += Tools.convertIndexToCharacter(charIndex) + " -> ";
            }
            possibleEncryptionPaths.add(encryptionPath);
        }
        this.configureRotorRotations(currentRotation);
        return possibleEncryptionPaths;
    }

    public List<String> getAllCurrentPossiblePaths(boolean isDecoding) {
        return getAllPossiblePaths(getCurrentRotation(), isDecoding);
    }

    // Encryption only takes place one way.
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
        return Tools.convertIndexToCharacter(newChar);
    }

    private char decode(char character) {
        if (!Character.isLetter(character)) {
            return character;
        }
        rotate();
        int characterIndex = character - Constants.JAVA_A_VALUE;
        int newChar = characterIndex;
        for (int i = rotors.size(); i-- > 0;) {
            newChar = rotors.get(i).encrypt(newChar, Direction.BACKWARD);
        }
        newChar = plugboard.encrypt(newChar);
        return Tools.convertIndexToCharacter(newChar);
    }

    private char loggedEncryption(char character) {
        EnhancedEnigmaLogger.setIsDecoding(false);
        if (!Character.isLetter(character)) {
            EnhancedEnigmaLogger.addEncryptionStep(character + " -> " + character);
            EnhancedEnigmaLogger.addRotation(getCurrentRotation());
            String rotationString = "";
            for (int i = rotors.size(); i-- > 0;) {
                rotationString += Tools.convertIndexToCharacter(rotors.get(i).getRotationPosition()); 
            }
            EnhancedEnigmaLogger.addRotationString(rotationString);

            return character;
        }
        EnhancedEnigmaLogger.setLogged(true);
        String encryptionPath = character + " -> ";
        rotate();
        int characterIndex = character - Constants.JAVA_A_VALUE;
        int newChar;
        newChar = plugboard.encrypt(characterIndex);
        encryptionPath += Tools.convertIndexToCharacter(newChar) + " -> ";
        for (int i = 0; i < rotors.size() - 1; i++) {
            newChar = rotors.get(i).encrypt(newChar, Direction.FORWARD);
            encryptionPath += Tools.convertIndexToCharacter(newChar) + " -> ";
        }
        newChar = rotors.get(2).encrypt(newChar, Direction.FORWARD);
        encryptionPath += Tools.convertIndexToCharacter(newChar);

        // Get rotation String
        String rotationString = "";
        for (int i = rotors.size(); i-- > 0;) {
            rotationString += Tools.convertIndexToCharacter(rotors.get(i).getRotationPosition()); 
        }
        EnhancedEnigmaLogger.addRotationString(rotationString);
        EnhancedEnigmaLogger.addRotation(getCurrentRotation());
        EnhancedEnigmaLogger.addEncryptionStep(encryptionPath);
        return Tools.convertIndexToCharacter(newChar);
    }


    private char loggedDecoding(char character) {
        EnhancedEnigmaLogger.setIsDecoding(true);
        if (!Character.isLetter(character)) {
            EnhancedEnigmaLogger.addEncryptionStep(character + " -> " + character);
            EnhancedEnigmaLogger.addRotation(getCurrentRotation());
            String rotationString = "";
            for (int i = rotors.size(); i-- > 0;) {
                rotationString += Tools.convertIndexToCharacter(rotors.get(i).getRotationPosition()); 
            }
            EnhancedEnigmaLogger.addRotationString(rotationString);

            return character;        
        }
        EnhancedEnigmaLogger.setLogged(true);
        String encryptionPath = character + " -> ";
        rotate();
        int characterIndex = character - Constants.JAVA_A_VALUE;
        int newChar = characterIndex;
        String rotationString = "";

        for (int i = rotors.size(); i-- > 0;) {
            rotationString += Tools.convertIndexToCharacter(rotors.get(i).getRotationPosition()); 
            newChar = rotors.get(i).encrypt(newChar, Direction.BACKWARD);
            encryptionPath += Tools.convertIndexToCharacter(newChar) + " -> ";
        }
        EnhancedEnigmaLogger.addRotationString(rotationString);
        EnhancedEnigmaLogger.addRotation(getCurrentRotation());
        newChar = plugboard.encrypt(newChar);
        encryptionPath += Tools.convertIndexToCharacter(newChar);
        EnhancedEnigmaLogger.addEncryptionStep(encryptionPath);
        return Tools.convertIndexToCharacter(newChar);
    }
    
}