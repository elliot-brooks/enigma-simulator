package main.enigma;

public class ReflectorFactory {

    public static final String A_REFLECTOR = "UKW-A";
    public static final String B_REFLECTOR = "UKW-B";
    public static final String C_REFLECTOR = "UKW-C";

    private static final String A_ENCODING = "EJMZALYXVBWFCRQUONTSPIKHGD";
    private static final String B_ENCODING = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
    private static final String C_ENCODING = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
    private static final String REVERSED_ENCODING = "ZYXWVUTSRQPONMLKJIHGFEDCBA";

    public static Reflector buildPresetReflector(String name) throws InvalidReflectorEncodingException {
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
        return new Reflector(name, encoding);
    }
}
