package com.enigma_machine.enigma;

import com.enigma_machine.enigma.exceptions.InvalidReflectorEncodingException;
import com.enigma_machine.tools.Constants;
import com.enigma_machine.tools.Tools;

public class ReflectorFactory {

    public static final String A_REFLECTOR = "UKW-A";
    public static final String B_REFLECTOR = "UKW-B";
    public static final String C_REFLECTOR = "UKW-C";

    private static final String A_ENCODING = "EJMZALYXVBWFCRQUONTSPIKHGD";
    private static final String B_ENCODING = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
    private static final String C_ENCODING = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
    private static final String REVERSED_ENCODING = "ZYXWVUTSRQPONMLKJIHGFEDCBA";

    public static Reflector buildPresetReflector(String name) {
        switch (name) {
            case A_REFLECTOR:
                return new Reflector(name, A_ENCODING);
            case B_REFLECTOR:
                return new Reflector(name, B_ENCODING);
            case C_REFLECTOR:
                return new Reflector(name, C_ENCODING);
            default:
                return new Reflector(name, REVERSED_ENCODING);
        }
    }

    public static Reflector buildCustomReflector(String name, String encoding)
            throws InvalidReflectorEncodingException {
        if (!validateEncoding(encoding)) {
            throw new InvalidReflectorEncodingException();
        }
        return new Reflector(name, encoding);
    }

    /**
     * Enigma Reflectors had a flaw that meant any letter x could not map to x. This
     * ensures that this criteria is met
     * 
     * @return
     */
    private static boolean validateEncoding(String encoding) {

        if (encoding == null) {
            return false;
        }
        if (encoding.length() != 26) {
            return false;
        }
        if (Tools.hasDuplicateLetters(encoding)) {
            return false;
        }
        
        // Check for the case where A -> A (enigma machines could not do this)
        char[] charArray = encoding.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] - Constants.JAVA_A_VALUE == i) {
                return false;
            }
        }
        return true;
    }

}
