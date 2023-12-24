package com.enigma_machine.enigma;

import java.util.ArrayList;
import java.util.List;

import com.enigma_machine.enigma.exceptions.PlugboardConnectionAlreadyEstablishedException;
import com.enigma_machine.enigma.exceptions.PlugboardConnectionDoesNotExistException;
import com.enigma_machine.tools.Constants;
import com.enigma_machine.tools.Tools;

public class EnhancedEnigma {

    private List<Rotor> rotors = new ArrayList<>();
    private Plugboard plugboard;
    
    public EnhancedEnigma(List<Rotor> rotorsIn, Plugboard plugboard) {
        this.rotors = rotorsIn;
        this.plugboard = plugboard;
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
        }
        return sb.toString();
    }

    public String encrypt(String message, boolean loggingEnabled) {
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
            EnhancedEnigmaLogger.setPlaintext(message);
            EnhancedEnigmaLogger.setCyphertext(sb.toString());
        }
        return sb.toString();
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
    public void setRotors(List<Rotor> rotors) {
        this.rotors = rotors;
    }

    public void setPlugboard(Plugboard plugboard) {
        this.plugboard = plugboard;
    }

    public Plugboard getPlugboard() {
        return plugboard;
    }

    public List<Rotor> getRotors() {
        return rotors;
    }

    private void rotate() {
        // Double stepping
        boolean doubleStepped = false;
        if (rotors.get(Enigma.ROTOR_SLOT_2).isAtTurnoverPosition()) {
            rotors.get(Enigma.ROTOR_SLOT_2).rotate();
            rotors.get(Enigma.ROTOR_SLOT_3).rotate();
            doubleStepped = true;
        }
        if (rotors.get(Enigma.ROTOR_SLOT_1).isAtTurnoverPosition() && !doubleStepped) {
            rotors.get(Enigma.ROTOR_SLOT_2).rotate();
        }

        rotors.get(Enigma.ROTOR_SLOT_1).rotate();
    }


    public void setRotor(int position, Rotor newRotor) {
        rotors.set(position, newRotor);
    }

    public void configureRotorRingSetting(int position, int ringSetting) {
        rotors.get(position).setRingSetting(ringSetting);
    }

    public void configureRotorRingSettings(int[] ringSettings) {
        for (int i = 0; i < ringSettings.length; i++) {
            rotors.get(i).setRingSetting(ringSettings[i]);
        }
    }

    public void configureRotorRotation(int position, int rotation) {
        rotors.get(position).setRotationPosition(rotation);
    }

    public void configureRotorRotations(int[] rotations) {
        for (int i = 0; i < rotations.length; i++) {
            rotors.get(i).setRotationPosition(rotations[i]);
        }
    }

    public void resetMachine() {
        for (Rotor rotor : rotors) {
            rotor.setRotationPosition(0);
            rotor.setRingSetting(0);
        }
        plugboard = new Plugboard();
    }

    /**
     * Turns 'AB' into [0, 1]
     * 
     * @param cablePairing
     * @return
     */
    private int[] parseCablePairing(String cablePairing) {
        char[] charArray = cablePairing.toCharArray();
        int[] parsedPairing = new int[2];

        for (int i = 0; i < charArray.length; i++) {
            parsedPairing[i] = charArray[i] - Constants.JAVA_A_VALUE;
        }
        return parsedPairing;
    }

    public void addCables(List<String> cablePairings) {
        for (String string : cablePairings) {
            try {
                addCable(string);
            } catch (PlugboardConnectionAlreadyEstablishedException e) {
                // Ignore
            }
        }
    }

    public void removeCables(List<String> cablePairings) {
        for (String string : cablePairings) {
            try {
                removeCable(string);
            } catch (PlugboardConnectionDoesNotExistException e) {
                // Ignore
            }
        }
    }

    public void addCable(String cablePairing) throws PlugboardConnectionAlreadyEstablishedException {
        if (!cablePairing.matches("[A-Z]+") || cablePairing.length() != 2) {
            return;
        }

        int[] parsedPairing = parseCablePairing(cablePairing);
        plugboard.addCable(parsedPairing[0], parsedPairing[1]);
    }

    public int[] getCurrentRotation() {
        int[] rotations = new int[3];
        rotations[0] = rotors.get(Enigma.ROTOR_SLOT_1).getRotationPosition();
        rotations[1] = rotors.get(Enigma.ROTOR_SLOT_2).getRotationPosition();
        rotations[2] = rotors.get(Enigma.ROTOR_SLOT_3).getRotationPosition();
        return rotations;
    }

    public int[] getRingSettings() {
        int[] ringSettings = new int[3];
        ringSettings[0] = rotors.get(Enigma.ROTOR_SLOT_1).getRingSetting();
        ringSettings[1] = rotors.get(Enigma.ROTOR_SLOT_2).getRingSetting();
        ringSettings[2] = rotors.get(Enigma.ROTOR_SLOT_3).getRingSetting();
        return ringSettings;
    }

    public void removeCable(String cablePairing) throws PlugboardConnectionDoesNotExistException {
        if (!cablePairing.matches("[A-Z]+") || cablePairing.length() != 2) {
            return;
        }
        int[] parsedPairing = parseCablePairing(cablePairing);
        plugboard.removeCable(parsedPairing[0], parsedPairing[1]);
    }

    /**
     * Returns all possible encryption paths for a given rotation
     */
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
    
}