package main.struct;

import java.util.HashMap;

public class Rotor {
    private static final int MAX_ROTATION_SETTING = 25;
    /**
     * TODO: Rethink the use of HashMaps, maybe just a string will suffice
     */
    private HashMap<String, String> translationTable;
    private HashMap<String, String> inverseTranslationTable;
    private int rotation;

    public Rotor(HashMap<String, String> translationTable, int rotation) {
        this.translationTable = translationTable;
        this.rotation = rotation;
    }

    public String forwardTranslate(String character) throws MissingTranslationException {
        /**
         * TODO: This needs to be updated to somehow involve the rotation
         */
        return translate(translationTable, character);
    }

    public String backwardTranslate(String character) throws MissingTranslationException {
        /**
         * TODO: This needs to be updated to somehow involve the rotation
         */
        return translate(inverseTranslationTable, character);
    }

    private String translate(HashMap<String, String> map, String character) throws MissingTranslationException {
        if (map == null) {
            return null;
        }
        String cyphertext = map.get(character);
        if (cyphertext == null) {
            throw new MissingTranslationException(character);
        }
        return cyphertext;
    }

    public void setRotation(int newRotation) {
        rotation = newRotation;
    }

    public int getRotation() {
        return rotation;
    }

    /**
     * Simulates the rotation of the rotor
     */
    public void rotate() {
        rotation = (rotation % MAX_ROTATION_SETTING) + 1;
    }
}