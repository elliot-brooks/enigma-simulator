package com.enigma_machine.tools;

public class Tools {

    public static char convertIndexToCharacter(int characterIndex) {
        return (char) (characterIndex + Constants.JAVA_A_VALUE);
    }

    public static int convertCharToIndex(char c) {
        char correctedCase = Character.toUpperCase(c);
        return correctedCase - Constants.JAVA_A_VALUE;
    }

    public static int minusOneInteger(int normalInteger) {
        return normalInteger - 1;
    }

    public static int plusOneInteger(int integer) {
        return integer + 1;
    }

}
