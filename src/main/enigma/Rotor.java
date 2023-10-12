package main.enigma;

import main.tools.Constants;

public class Rotor {
    private String encoding;
    private int[] wiring;
    private int[] wiringReversed;
    private int rotationPosition;
    private int ringSetting;
    private int turnoverPosition;
    private String name;

    public Rotor(String name, String encoding, int rotation) {
        this.name = name;
        this.encoding = encoding;
        configureWiring(encoding);
        this.rotationPosition = rotation;
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
     * @throws MissingEncodingException When encoding is null
     */
    public int encrypt(int characterIndex, TranslationDirection dir)
            throws MissingEncodingException {
        if (encoding == null) {
            throw new MissingEncodingException();
        }

        switch (dir) {
            case FORWARD:
                return calculateCypherCharacter(characterIndex, wiring);
            case BACKWARD:
                return calculateCypherCharacter(characterIndex, wiringReversed);
            default:
                return 0;
        }
    }

    private int calculateCypherCharacter(int characterIndex, int[] wiringConfiguration) {
        int rotorConfigurationShift = rotationPosition - ringSetting;
        return (wiringConfiguration[(characterIndex + rotorConfigurationShift + Constants.ALPHABET_LENGTH)
                % Constants.ALPHABET_LENGTH] - rotorConfigurationShift
                + Constants.ALPHABET_LENGTH) % Constants.ALPHABET_LENGTH;
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
        rotationPosition = newRotation;
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
}