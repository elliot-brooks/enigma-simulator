package main.cli;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.enigma.ComponentCache;
import main.enigma.Enigma;
import main.parsers.EnigmaSettingsParser;
import main.parsers.exceptions.MissingReflectorException;
import main.parsers.exceptions.MissingRotorException;

public class EnigmaSimulator {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, MissingRotorException, MissingReflectorException {
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

        System.out.println(message + " = \n" + machine.encrypt(message));
    }
}
