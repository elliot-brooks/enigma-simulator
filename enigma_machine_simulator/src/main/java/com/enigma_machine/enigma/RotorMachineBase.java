package com.enigma_machine.enigma;

import java.util.ArrayList;
import java.util.List;

import com.enigma_machine.enigma.exceptions.PlugboardConnectionAlreadyEstablishedException;
import com.enigma_machine.enigma.exceptions.PlugboardConnectionDoesNotExistException;
import com.enigma_machine.tools.Constants;

public abstract class RotorMachineBase {

    /**
     * Important to note that rotor[0] represents the right-most rotor in the enigma
     * machine i.e. the first rotor the current will pass through
     */
    protected List<Rotor> rotors = new ArrayList<>();
    protected Plugboard plugboard;

    public static final int ROTOR_SLOT_1 = 0;
    public static final int ROTOR_SLOT_2 = 1;
    public static final int ROTOR_SLOT_3 = 2;

    public List<Rotor> getRotors() {
        return rotors;
    }
    public Plugboard getPlugboard() {
        return plugboard;
    }
    public abstract String encode(String message, boolean loggingEnabled);
    public abstract String decode(String message, boolean loggingEnabled);
    public abstract List<String> getAllPossiblePaths(int[] rotations, boolean isDecoding);

    public List<String> getAllCurrentPossiblePaths(boolean isDecoding) {
        return getAllPossiblePaths(getCurrentRotation(), isDecoding);
    }

    public void setRotor(int position, Rotor newRotor) {
        getRotors().set(position, newRotor);
    }

    public void setRotors(List<Rotor> rotors) {
        this.rotors = rotors;
    }

    public void resetMachine() {
        for (Rotor rotor : rotors) {
            rotor.setRotationPosition(0);
            rotor.setRingSetting(0);
        }
        plugboard = new Plugboard();
    }

    public void configureRotorRingSetting(int position, int ringSetting) {
        getRotors().get(position).setRingSetting(ringSetting);
    }

    public void configureRotorRingSettings(int[] ringSettings) {
        for (int i = 0; i < ringSettings.length; i++) {
            getRotors().get(i).setRingSetting(ringSettings[i]);
        }
    }

    public void configureRotorRotation(int position, int rotation) {
        getRotors().get(position).setRotationPosition(rotation);
    }

    public void configureRotorRotations(int[] rotations) {
        for (int i = 0; i < rotations.length; i++) {
            getRotors().get(i).setRotationPosition(rotations[i]);
        }
    }

    public int[] getCurrentRotation() {
        int[] rotations = new int[3];
        rotations[0] = getRotors().get(ROTOR_SLOT_1).getRotationPosition();
        rotations[1] = getRotors().get(ROTOR_SLOT_2).getRotationPosition();
        rotations[2] = getRotors().get(ROTOR_SLOT_3).getRotationPosition();
        return rotations;
    }

    public int[] getRingSettings() {
        int[] ringSettings = new int[3];
        ringSettings[0] = getRotors().get(ROTOR_SLOT_1).getRingSetting();
        ringSettings[1] = getRotors().get(ROTOR_SLOT_2).getRingSetting();
        ringSettings[2] = getRotors().get(ROTOR_SLOT_3).getRingSetting();
        return ringSettings;
    }

    protected int[] parseCablePairing(String cablePairing) {
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
    
    public void addCable(String cablePairing) throws PlugboardConnectionAlreadyEstablishedException {
        if (!cablePairing.matches("[A-Z]+") || cablePairing.length() != 2) {
            return;
        }

        int[] parsedPairing = parseCablePairing(cablePairing);
        getPlugboard().addCable(parsedPairing[0], parsedPairing[1]);
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

    public void removeCable(String cablePairing) throws PlugboardConnectionDoesNotExistException {
        if (!cablePairing.matches("[A-Z]+") || cablePairing.length() != 2) {
            return;
        }
        int[] parsedPairing = parseCablePairing(cablePairing);
        getPlugboard().removeCable(parsedPairing[0], parsedPairing[1]);
    }

    protected void rotate() {
        // Double stepping
        boolean doubleStepped = false;
        if (getRotors().get(ROTOR_SLOT_2).isAtTurnoverPosition()) {
            getRotors().get(ROTOR_SLOT_2).rotate();
            getRotors().get(ROTOR_SLOT_3).rotate();
            doubleStepped = true;
        }
        if (getRotors().get(ROTOR_SLOT_1).isAtTurnoverPosition() && !doubleStepped) {
            getRotors().get(ROTOR_SLOT_2).rotate();
        }

        getRotors().get(ROTOR_SLOT_1).rotate();
    }

    

}
