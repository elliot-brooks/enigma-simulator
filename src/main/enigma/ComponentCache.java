package main.enigma;

import java.util.HashMap;

public class ComponentCache {
    private static HashMap<String, Rotor> rotorCache;
    private static HashMap<String, Reflector> reflectorCache;

    public static void initialise() {
        rotorCache = new HashMap<>();
        reflectorCache = new HashMap<>();
        // TODO : Create all pre-built rotors/reflectors here and add to cache
        // TODO : Implement parsing of xml files here and store all information found
    }

    public static Rotor getRotor(String rotorName) {
        return rotorCache.get(rotorName);
    }

    public static Reflector getReflector(String reflectorName) {
        return reflectorCache.get(reflectorName);
    }

    public static void addReflector(String name, Reflector reflector) {
        reflectorCache.putIfAbsent(name, reflector);
    }

    public static void addRotor(String name, Rotor rotor) {
        rotorCache.putIfAbsent(name, rotor);
    }

}
