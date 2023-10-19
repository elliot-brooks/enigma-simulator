package main.tools;

public class Tools {

    public static char convertIndexToCharacter(int characterIndex) {
        return (char) (characterIndex + Constants.JAVA_A_VALUE);
    }

    public static int convertCharToIndex(char c) {
        char correctedCase = Character.toUpperCase(c);
        return c - Constants.JAVA_A_VALUE;
    }

    public static int correctUserIntegerInput(int normalInteger) {
        return normalInteger - 1;
    }

}
