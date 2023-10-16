package main.enigma;

import main.enigma.exceptions.InvalidRotorEncodingException;

public class RotorFactory {
    public static final String I_ROTOR = "I";
    public static final String II_ROTOR = "II";
    public static final String III_ROTOR = "III";
    public static final String IV_ROTOR = "IV";
    private static final String V_ROTOR = "V";

    public static final int I_TURNOVER = 16; // Notch at Y, turnover at Q
    public static final int II_TURNOVER = 4; // Notch at M, turnover at E
    public static final int III_TURNOVER = 21; // Notch at D, turnover at V
    public static final int IV_TURNOVER = 9; // Notch at R, turnover at J
    public static final int V_TURNOVER = 25; // Notch at H, turnover at Z

    public static Rotor buildPresetRotor(String name, int ringSetting, int startPosition) {
        switch (name) {
            case I_ROTOR:
                return new Rotor(name, "EKMFLGDQVZNTOWYHXUSPAIBRCJ", ringSetting, startPosition, I_TURNOVER);

            case II_ROTOR:
                return new Rotor(name, "AJDKSIRUXBLHWTMCQGZNPYFVOE", ringSetting, startPosition, II_TURNOVER);

            case III_ROTOR:
                return new Rotor(name, "BDFHJLCPRTXVZNYEIWGAKMUSQO", ringSetting, startPosition, III_TURNOVER);

            case IV_ROTOR:
                return new Rotor(name, "ESOVPZJAYQUIRHXLNFTGKDCMWB", ringSetting, startPosition, IV_TURNOVER);

            case V_ROTOR:
                return new Rotor(name, "VZBRGITYUPSDNHLXAWMJQOFECK", ringSetting, startPosition, V_TURNOVER);

            // Return "Identity Rotor"
            default:
                return new Rotor(name, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", ringSetting, startPosition, 0);
        }

    }

    public static Rotor buildCustomRotor(String name, String encoding, int ringSetting, int startPosition,
            int turnoverPosition) throws InvalidRotorEncodingException {
        if (!validateEncoding(encoding)) {
            throw new InvalidRotorEncodingException();
        }
        return new Rotor(name, encoding, ringSetting, startPosition, turnoverPosition);
    }

    private static boolean validateEncoding(String encoding) {
        if (encoding.length() != encoding.chars().distinct().count() && encoding.length() != 26) {
            return false;
        }
        return true;
    }

}
