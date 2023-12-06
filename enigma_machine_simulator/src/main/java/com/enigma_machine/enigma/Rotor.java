package com.enigma_machine.enigma;

import com.enigma_machine.tools.Constants;

public class Rotor {
    private String encoding;
    private int[] wiring;
    private int[] wiringReversed;
    private int rotationPosition;
    private int ringSetting;
    private int turnoverPosition;
    private String name;

    Rotor(String name, String encoding, int ringSetting, int rotation, int turnoverPosition) {
        this.name = name;
        this.encoding = encoding;
        configureWiring(encoding);
        this.rotationPosition = rotation;
        this.ringSetting = ringSetting;
        this.turnoverPosition = turnoverPosition;
    }

    public Rotor(Rotor oldRotor) {
        this.name = oldRotor.name;
        this.encoding = oldRotor.encoding;
        configureWiring(encoding);
        this.rotationPosition = oldRotor.rotationPosition;
        this.ringSetting = oldRotor.ringSetting;
        this.turnoverPosition = oldRotor.turnoverPosition;
    }

    /**
     * Takes a given character and direction and simulates the passing of current
     * through the rotor to give return an integer representing the encrypted
     * character
     * 
     * @param characterIndex - The character before being encrypted
     * @param dir            - The direction of the encryption i.e. which side the
     *                       of the rotor the signal passes through
     * @return The cypher text
     */
    public int encrypt(int characterIndex, Direction dir) {
        int rotorShift = rotationPosition - ringSetting;

        switch (dir) {
            case FORWARD:
                return (wiring[(characterIndex + rotorShift + Constants.ALPHABET_LENGTH) % Constants.ALPHABET_LENGTH]
                        - rotorShift + Constants.ALPHABET_LENGTH) % Constants.ALPHABET_LENGTH;
            case BACKWARD:
                return (wiringReversed[(characterIndex + rotorShift + Constants.ALPHABET_LENGTH)
                        % Constants.ALPHABET_LENGTH]
                        - rotorShift + Constants.ALPHABET_LENGTH) % Constants.ALPHABET_LENGTH;
            default:
                return 0;
        }
    }

    /**
     * Take a string encoding such as 'zyxwvutsrqponmlkjihgfedbca' and turn it into
     * an array of integers. The position corresponds to the letter of the standard
     * alphabet order and the value at that position corresponds to the character it
     * will be encrypted as. E.g. wiring[0] = 1 means A -> B
     */
    private void configureWiringForward() {
        char[] charArray = encoding.toCharArray();
        wiring = new int[charArray.length];
        for (int i = 0; i < wiring.length; i++) {
            wiring[i] = charArray[i] - Constants.JAVA_A_VALUE;
        }
    }

    /**
     * Create an integer list which holds the reverse of the wiring such that if
     * wiring[0] = 1 then wiringReverse[1] = 0;
     */
    private void configureWiringBackwards() {
        wiringReversed = new int[wiring.length];
        // For each letter in the alphabet
        for (int i = 0; i < wiringReversed.length; i++) {
            // Find what the connection is in the forward direction and reverse
            int cypherCharacter = wiring[i];
            wiringReversed[cypherCharacter] = i;
        }
    }

    public void configureWiring(String encoding) {
        this.encoding = encoding;
        configureWiringForward();
        configureWiringBackwards();
    }

    public void setRotationPosition(int newRotation) {
        rotationPosition = (newRotation + 26) % 26;
    }

    public int getRotationPosition() {
        return rotationPosition;
    }

    public boolean isAtTurnoverPosition() {
        return this.rotationPosition == this.turnoverPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Simulates the rotation of the rotor
     */
    public void rotate() {
        // Check for when rotation is max, rotate the next rotor
        rotationPosition = (rotationPosition + 1) % Constants.ALPHABET_LENGTH;
    }

    public int getRingSetting() {
        return ringSetting;
    }

    public void setRingSetting(int setting) {
        ringSetting = (setting + 26) % 26;
    }

    public String getEncoding() {
        return encoding;
    }
}