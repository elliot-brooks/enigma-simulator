package com.enigma_machine.parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.enigma_machine.enigma.Reflector;
import com.enigma_machine.enigma.ReflectorFactory;
import com.enigma_machine.enigma.Rotor;
import com.enigma_machine.enigma.RotorFactory;

public class ComponentCache {
    private HashMap<String, Rotor> rotorCache;
    private HashMap<String, Reflector> reflectorCache;
    private static final String DEFAULT_CONFIG_PATH = "../config/";
    private static final String CONFIG_LOC_TXT = "src/main/resources/config_loc.txt";
    private String configPath;

    public void initialise() throws SAXException {
        rotorCache = new HashMap<>();
        reflectorCache = new HashMap<>();

        // locate config dir
        configPath = locateConfigPath();

        // Add preset rotors
        this.addRotor(RotorFactory.I_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.I_ROTOR, 0, 0));
        this.addRotor(RotorFactory.II_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.II_ROTOR, 0, 0));
        this.addRotor(RotorFactory.III_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.III_ROTOR, 0, 0));
        this.addRotor(RotorFactory.IV_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.IV_ROTOR, 0, 0));
        this.addRotor(RotorFactory.V_ROTOR, RotorFactory.buildPresetRotor(RotorFactory.V_ROTOR, 0, 0));
        // Add preset reflectors
        this.addReflector(ReflectorFactory.A_REFLECTOR,
                ReflectorFactory.buildPresetReflector(ReflectorFactory.A_REFLECTOR));
        this.addReflector(ReflectorFactory.B_REFLECTOR,
                ReflectorFactory.buildPresetReflector(ReflectorFactory.B_REFLECTOR));
        this.addReflector(ReflectorFactory.C_REFLECTOR,
                ReflectorFactory.buildPresetReflector(ReflectorFactory.C_REFLECTOR));
        // Add custom rotors + reflectors
        try {
            rotorCache.putAll(RotorBankParser.parse(configPath));
            reflectorCache.putAll(ReflectorBankParser.parse(configPath));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new SAXException(e.getLocalizedMessage());
        }

    }

    private String locateConfigPath() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_LOC_TXT))) {
            
            String line = reader.readLine();
            if (line == null || line.isEmpty()) {
                throw new IOException();
            }
            if (!isValidPath(line)) {
                throw new IOException();
            }

            return line.trim();
        } catch (IOException e) {
            return DEFAULT_CONFIG_PATH;
        }
    }

    private static boolean isValidPath(String pathString) {
        Path path = Paths.get(pathString);
        return Files.exists(path) && Files.isReadable(path);
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
        rotorNames.add(rotorCache.get(RotorFactory.I_ROTOR).getName());
        rotorNames.add(rotorCache.get(RotorFactory.II_ROTOR).getName());
        rotorNames.add(rotorCache.get(RotorFactory.III_ROTOR).getName());
        rotorNames.add(rotorCache.get(RotorFactory.IV_ROTOR).getName());
        rotorNames.add(rotorCache.get(RotorFactory.V_ROTOR).getName());
        for (String string : rotorCache.keySet()) {
            if (!rotorNames.contains(string)) {
                rotorNames.add(string);
            }
        }
        return rotorNames;
    }

    public List<String> getReflectorNames() {
        List<String> reflectorNames = new ArrayList<>();
        reflectorNames.add(reflectorCache.get(ReflectorFactory.A_REFLECTOR).getName());
        reflectorNames.add(reflectorCache.get(ReflectorFactory.B_REFLECTOR).getName());
        reflectorNames.add(reflectorCache.get(ReflectorFactory.C_REFLECTOR).getName());
        for (String string : reflectorCache.keySet()) {
            if (!reflectorNames.contains(string)) {
                reflectorNames.add(string);
            }
        }

        return reflectorNames;
    }

    public String getConfigPath() {
        return configPath;
    }

}
