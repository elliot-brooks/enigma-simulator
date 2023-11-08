package com.enigma_machine.cli;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.enigma_machine.enigma.ComponentCache;
import com.enigma_machine.enigma.Enigma;
import com.enigma_machine.parsers.EnigmaSettingsParser;
import com.enigma_machine.parsers.exceptions.MissingReflectorException;
import com.enigma_machine.parsers.exceptions.MissingRotorException;

public class EnigmaSimulatorCLI {
    public static void launchCLI() throws ParserConfigurationException, SAXException, IOException,
            MissingRotorException, MissingReflectorException {
        ComponentCache cache = new ComponentCache();
        cache.initialise();
        EnigmaSettingsParser settingParser = new EnigmaSettingsParser();
        settingParser.parse(cache);
        Enigma machine = settingParser.getEnigmaMachine();

        System.out.println(machine.getCurrentSettings() + '\n');

        // Get message input
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a message: ");
        String message = reader.nextLine();
        reader.close();

        System.out.println(message + " = \n" + machine.encrypt(message, false));
    }
}
