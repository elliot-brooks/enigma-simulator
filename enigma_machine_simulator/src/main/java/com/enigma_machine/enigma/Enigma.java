package com.enigma_machine.enigma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.enigma_machine.enigma.exceptions.PlugboardConnectionAlreadyEstablishedException;
import com.enigma_machine.enigma.exceptions.PlugboardConnectionDoesNotExistException;
import com.enigma_machine.logger.EnigmaLogger;
import com.enigma_machine.tools.Constants;
import com.enigma_machine.tools.Tools;

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

    public void setRotors(List<Rotor> rotors) {
        this.rotors = rotors;
    }

    public void setPlugboard(Plugboard plugboard) {
        this.plugboard = plugboard;
    }

    public void setReflector(Reflector reflector) {
        this.reflector = reflector;
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

    private void rotate() {
        // Double stepping
        boolean doubleStepped = false;
        if (rotors.get(ROTOR_SLOT_2).isAtTurnoverPosition()) {
            rotors.get(ROTOR_SLOT_2).rotate();
            rotors.get(ROTOR_SLOT_3).rotate();
            doubleStepped = true;
        }
        if (rotors.get(ROTOR_SLOT_1).isAtTurnoverPosition() && !doubleStepped) {
            rotors.get(ROTOR_SLOT_2).rotate();
        }

        rotors.get(ROTOR_SLOT_1).rotate();
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

    private char loggedEncryption(char character) {
        if (!Character.isLetter(character)) {
            return character;
        }
        EnigmaLogger.setLogged(true);
        EnigmaLogger.appendLine("--INPUT CHARACTER [" + character + "]--");
        EnigmaLogger.addRotation(getCurrentRotation());
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
        currentMessageKey += rotationString;
        newChar = plugboard.encrypt(newChar);
        encryptionPath += Tools.convertIndexToCharacter(newChar);
        EnigmaLogger.appendLine(currentMessageKey);
        EnigmaLogger.appendLine(encryptionPath);
        EnigmaLogger.addEncrryptionStep(encryptionPath);
        return Tools.convertIndexToCharacter(newChar);
    }

    public String encrypt(String message, boolean loggingEnabled) {
        EnigmaLogger.resetLogger();
        if (loggingEnabled) {
            EnigmaLogger.appendLine("INITIAL SETTINGS");
            EnigmaLogger.appendLine(getCurrentSettings());
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
        rotations[0] = rotors.get(ROTOR_SLOT_1).getRotationPosition();
        rotations[1] = rotors.get(ROTOR_SLOT_2).getRotationPosition();
        rotations[2] = rotors.get(ROTOR_SLOT_3).getRotationPosition();
        return rotations;
    }

    public int[] getRingSettings() {
        int[] ringSettings = new int[3];
        ringSettings[0] = rotors.get(ROTOR_SLOT_1).getRingSetting();
        ringSettings[1] = rotors.get(ROTOR_SLOT_2).getRingSetting();
        ringSettings[2] = rotors.get(ROTOR_SLOT_3).getRingSetting();
        return ringSettings;
    }

    public void removeCable(String cablePairing) throws PlugboardConnectionDoesNotExistException {
        if (!cablePairing.matches("[A-Z]+") || cablePairing.length() != 2) {
            return;
        }
        int[] parsedPairing = parseCablePairing(cablePairing);
        plugboard.removeCable(parsedPairing[0], parsedPairing[1]);
    }

}
