package com.enigma_machine.enigma;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.enigma_machine.parsers.ReflectorBankParser;
import com.enigma_machine.parsers.RotorBankParser;

public class ComponentCache {
    private HashMap<String, Rotor> rotorCache;
    private HashMap<String, Reflector> reflectorCache;

    public void initialise() throws SAXException {
        rotorCache = new HashMap<>();
        reflectorCache = new HashMap<>();
        // Add preset rotors
        rotorCache.put(RotorFactory.I_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.I_ROTOR, 0, 0));
        rotorCache.put(RotorFactory.II_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.II_ROTOR, 0, 0));
        rotorCache.put(RotorFactory.III_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.III_ROTOR, 0, 0));
        rotorCache.put(RotorFactory.IV_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.IV_ROTOR, 0, 0));
        rotorCache.put(RotorFactory.V_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.V_ROTOR, 0, 0));
        // Add preset reflectors
        reflectorCache.put(ReflectorFactory.A_REFLECTOR,
                ReflectorFactory.buildPresetReflector(ReflectorFactory.A_REFLECTOR));
        reflectorCache.put(ReflectorFactory.B_REFLECTOR,
                ReflectorFactory.buildPresetReflector(ReflectorFactory.B_REFLECTOR));
        reflectorCache.put(ReflectorFactory.C_REFLECTOR,
                ReflectorFactory.buildPresetReflector(ReflectorFactory.C_REFLECTOR));
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

    public List<String> getRotorNames() {
        List<String> rotorNames = new ArrayList<>();
        for (String str : rotorCache.keySet()) {
            rotorNames.add(str);
        }
        return rotorNames;
    }

    public List<String> getReflectorNames() {
        List<String> reflectorNames = new ArrayList<>();
        for (String str : reflectorCache.keySet()) {
            reflectorNames.add(str);
        }
        return reflectorNames;
    }

}
