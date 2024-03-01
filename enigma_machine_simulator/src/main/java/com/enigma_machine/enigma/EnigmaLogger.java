package com.enigma_machine.enigma;

import java.util.ArrayList;
import java.util.List;

import com.enigma_machine.enigma.tools.Constants;

public class EnigmaLogger {
    private static String logString = "";
    private static String plaintext = "";
    private static String cyphertext = "";
    private static int[] ringSettings;
    private static List<int[]> rotations = new ArrayList<>();
    private static List<String> rotationStrings = new ArrayList<>();
    private static List<String> encryptionSteps = new ArrayList<>();
    private static boolean hasLogged = false;
    private static List<String> rotorNames = new ArrayList<>();
    private static String reflectorName = "";

    public static void appendLine(String s) {
        logString = logString + s + "\n";
    }

    public static void resetLogger() {
        logString = "";
        plaintext = "";
        cyphertext = "";
        encryptionSteps = new ArrayList<>();
        rotations = new ArrayList<>();
        rotationStrings = new ArrayList<>();
        ringSettings = new int[0];
        hasLogged = false;
        rotorNames = new ArrayList<>();
        reflectorName = "";
    }

    public static boolean hasLogged() {
        return hasLogged;
    }

    public static void setLogged(boolean logged) {
        hasLogged = logged;
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

    public static void addEncryptionStep(String step) {
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

    public static void addRotationString(String rotation) {
        rotationStrings.add(rotation);
    }

    public static String getRotationString(int index) {
        return rotationStrings.get(index);
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

    public static String getRotorName(int index) {
        return rotorNames.get(index);
    }

    public static void addRotorName(String name) {
        rotorNames.add(name);
    }

    public static String getReflectorName() {
        return reflectorName;
    }

    public static void setReflectorName(String name) {
        reflectorName = name;
    }
}