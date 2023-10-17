package main.enigma;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.parsers.ReflectorBankParser;
import main.parsers.RotorBankParser;

public class ComponentCache {
    private HashMap<String, Rotor> rotorCache;
    private HashMap<String, Reflector> reflectorCache;

    public void initialise() throws SAXException {
        rotorCache = new HashMap<>();
        reflectorCache = new HashMap<>();
        // Add preset rotors
        rotorCache.put("I", RotorFactory.buildPresetRotor("I", 0, 0));
        rotorCache.put("II", RotorFactory.buildPresetRotor("II", 0, 0));
        rotorCache.put("III", RotorFactory.buildPresetRotor("III", 0, 0));
        rotorCache.put("IV", RotorFactory.buildPresetRotor("IV", 0, 0));
        rotorCache.put("V", RotorFactory.buildPresetRotor("V", 0, 0));
        // Add preset reflectors
        reflectorCache.put("UKW-A", ReflectorFactory.buildPresetReflector("UKW-A"));
        reflectorCache.put("UKW-B", ReflectorFactory.buildPresetReflector("UKW-B"));
        reflectorCache.put("UKW-C", ReflectorFactory.buildPresetReflector("UKW-C"));

        // Add custom rotors + reflectors
        try {
            rotorCache.putAll(RotorBankParser.parse());
            reflectorCache.putAll(ReflectorBankParser.parse());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new SAXException(e.getLocalizedMessage());
        }

    }

    public Rotor getRotor(String rotorName) {
        return rotorCache.get(rotorName);
    }

    public Reflector getReflector(String reflectorName) {
        return reflectorCache.get(reflectorName);
    }

    public void addReflector(String name, Reflector reflector) {
        reflectorCache.putIfAbsent(name, reflector);
    }

    public void addRotor(String name, Rotor rotor) {
        rotorCache.putIfAbsent(name, rotor);
    }

}
