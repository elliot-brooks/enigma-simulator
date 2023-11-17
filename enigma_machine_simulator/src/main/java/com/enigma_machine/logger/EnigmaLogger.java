package com.enigma_machine.logger;

import java.util.ArrayList;
import java.util.List;

import com.enigma_machine.tools.Constants;

public class EnigmaLogger {
    private static String logString = "";
    private static String plaintext = "";
    private static String cyphertext = "";
    private static int[] ringSettings;
    private static List<int[]> rotations = new ArrayList<>();
    private static List<String> encryptionSteps = new ArrayList<>();

    public static void appendLine(String s) {
        logString = logString + s + "\n";
    }

    public static void resetLogger() {
        logString = "";
        plaintext = "";
        cyphertext = "";
        encryptionSteps = new ArrayList<>();
        rotations = new ArrayList<>();
        ringSettings = new int[0];
    }

    public static String getLog() {
        return logString;
    }

    public static void setPlaintext(String newPlaintext) {
        plaintext = newPlaintext;
    }

    public static void setCyphertext(String newCyphertext) {
        cyphertext = newCyphertext;
    }

    public static void addEncrryptionStep(String step) {
        encryptionSteps.add(step);
    }

    public static String getEncryptionStep(int index) {
        return encryptionSteps.get(index);
    }

    public static int[] getRotation(int index) {
        return rotations.get(index);
    }

    public static void addRotation(int[] rotationSettings) {
        rotations.add(rotationSettings);
    }

    public static int[] getEncryptionStepArray(int index) {
        String step = getEncryptionStep(index);
        String[] characterArray = step.split(" -> ");
        int[] stepArray = new int[characterArray.length];
        for (int i = 0; i < characterArray.length; i++) {
            char character = characterArray[i].charAt(0);
            stepArray[i] = character - Constants.JAVA_A_VALUE;
        }

        return stepArray;
    }

    public static String getPlaintext() {
        return plaintext;
    }

    public static String getCyphertext() {
        return cyphertext;
    }

    public static void setRingSetting(int[] newRingSettings) {
        ringSettings = newRingSettings;
    }

    public static int[] getRingSetting() {
        return ringSettings;
    }

}